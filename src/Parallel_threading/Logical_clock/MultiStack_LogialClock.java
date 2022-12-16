package Parallel_threading.Logical_clock;
import java.util.Stack;

public class MultiStack_LogialClock {
    private static int clock;
    private static Stack<Content> stack1 = new Stack<>();
    private static Stack<Content> stack2 = new Stack<>();
    private static Stack<Content> stack3 = new Stack<>();
    private static String stackChoice = "A";;

    public MultiStack_LogialClock(){
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        stack3 = new Stack<>();
    }

    /**
    * synchronized method for the incrementing the timer or clock
    * @return clock to timestamp the object
    * */
    synchronized private static int order(){
        clock++;
        return clock;
    }

    /**
     * @param value of object of any type
     * @return void     *
     * @decription method to add time stamped events to the stacks
     * */
    public static void StackEvents(Object value){
        int x = order();
        //stack1.add(new Content(x, value));

        if (stackChoice.equals("A")){
            stack1.push(new Content(x, value));
            stackChoice = "B";
        }else if (stackChoice.equals("B")){
            stack2.push(new Content(x, value));
            stackChoice = "C";
        }else if (stackChoice.equals("C")){
            stack3.push(new Content(x, value));
            stackChoice = "A";
        }

    }

    /**
    * Method for selecting the latest event objects in one of the stacks through comparison
    * of the time among the objects on the three stacks.
    * @return latest event popped from one of the stacks
    * */
    public static Object ChooseEvent(){
        Content results;
        Content values1 = (Content) stack1.peek();
        Content values2 = (Content) stack2.peek();
        Content values3 = (Content) stack3.peek();

        if (values1.time >= values2.time) results = (Content) stack1.pop();
        else results = (Content) stack2.pop();

        if (values3.time >= results.time) results = (Content) stack3.pop();

        return results;

    }

    /**
    * Inner class Content which is the object to store the timestamp and the values/objects to be stored in the
    * stack1, stack2 and stack3
    * */
    private static class Content{
        public int time;
        public Object value;
        public Content(int time, Object value){
            this.time = time;
            this.value =value;
        }

    }

    /**
    * Main method with threads x, x1 and x2 for putting the values into the stacks in an ordered manner of events
    * */
    public static void main(String[] args) throws InterruptedException {
        Thread x = new Thread(new Runnable() {
            @Override
            public void run() {
                int value = 0;
                while (true){
                    // pushing events tothe stacks
                    StackEvents(value);
                    System.out.println("Thread 1 added something");
                    if (value == 10){
                        break;
                    }
                    value++;
                    try {
                        Thread.sleep(8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread x1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int value = 10;
                while (true){
                    // pushing event to the stacks
                    StackEvents(value);
                    System.out.println("Thread 2 added something");
                    if (value == 0){
                        break;
                    }
                    value--;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Thread x2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int value = 500;
                while (true){
                    // pushing event to the stacks
                    StackEvents(value);
                    System.out.println("Thread 3 added something");
                    if (value == 510){
                        break;
                    }
                    value++;
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        x.start();
        x1.start();
        x2.start();

        x.join();
        x1.join();
        x2.join();

        while (true) {
            if (x.isAlive() == false && x1.isAlive() == false && x2.isAlive() == false) {
                // popping the latest event in the stacks by invoking ChooseEvent
                Content event = (Content) ChooseEvent();
                System.out.println("Order Number/Time: " + event.time);
                System.out.println("Value: " + event.value);
                break;
            }
        }
    }
}

