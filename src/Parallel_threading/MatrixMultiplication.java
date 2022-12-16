package Parallel_threading;
import java.util.Arrays;

public class MatrixMultiplication {
    private static int matrix_1[][];
    private static int matrix_2[][];
    private static int Result[][];

    public static void main(String[] args) throws InterruptedException {
        // Sample of two matrices to be multiplied
        matrix_1 = new int[][]{{1,2,3},{4,5,6}};
        matrix_2 = new int[][]{{1,2,3},{4,5,6}};
        // Matrix to store the product of the two above matrices
        Result = new int[matrix_2[0].length][matrix_2[0].length];

        /*
        * thread to multiply the first row by column of the matrices
        * */
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                threadmatrix(0);
            }
        });

        /*
        * Second thread to multiply the second row by column of the matrices
        * */
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                threadmatrix(1);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread1.join();

        // printing the final results of the products
        System.out.println(Arrays.toString(Result[0])+Arrays.toString(Result[1])+Arrays.toString(Result[2]));
    }

    /**
     * @param i for the row index in the 2 dimensional array which is a matrix
     * method that does the multiplication
     * */
    public static void threadmatrix(int i){
        for (int j = 0; j<=matrix_2[0].length-1;j++) {
            int x = 0;
            int temp = 0;

            // multiplication is done in the for loop below
            for (int k = 0; k<=matrix_2[0].length-1;k++) {
                try {
                    temp += matrix_1[i][k] * matrix_2[x][j];
                    x++;
                }catch (ArrayIndexOutOfBoundsException xt){
                    /*
                    *if a row is missing in the array for which we are multiplying then, those parts are filled
                    * with zeroes since there is nothing to multiply with.
                    */
                    Result[i][j] = 0;
                    continue;
                }
            }
            //storing the temp value of the multiplication to the 'Results' matrix
            Result[i][j] = temp;
        }
        System.out.println("Row number: "+i+Arrays.toString(Result[i]));
    }
}
