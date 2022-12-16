package Distributed.Matrix;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Server code that can be placed on different computers with change on the port number
 * then a client can make requests for the computations
 *
 * */
public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // don't need to specify a hostname, it will be the current machine
        ServerSocket ss = new ServerSocket(7777);
        System.out.println("ServerSocket awaiting connections...");
        while(true) {
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("Connection from " + socket + "!");

            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            OutputStream outputStream = socket.getOutputStream();
            // create an object output stream from the output stream so we can send an object through it
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // read the list of messages from the socket
            SerializedObject_matrix values = (SerializedObject_matrix) objectInputStream.readObject();
            int[] ans = matrixMultiplication((int[]) values.row, (int[][]) values.matrix);
            SerializedObject_matrix send = new SerializedObject_matrix(ans, values.row_number);
            objectOutputStream.writeObject(send);

            System.out.println("Results sent!!");
        }
        //System.out.println("Closing sockets.");
        //ss.close();
        //socket.close();
    }

    /**
     * method for multiplication of the row matrix and second matrix
     * @return an array of the products
     * */
    public static int[] matrixMultiplication(int[] mat1, int[][] mat2){
        int[] Result = new int[mat2.length];
        for (int j = 0; j<=mat2[0].length-1;j++) {
            int x = 0;
            int temp = 0;
            for (int k = 0; k<=mat2[0].length-1;k++) {
                try {
                    temp += mat1[k] * mat2[x][j];
                    x++;
                }catch (ArrayIndexOutOfBoundsException xt){
                    Result[j] = 0;
                    continue;
                }
            }
            Result[j] = temp;
        }
        System.out.println("Row number: "+ Arrays.toString(Result));
        return Result;
    }
}
