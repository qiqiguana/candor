package net.virtualinfinity.atrobots.tournament;

import net.virtualinfinity.atrobots.arena.FrameBuilder;
import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.game.Game;
import net.virtualinfinity.atrobots.game.GameResult;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class PairTournament implements Callable<PairTournamentResults> {
    private List<RobotFactory> competitors;
    private int roundsPerPairing = 25;
    private FrameBuilder frameBuffer;
    private final long delay;
    private final ExecutorService executorService;

    public PairTournament() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1), 0);
    }

    public PairTournament(long delay) {
        this(Executors.newSingleThreadExecutor(), delay);
    }

    public PairTournament(ExecutorService executorService, long delay) {
        this.executorService = executorService;
        this.delay = delay;
    }

    public PairTournamentResults call() throws InterruptedException, ExecutionException {
        final PairTournamentResults results = new PairTournamentResults();
        final CompletionService<GameResult> service = new ExecutorCompletionService<GameResult>(executorService);
        int toTake = 0;
        for (final RobotFactory left : competitors) {
            for (final RobotFactory right : competitors) {
                if (right == left) {
                    break;
                }
                ++toTake;
                service.submit(new CompeteWorker(results, left, right));
            }
        }
        for (int count = 0; count < toTake; ++count) {
            results.recordScores(service.take().get());
        }
        return results;
    }

    public List<RobotFactory> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<RobotFactory> competitors) {
        this.competitors = competitors;
    }

    public int getRoundsPerPairing() {
        return roundsPerPairing;
    }

    public void setRoundsPerPairing(int roundsPerPairing) {
        this.roundsPerPairing = roundsPerPairing;
    }

    private GameResult compete(RobotFactory left, RobotFactory right) throws InterruptedException {
        System.out.println("Running pairing: " + left.getName() + " vs " + right.getName());
        final Object lock = frameBuffer != null ? frameBuffer : new Object();
        synchronized (lock) {
            final Game game = new Game(roundsPerPairing, frameBuffer);
            game.addEntrant(left);
            game.addEntrant(right);
            game.nextRound();
            while (game.stepRound()) {
                if (delay > 0) {
                    Thread.sleep(delay);
                }
            }
            System.out.println("Pairing complete: " + left.getName() + " vs " + right.getName());
            return game.getFinalResults();
        }
    }

    public FrameBuilder getFrameBuffer() {
        if (frameBuffer == null) {
            frameBuffer = new FrameBuilder();
        }
        return frameBuffer;
    }

    private class CompeteWorker implements Callable<GameResult> {
        private final PairTournamentResults results;
        private final RobotFactory left;
        private final RobotFactory right;

        public CompeteWorker(PairTournamentResults results, RobotFactory left, RobotFactory right) {
            this.results = results;
            this.left = left;
            this.right = right;
        }

        public GameResult call() throws InterruptedException {
            return compete(left, right);
        }
    }
}
