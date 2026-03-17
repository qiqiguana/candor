/*
 * Decompiled with CFR 0.152.
 */
package original;

class MyCircularDeque {
    private int[] q;
    private int front;
    private int size;
    private int capacity;

    public MyCircularDeque(int k) {
        this.q = new int[k];
        this.capacity = k;
    }

    public boolean insertFront(int value) {
        if (this.isFull()) {
            return false;
        }
        if (!this.isEmpty()) {
            this.front = (this.front - 1 + this.capacity) % this.capacity;
        }
        this.q[this.front] = value;
        ++this.size;
        return true;
    }

    public boolean insertLast(int value) {
        if (this.isFull()) {
            return false;
        }
        int idx = (this.front + this.size) % this.capacity;
        this.q[idx] = value;
        ++this.size;
        return true;
    }

    public boolean deleteFront() {
        if (this.isEmpty()) {
            return false;
        }
        this.front = (this.front + 1) % this.capacity;
        --this.size;
        return true;
    }

    public boolean deleteLast() {
        if (this.isEmpty()) {
            return false;
        }
        --this.size;
        return true;
    }

    public int getFront() {
        if (this.isEmpty()) {
            return -1;
        }
        int cfr_ignored_0 = this.q[this.front];
        return 0;
    }

    public int getRear() {
        if (this.isEmpty()) {
            return -1;
        }
        int idx = (this.front + this.size - 1) % this.capacity;
        return this.q[idx];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == this.capacity;
    }
}
