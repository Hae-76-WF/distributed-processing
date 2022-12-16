package Distributed.Matrix;
import java.io.IOException;
import java.util.Arrays;

/**
 * complete matrix client that makes splits up the matrix rows and makes a distributed communication to different
 * server machines and send the row matrices and second matrix for multiplication
 * */

public class Matrix_CompleteClient {
    // Results 2D array (matrix) to store the results
    static int [][] Result = new int[4][];
    static int [][] Matrix1;
    static int [][] Matrix2;

    public static void main(String[] args) throws InterruptedException {
        //hard coded sample matrices to be multiplied
        Matrix1 = new int[][]{{5,65,16},{5,12,56},{12,36,56},{20,35,10}};
        Matrix2 = new int[][]{{8,23,52},{6,23,11},{12,36,6},{5,2,36}};
        System.out.println("Establishing Connections....");

        // making connection to different servers and Send the different rows and matrices for computation
        Clientconection clientconection1 = new Clientconection("",7777, Matrix1[0], Matrix2, 0);
        Clientconection clientconection2 = new Clientconection("",7560, Matrix1[1], Matrix2, 1);
        Clientconection clientconection3 = new Clientconection("",8932, Matrix1[2], Matrix2, 2);
        Clientconection clientconection4 = new Clientconection("",2671, Matrix1[3], Matrix2, 3);

        /**
         *Below, are 4 threads that make each a connection to a unique server machine and each gets back
         *the results to the client to put them together and print
         *
         * */

        //this threads connects to a specific server
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Result[0] = clientconection1.main();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //this threads connects to another server
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20);
                    Result[1] = clientconection2.main();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //this threads connects to another server
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                    Result[2] = clientconection3.main();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        //this threads connects to another server
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(30);
                    Result[3] = clientconection4.main();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        /*
        * Printing out the Final results
        * */
        while(true){
            if (t1.isAlive() == false && t2.isAlive() == false && t3.isAlive() == false && t4.isAlive() == false){
                //2D array format
                System.out.println("-----------------------------------------");
                System.out.println("Final Results in 2D array: "+ Arrays.toString(Result[0]));
                System.out.println("-----------------------------------------");
                System.out.println("-----Final Matrix: -------");
                for(int[] x: Result){
                    for (int j: x){
                        System.out.print(j+"  ");
                    }
                    System.out.print("\n");

                }
                System.out.println("-----------------------------------------");

                break;
            }

        }



    }
}
