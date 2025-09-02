package original;

import java.util.Deque;
import java.util.Set;
class MyCircularDeque {
    private int[] q;
    private int front;
    private int size;
    private int capacity;

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        q = new int[k];
        capacity = k;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
  /**
  Design your implementation of the circular double-ended queue (deque). Implement the MyCircularDeque class: MyCircularDeque(int k) Initializes the deque with a maximum size of k. boolean insertFront() Adds an item at the front of Deque. Returns true if the operation is successful, or false otherwise. boolean insertLast() Adds an item at the rear of Deque. Returns true if the operation is successful, or false otherwise. boolean deleteFront() Deletes an item from the front of Deque. Returns true if the operation is successful, or false otherwise. boolean deleteLast() Deletes an item from the rear of Deque. Returns true if the operation is successful, or false otherwise. int getFront() Returns the front item from the Deque. Returns -1 if the deque is empty. int getRear() Returns the last item from Deque. Returns -1 if the deque is empty. boolean isEmpty() Returns true if the deque is empty, or false otherwise. boolean isFull() Returns true if the deque is full, or false otherwise. &nbsp; Example 1: Input [&quot;MyCircularDeque&quot;, &quot;insertLast&quot;, &quot;insertLast&quot;, &quot;insertFront&quot;, &quot;insertFront&quot;, &quot;getRear&quot;, &quot;isFull&quot;, &quot;deleteLast&quot;, &quot;insertFront&quot;, &quot;getFront&quot;] [[3], [1], [2], [3], [4], [], [], [], [4], []] Output [null, true, true, true, false, 2, true, true, true, 4] Explanation MyCircularDeque myCircularDeque = new MyCircularDeque(3); myCircularDeque.insertLast(1); // return True myCircularDeque.insertLast(2); // return True myCircularDeque.insertFront(3); // return True myCircularDeque.insertFront(4); // return False, the queue is full. myCircularDeque.getRear(); // return 2 myCircularDeque.isFull(); // return True myCircularDeque.deleteLast(); // return True myCircularDeque.insertFront(4); // return True myCircularDeque.getFront(); // return 4 &nbsp; Constraints: 1 &lt;= k &lt;= 1000 0 &lt;= value &lt;= 1000 At most 2000 calls will be made to insertFront, insertLast, deleteFront, deleteLast, getFront, getRear, isEmpty, isFull.
  */
        if (isFull()) {
            return false;
        }
        if (!isEmpty()) {
            front = (front - 1 + capacity) % capacity;
        }
        q[front] = value;
        ++size;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }
        int idx = (front + size) % capacity;
        q[idx] = value;
        ++size;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }
        front = (front + 1) % capacity;
        --size;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }
        --size;
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        if (isEmpty()) {
            return -1;
        }
        return q[front];
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if (isEmpty()) {
            return -1;
        }
        int idx = (front + size - 1) % capacity;
        return q[idx];
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return size == capacity;
    }
}

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */