package net.virtualinfinity.atrobots.debugger;

import net.virtualinfinity.atrobots.computer.Computer;
import net.virtualinfinity.atrobots.computer.DebugListener;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class Debugger implements DebugListener {
    private final BlockingQueue<Command> afterBreakpointCommandQueue = new LinkedBlockingDeque<Command>();
    private final BlockingQueue<Command> commandQueue = new LinkedBlockingDeque<Command>();
    private BreakpointHandler breakpointHandler;

    private volatile boolean allPaused;
    private boolean wasAllPaused;
    private volatile Integer defaultEntrant;
    private boolean inBreakpoint;

    protected void doPauseAll() {
        allPaused = true;
    }

    private final Map<Integer, EntrantState> entrantStates = new HashMap<Integer, EntrantState>();

    protected EntrantState getEntrantState(Computer computer) {
        return getEntrantState(computer.getId());
    }

    private EntrantState getEntrantState(int entrantId) {
        EntrantState state = entrantStates.get(entrantId);
        if (state == null) {
            state = createEntrantState();
            entrantStates.put(entrantId, state);
        }
        return state;
    }

    protected EntrantState createEntrantState() {
        return new EntrantState();
    }


    protected boolean isPaused(Computer computer) {
        return allPaused || getEntrantState(computer).isPaused();
    }

    protected void doPause(Computer computer) {
        getEntrantState(computer).pause();
    }

    protected boolean doPause(Integer entrantId) {
        return getEntrantState(entrantId).isPaused();
    }


    protected void clearPaused(Computer computer) {
        if (getEntrantState(computer).isPaused()) {
            getEntrantState(computer).clearPaused();
        } else {
            allPaused = false;
        }
    }

    private boolean isBreakpoint(Computer computer) {
        return getEntrantState(computer).isBreakpoint(computer);
    }

    public void afterInstruction(Computer computer) {
    }

    public boolean isAllPaused() {
        return allPaused;
    }

    public int getDefaultEntrant() {
        return defaultEntrant;
    }

    public void setDefaultEntrant(int defaultEntrant) {
        this.defaultEntrant = defaultEntrant;
    }

    public void clearDefaultEntrant() {
        this.defaultEntrant = null;
    }

    public boolean hasDefaultEntrant() {
        return defaultEntrant != null;
    }

    protected void invokeOnBreakpoint(Command command) throws InterruptedException {
        afterBreakpointCommandQueue.put(command);
    }

    protected void invokeLater(Command command) throws InterruptedException {
        commandQueue.put(command);
    }

    public void beforeInstruction(Computer computer) {
        try {
            final Collection<Command> commands = new ArrayList<Command>();
            commandQueue.drainTo(commands);
            for (Command command : commands) {
                command.execute(computer);
            }
            wasAllPaused = isAllPaused();
            if (isBreakpoint(computer) || isPaused(computer)) {
                clearPaused(computer);
                getBreakpointHandler().handleBreakpoint(computer);
                waitForCommandAfterBreakpoint(computer);
            }
        } catch (InterruptedException e) {
            Thread.interrupted();
            e.printStackTrace();
        }
    }

    private void waitForCommandAfterBreakpoint(Computer computer) throws InterruptedException {
        inBreakpoint = true;
        while (inBreakpoint) {
            afterBreakpointCommandQueue.take().execute(computer);
        }
    }

    public void pause(final int enrantId) throws InterruptedException {
        invokeLater(new Command() {
            public void execute(Computer computer) {
                doPause(enrantId);
            }
        });
    }

    public void pauseAll() throws InterruptedException {
        invokeLater(new Command() {
            public void execute(Computer computer) {
                doPauseAll();
            }
        });
    }

    public void go() throws InterruptedException {
        invokeOnBreakpoint(new Command() {
            public void execute(Computer computer) {
                inBreakpoint = false;
                clearPaused(computer);
            }
        });
    }

    public void step() throws InterruptedException {
        invokeOnBreakpoint(new Command() {
            public void execute(Computer computer) throws InterruptedException {
                inBreakpoint = false;
                if (wasAllPaused) {
                    doPauseAll();
                } else {
                    doPause(computer);
                }
            }
        });
    }

    public void pause() throws InterruptedException {
        if (hasDefaultEntrant()) {
            pause(getDefaultEntrant());
        }
        pauseAll();
    }

    public BreakpointHandler getBreakpointHandler() {
        return breakpointHandler;
    }

    public void setBreakpointHandler(BreakpointHandler breakpointHandler) {
        this.breakpointHandler = breakpointHandler;
    }

    public void clearBreakpoint() throws InterruptedException {
        invokeOnBreakpoint(new Command() {
            public void execute(Computer computer) {
                getEntrantState(computer).removeBreakpoint(computer.getInstructionPointer());
            }
        });

    }

    public void clearBreakpoint(final int instructionPointer) throws InterruptedException {
        invokeLater(new Command() {
            public void execute(Computer computer) {
                getDefaultEntrantState(computer).removeBreakpoint(instructionPointer);
            }
        });
    }

    public void setBreakpoint() throws InterruptedException {
        invokeOnBreakpoint(new Command() {
            public void execute(Computer computer) {
                getEntrantState(computer).setBreakpoint(computer.getInstructionPointer());
            }
        });
    }

    public void setBreakpoint(final int instructionPointer) throws InterruptedException {
        invokeLater(new Command() {
            public void execute(Computer computer) {
                getDefaultEntrantState(computer).setBreakpoint(instructionPointer);
            }
        });
    }

    private EntrantState getDefaultEntrantState(Computer computer) {
        return hasDefaultEntrant() ? getEntrantState(getDefaultEntrant()) : getEntrantState(computer);
    }

    public void resetDefaultEntrant() throws InterruptedException {
        invokeLater(new Command() {
            public void execute(Computer computer) throws InterruptedException {
                setDefaultEntrant(computer.getId());
            }
        });
    }

    protected static class EntrantState {
        private Set<Integer> breakpoints = new HashSet<Integer>();
        private boolean paused;

        public boolean isBreakpoint(Computer computer) {
            return breakpoints.contains(computer.getInstructionPointer());
        }

        public void setBreakpoint(int breakpoint) {
            breakpoints.add(breakpoint);
        }

        public void removeBreakpoint(int breakpoint) {
            breakpoints.remove(breakpoint);
        }

        public boolean isPaused() {
            return paused;
        }

        public void clearPaused() {
            paused = false;
        }

        public void pause() {
            paused = true;
        }
    }

    protected static interface Command {
        void execute(Computer computer) throws InterruptedException;
    }


}
