package Distributed.Sorting;

import java.io.Serializable;

public class Distributing_UnsortedArray {
    private  static int[] arr = {98,53,9,11,86,7,97,23,71,59,22,60,91,95,33,67,21,74,17,89,66,61,
            20,83,82,55,54,87,42,92,18,84,34,14,3,10,41,73,46,26};
    private static int [] sect1 = new int[13];
    private static int [] sect2 = new int[13];
    private static int [] sect3 = new int[14];


    /**
     * Method that does the splitting of the unsorted array into unsorted sub-arrays
     * @return array of serialized sub-array objects
     * */
    public SerializedArrayObject[] splitter() {
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
        return new SerializedArrayObject[]{new SerializedArrayObject(sect1), new SerializedArrayObject(sect2), new SerializedArrayObject(sect3)};

    }
}

/**
 * Serialized object class that is used to send objects of the sorted and unsorted arrays results to and from
 * the client and server
 * */
class SerializedArrayObject implements Serializable{
    int[] arr;
    int[] results;
    boolean flag;

    // constructor to send serialized unsorted array from client to server
    public SerializedArrayObject(int [] x){
        this.arr = x;
    }

    // constructor to use for sending serialized results from server to client
    public SerializedArrayObject(int[] y, boolean flag){
        this.results = y;
        this.flag =flag;
    }


}