package Parallel_threading;

import java.util.Arrays;

public class Sorting {
    // sample hard coded array to be sorted
    private static int [] arr = {98,53,9,11,86,7,97,23,71,59,22,60,91,95,33,67,21,74,17,89,66,61,
            20,83,82,55,54,87,42,92,18,84,34,14,3,10,41,73,46,26};

    // subarrays that will contain partitions of the main array to be sorted
    private static int [] sect1 = new int[13];
    private static int [] sect2 = new int[13];
    private static int [] sect3 = new int[14];


    public static void main(String[] args) throws InterruptedException {
        // putting different segments of the unsorted array into several unsorted sub-arrays
        int num = 0;
        for (int x = 0; x<=39; x++) {
            if (x<=12){
                sect1[num] = arr[x];
            }else if (x >= 13 && x <= 25){
                if (x == 13){
                    num = 0;
                }
                sect2[num] = arr[x];

            } else if (x >= 26 && x <= 39) {
                if (x == 26) {
                    num = 0;
                }
                sect3[num] = arr[x];

            }
            num+=1;
        }

        // thread to sort the first partition
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sect1 = selection_sort(sect1);
                System.out.println("sect1 "+Arrays.toString(sect1));
            }
        });


        //thread to sort the sort partition
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sect2 = selection_sort(sect2);
                System.out.println("sect2 "+Arrays.toString(sect2));
            }
        });

        // thread to sort the third partition of the array
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sect3 = selection_sort(sect3);
                System.out.println("sect3 "+Arrays.toString(sect3));
            }
        });

        // thread to merge the above sorted sub-arrays
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // invoking the merging method after the first, second and third thread are dead
                while (true){
                    if (!thread1.isAlive() && !thread2.isAlive() && !thread3.isAlive()){
                        arr = merger(sect3,sect2,sect1);
                        break;
                    }
                }

            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        // printing the sorted array
        System.out.println("Sorted: "+ Arrays.toString(arr));

    }

    /**
    * method that sorts an array using the selection sort algorithm
    * @param values array of integers
    * @return values that are sorted of that array
    * */
    private static int [] selection_sort(int [] values){
        // int array[] = {};
        int size = values.length;

        for (int i = 0 ;i< size-1; i++){
            int min = i;

            for (int j = i+1; j<size; j++){
                if (values[j] < values[min]){
                    min = j;
                }
            }
            int temp = values[min];
            values[min] = values[i];
            values[i] = temp;
        }

        return values;
    }

    /**
     * Method merger for mergin three arrays
     * @param x is an array
     * @param y is an array
     * @param z is an array
     * @return a merged array of the three arrays
     * */
    private static int [] merger(int [] x, int [] y, int [] z){
        // array to store the merge of the 3 arrays
        int [] results = new int[x.length+y.length+z.length];

        int temp;

        // variables to store position indices of each of the three arrays
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
}
