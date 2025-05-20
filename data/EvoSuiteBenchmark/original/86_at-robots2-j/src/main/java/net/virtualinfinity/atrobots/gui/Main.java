package net.virtualinfinity.atrobots.gui;

import net.virtualinfinity.atrobots.compiler.AtRobotCompiler;
import net.virtualinfinity.atrobots.compiler.AtRobotCompilerOutput;
import net.virtualinfinity.atrobots.compiler.Errors;
import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * This is the main GUI class.
 *
 * @author Daniel Pitts
 */
public class Main extends ArenaWindowBuilder implements Runnable {
    private volatile Game game;
    private boolean paused = true;

    private final Object gameLock;
    private volatile int frameDelay = 25;
    private volatile boolean useDelay = true;
    private Thread gameThread = new GameThread();

    private volatile boolean closed;
    private boolean debugMode;
    private java.util.List<String> initialRobots;
    private final AbstractAction runAction = new AbstractAction("Run") {
        public void actionPerformed(ActionEvent e) {
            setPaused(!paused);
            this.putValue(Action.NAME, paused ? "Run" : "Pause");
        }
    };

    public boolean isPaused() {
        synchronized (gameLock) {
            return paused;
        }
    }

    public void setPaused(boolean paused) {
        synchronized (gameLock) {
            this.paused = paused;
            gameLock.notifyAll();
        }
    }

    private final AbstractAction speedToggleAction = new AbstractAction("Full Speed") {
        public void actionPerformed(ActionEvent e) {
            useDelay = !useDelay;
            this.putValue(Action.NAME, useDelay ? "Full Speed" : "Slower");
        }
    };

    public Main() {
        gameLock = new Object();
    }

    public void run() {
        initializeWindow();

        startGame();
    }

    private void startGame() {
        setGame(new Game(1000));
        if (!initialRobots.isEmpty()) {
            new EntrantLoader(initialRobots).execute();
        }

        gameThread.setDaemon(true);
        gameThread.start();
    }

    @Override
    protected void buildMenuBar() {
        menubar.add(createFileMenu());
        menubar.add(createViewMenu());
        menubar.add(new JButton(runAction));
        if (isDebugMode()) {
            addDebugMenuItems();
        }
    }

    private JMenu createFileMenu() {
        final JMenu menu = new JMenu("Game");
        menu.add(new AbstractAction("New Game") {
            public void actionPerformed(ActionEvent e) {
                final NewGameDialog dialog = new NewGameDialog();
                dialog.setModal(true);
                dialog.pack();
                dialog.setVisible(true);
                final Game newGame = dialog.getGame();
                if (newGame != null) {
//                    if (game != null) {
//                        game.dispose();
//                    }
                    setGame(newGame);
                }
                game.nextRound();
            }
        });
        menu.add(new AbstractAction("Add Entrant") {
            public void actionPerformed(ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(RobotFileUtils.getAtRobotsFileNameFilter());
                chooser.setMultiSelectionEnabled(true);
                if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
                    new EntrantLoader(chooser.getSelectedFiles()).execute();
                }
            }
        });
        return menu;
    }

    private void setGame(Game newGame) {
        setPaused(true);
        arenaPane.reset();
        robotStatusPane.reset();
        game = newGame;
        game.addSimulationObserver(arenaPane);
        game.addSimulationObserver(robotStatusPane);
    }

    private void addDebugMenuItems() {
        menubar.add(new JButton(new AbstractAction("Add all original") {
            public void actionPerformed(ActionEvent e) {
                new EntrantLoader(new File("original").listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".at2");
                    }
                })).execute();

            }
        }));
        menubar.add(new JButton(new AbstractAction("Add single") {
            public void actionPerformed(ActionEvent e) {
                for (final File parent : new File[]{new File("."), new File("original")})
                    new EntrantLoader(parent.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().matches("zitgun.at2") ||
                                    name.toLowerCase().endsWith("sniper2.at2");
                        }
                    })).execute();

            }
        }));
        menubar.add(new JButton(speedToggleAction));
    }

    public static void main(String[] args) {
        final Main main = new Main();
        java.util.List<String> initialRobots = new ArrayList<String>(Arrays.asList(args));
        main.setDebugMode(initialRobots.remove("--debug"));
        main.setInitialRobots(initialRobots);
        EventQueue.invokeLater(main);
    }

    private void setInitialRobots(java.util.List<String> initialRobots) {
        this.initialRobots = initialRobots;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    protected void registerCloseListener() {
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                synchronized (gameLock) {
                    closed = true;
                    paused = false;
                    gameLock.notifyAll();
                }

            }
        });
    }

    private class EntrantLoader extends SwingWorker<Errors, RobotFactory> {
        private final RobotFileUtils.EntrantFile[] selectedFiles;

        public EntrantLoader(File[] selectedFiles) {
            this(RobotFileUtils.getEntrantFiles(selectedFiles));
        }

        public EntrantLoader(RobotFileUtils.EntrantFile[] selectedFiles) {
            this.selectedFiles = selectedFiles;
        }

        public EntrantLoader(java.util.List<String> initialRobots) {
            this(RobotFileUtils.getFilesByName(initialRobots));
        }


        protected Errors doInBackground() throws Exception {
            Errors errors = new Errors();
            for (RobotFileUtils.EntrantFile entrantFile : selectedFiles) {
                final File file = entrantFile.file;
                AtRobotCompiler compiler = new AtRobotCompiler();
                try {
                    System.out.println("Loading " + file);
                    final AtRobotCompilerOutput result = compiler.compile(file);
                    if (result.hasErrors()) {
                        errors.info("Errors in " + file.getName());
                        errors.addAll(result.getErrors());
                    }
                    if (game != null) {
                        game.addEntrant(result.createRobotFactory(file.getName()).setDebug(entrantFile.debug));
                    }
                } catch (IOException e1) {
                    errors.info("Errors in " + file.getName());
                    errors.info(e1.getMessage());
                } catch (Throwable t) {
                    errors.info("Compiler error in " + file.getName());
                    errors.info(t.getMessage());
                    t.printStackTrace();
                }
            }
            return errors;
        }

        protected void done() {
            try {
                get().showErrorDialog("Errors", mainFrame);
                game.nextRound();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            }
        }
    }


    private class GameThread extends Thread {
        public void run() {
            while (!closed) {
                try {
                    synchronized (gameLock) {
                        while (paused && !closed) {
                            gameLock.wait();
                        }
                    }
                    if (useDelay) {
                        Thread.sleep(frameDelay);
                    } else {
                        Thread.yield();
                    }
                    synchronized (gameLock) {
                        if (!closed && game != null) {
                            game.stepRound();
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
