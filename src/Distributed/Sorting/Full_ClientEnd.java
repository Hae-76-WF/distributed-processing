package Distributed.Sorting;

import java.util.Arrays;

public class Full_ClientEnd {
    private static int[] sort1;
    private static int[] sort2;
    private static int[] sort3;

    public static void main(String[] args) throws InterruptedException {
        Distributing_UnsortedArray st = new Distributing_UnsortedArray();
        SerializedArrayObject[] xv = st.splitter();

        // making three connections to three different servers to sort our arrays
        ClientSide conn1 = new ClientSide("", 2000, xv[0]);
        ClientSide conn2 = new ClientSide("", 2001, xv[1]);
        ClientSide conn3 = new ClientSide("", 2002, xv[2]);

        // first thread to send one segment of the unsorted array to a certain server
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sort1 = conn1.run();

            }
        });

        // Second thread to send one segment of the unsorted array to another certain server
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sort2 = conn2.run();

            }
        });
        // Third thread to send one segment of the unsorted array to another server
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                sort3 = conn3.run();

            }
        });

        //thread to merge the returned results from three different servers that sorted the sub-arrays
        Thread Final_Result = new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                * Merge the arrays after the above threads have received the results sub-arrays and have died
                *
                * */
                while(true){
                    if (thread1.isAlive() == false && thread2.isAlive() == false && thread3.isAlive() == false){
                        int [] result = merger(sort1, sort2, sort3);
                        System.out.println("Final sorted Array: "+ Arrays.toString(result));
                        break;
                    }
                }
            }

            /**
             * Method to merge the three returned results
             * */
            int [] merger(int [] x, int [] y, int [] z){
                int [] results = new int[x.length+y.length+z.length];
                int temp;
                int posX = 0, posY = 0, posZ = 0;

                for (int i = 0; i<=results.length-1; i++){
                    int flag = 0;

                    if (posX > x.length - 1) {
                        if (posY <= y.length - 1) {
                            temp = y[posY];
                            if (posZ <= z.length -1) {

                                if (temp >= z[posZ]) {
                                    temp = z[posZ];
                                    flag = 1;
                                }

                                if (flag == 0) posY += 1;
                                else if (flag == 1) posZ += 1;
                                results[i] = temp;
                            }else{
                                results[i] = y[posY];
                                posY += 1;
                            }
                        } else {
                            if (posZ <= z.length -1) {
                                results[i] = z[posZ];
                                posZ += 1;
                            }
                        }


                    } else if (posX <= x.length - 1) {
                        temp = x[posX];
                        if (posY <= y.length - 1) {
                            if (posZ <= z.length - 1) {
                                if (temp >= y[posY]) {
                                    temp = y[posY];
                                    flag = 1;
                                }

                                if (temp >= z[posZ]) {
                                    temp = z[posZ];
                                    flag = 2;
                                }

                                if (flag == 0) posX += 1;
                                else if (flag == 1) posY += 1;
                                else if (flag == 2) posZ += 1;
                                results[i] = temp;
                            }
                        } else {
                            if (posZ <= z.length-1) {
                                if (temp >= z[posZ]) {
                                    temp = z[posZ];
                                    flag = 2;
                                }

                                if (flag == 0) posX += 1;
                                else if (flag == 2) posZ += 1;
                                results[i] = temp;
                            }else {
                                results[i] = x[posX];
                                posX += 1;
                            }
                        }

                    }
                }
                return results;
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        Final_Result.start();

        thread1.join();
        thread2.join();
        thread3.join();
        Final_Result.join();




    }


}