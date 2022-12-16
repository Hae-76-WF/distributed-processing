package Parallel_threading;

public class Counter {
    // variable counter to store the count
    private static int counter;

    public static void main(String[] args) throws InterruptedException {

        // first thread to count
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    count();
                    System.out.println(Thread.currentThread().getName() + " " + counter);
                } while (counter != 1000);

            }
        });

        // Second thread to count
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    count();
                    System.out.println(Thread.currentThread().getName() + " " + counter);
                } while (counter != 1000);
            }
        });

        // third thread to count
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    count();
                    System.out.println(Thread.currentThread().getName() + " " + counter);
                } while (counter != 1000);
            }
        });

        // Forth thread to count
        Thread thread$ = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    count();
                    System.out.println(Thread.currentThread().getName() + " " + counter);
                } while (counter != 1000);

            }
        });


        thread1.start();
        thread2.start();
        thread3.start();
        thread$.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread$.join();

    }

    /**
     * Synchronized method for incrementing the counter by the different threads
     * */
    static synchronized public void count() {
        if (counter != 1000){
            counter += 1;
        }

    }


}
