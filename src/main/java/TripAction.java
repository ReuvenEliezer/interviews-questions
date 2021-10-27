import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class TripAction {

    @Test
    public void test() {
        MyQueue myQueue = new MyQueue();
        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(2);
        myQueue.push(1);

        Assert.assertEquals(1, myQueue.peek());
        Assert.assertEquals(1, myQueue.peek());

        Assert.assertEquals(1, myQueue.pop());
        Assert.assertEquals(2, myQueue.peek());
        Assert.assertEquals(2, myQueue.pop());
        Assert.assertEquals(2, myQueue.peek());
        Assert.assertEquals(2, myQueue.pop());
        Assert.assertEquals(1, myQueue.peek());
        Assert.assertEquals(1, myQueue.pop());
    }

    class MyQueue<T> {

        Stack<T> stack1;
        Stack<T> stack2;

        /**
         * Initialize your data structure here.
         */
        public MyQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        /**
         * Push element x to the back of queue.
         */
        public void push(T t) {
            stack1.push(t);
        }

        /**
         * Removes the element from in front of queue and returns that element.
         */
        public T pop() {
            stack2.clear();
            T pop = null;

            while (!stack1.isEmpty()) {
                pop = stack1.pop();
                if (stack1.size() > 0)
                    stack2.push(pop);
            }

            T pop2;
            while (!stack2.isEmpty()) {
                pop2 = stack2.pop();
                stack1.push(pop2);
            }
            return pop;
        }

        /**
         * Get the front element.
         */
        public T peek() {
            stack2.clear();
            T pop = null;

            while (!stack1.isEmpty()) {
                pop = stack1.pop();
                stack2.push(pop);
            }

            T pop2;
            while (!stack2.isEmpty()) {
                pop2 = stack2.pop();
                stack1.push(pop2);
            }
            return pop;
        }

        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            return stack1.isEmpty();
        }
    }
}
