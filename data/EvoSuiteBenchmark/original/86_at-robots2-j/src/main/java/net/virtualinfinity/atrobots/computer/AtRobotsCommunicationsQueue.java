package net.virtualinfinity.atrobots.computer;

import net.virtualinfinity.atrobots.comqueue.CommunicationsQueue;

/**
 * A communications queue wrapper.
 *
 * @author Daniel Pitts
 */
public class AtRobotsCommunicationsQueue implements CommunicationsQueue {
    private final MemoryRegion queue;
    private final MemoryCell head;
    private final MemoryCell tail;
    private ComputerErrorHandler computerErrorHandler;

    public AtRobotsCommunicationsQueue(MemoryRegion communicationsQueueMemory, MemoryCell head, MemoryCell tail) {
        this.queue = communicationsQueueMemory;
        this.head = head;
        this.tail = tail;
    }

    public void popTo(MemoryCell destination) {
        fixRange();
        if (isEmpty()) {
            computerErrorHandler.commQueueEmptyError();
            return;
        }
        destination.set(queue.get(head.signed()));
        head.increment();
        fixRange();
    }

    public boolean isEmpty() {
        return head.signed() == tail.signed();
    }

    private void fixRange() {
        fixRange(head);
        fixRange(tail);
    }

    private void fixRange(MemoryCell pointer) {
        if (pointer.unsigned() >= queue.size()) {
            pointer.set((short) 0);
        }
    }

    public short size() {
        fixRange();
        final int size = tail.signed() - head.signed();
        return (short) (size > 0 ? size : (size + queue.size()));
    }

    public void setComputerErrorHandler(ComputerErrorHandler computerErrorHandler) {
        this.computerErrorHandler = computerErrorHandler;
    }

    public void enqueue(short value) {
        fixRange(tail);
        fixRange(head);
        queue.set(tail.signed(), value);
        tail.increment();
        fixRange(tail);
    }
}
