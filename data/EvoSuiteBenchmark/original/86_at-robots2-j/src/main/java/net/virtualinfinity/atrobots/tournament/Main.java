package net.virtualinfinity.atrobots.tournament;

import net.virtualinfinity.atrobots.compiler.AtRobotCompiler;
import net.virtualinfinity.atrobots.compiler.AtRobotCompilerOutput;
import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.network.Server;
import net.virtualinfinity.atrobots.robot.RobotScore;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class Main {


    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
        final List<String> realArgs = new ArrayList<String>(Arrays.asList(args));
        final boolean runServer = realArgs.remove("--server");

        final List<RobotFactory> competitors = new ArrayList<RobotFactory>();
        final AtRobotCompiler compiler = new AtRobotCompiler();
        for (String arg : realArgs) {
            try {
                final AtRobotCompilerOutput compile = compiler.compile(getRobotFile(arg));
                String name;
                if (arg.contains("/")) {
                    name = arg.substring(arg.indexOf("/") + 1).toLowerCase();
                } else {
                    name = arg.toLowerCase();
                }
                if (name.endsWith(".at2") || name.endsWith(".atl")) name = name.substring(0, name.length() - 4);
                competitors.add(compile.createRobotFactory(name));
            } catch (Exception e) {
                System.out.println(arg + " failed to compile, disqualified. ");
                e.printStackTrace();
            }
        }
        final PairTournament tournament;
        if (!runServer) {
            tournament = new PairTournament();
        } else {
            tournament = new PairTournament(25);
            final Server server = new Server(new ServerSocket(2001));
            server.setBuffer(tournament.getFrameBuffer());
            final Thread thread = new Thread(server);
            thread.setDaemon(true);
            thread.start();
        }
        tournament.setRoundsPerPairing(10);
        tournament.setCompetitors(competitors);
        final PairTournamentResults results = tournament.call();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                final JFrame frame = new JFrame("Tournament Results");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JTable table = new JTable(new AbstractTableModel() {
                    public int getRowCount() {
                        return competitors.size();
                    }

                    public int getColumnCount() {
                        return competitors.size() + 1;
                    }

                    public Object getValueAt(int rowIndex, int columnIndex) {
                        if (columnIndex == 0) {
                            return competitors.get(rowIndex).getName();
                        }
                        if (rowIndex == (columnIndex - 1)) {
                            return "vs";
                        }
                        final RobotFactory scorer = competitors.get(rowIndex);
                        final RobotFactory opponent = competitors.get(columnIndex - 1);
                        final RobotScore score = results.getScore(scorer, opponent);
                        return score == null ? "x" : score.getTotalWins();
                    }

                    @Override
                    public String getColumnName(int column) {
                        return column == 0 ? "vs" : competitors.get(column - 1).getName();
                    }
                });
//                frame.getContentPane().add(new JScrollPane(table));
                final JPanel scorePanel = new JPanel();
                frame.getContentPane().add(scorePanel);
                scorePanel.setLayout(new GridLayout(competitors.size(), competitors.size()));
                for (int y = 0; y < competitors.size(); ++y) {
                    for (int x = 0; x < competitors.size(); ++x) {
                        if (x == y) {
                            scorePanel.add(new JLabel(competitors.get(x).getName()));
                        } else
                        if (x < y) {
                            scorePanel.add(new JComponent(){});
                        } else {
                            final RobotScore leftScore = results.getScore(competitors.get(y), competitors.get(x));
                            final RobotScore rightScore = results.getScore(competitors.get(x), competitors.get(y));
                            scorePanel.add(new JComponent() {
                                @Override
                                protected void paintComponent(Graphics g) {
                                    g.drawLine(0, getHeight(), getWidth(), 0);
                                    g.drawString(leftScore.getTotalWins()+"", 0, 0);
                                    g.drawString(leftScore.getTotalWins()+"", 0, getHeight());
                                }
                            });
                        }
                    }
                }
                frame.pack();
                frame.setVisible(true);
            }
        });
//        System.out.println("Name:\tGames Won\tGames tied\tRounds won");
//        for (PairTournamentResults.Score score : results.getScores()) {
//            System.out.println(score.getName() + ":\t" + score.getTotalWins() + "\t" + score.getTies() + "\t" + score.getRoundWins());
//        }
    }

    private static File getRobotFile(String arg) {
        return new File(arg);
    }
}
