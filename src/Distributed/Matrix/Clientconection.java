package Distributed.Matrix;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * client class for making a connection to a server
 * */

public class Clientconection {

    String host;
    int port;
    int[] row;
    int[][] matrix;
    int row_number;

    public Clientconection(String host, int port, int[] row, int [][]second_matrix, int row_number){
        this.host = host;
        this.port = port;
        this.row = row;
        this.matrix = second_matrix;
        this.row_number = row_number;

    }

    public int[] main() throws IOException, ClassNotFoundException {
        // need host and port, we want to connect to the ServerSocket at port 7777
        Socket socket = new Socket(this.host, this.port);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // get the input stream from the connected socket
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);


        System.out.println("Sending request...");
        SerializedObject_matrix send_compute = new SerializedObject_matrix(this.row, this.matrix, this.row_number);
        objectOutputStream.writeObject(send_compute);

        // retrieving results from server and store in
        SerializedObject_matrix value = (SerializedObject_matrix) objectInputStream.readObject();
        System.out.println("Results: "+ Arrays.toString(value.results));

        System.out.println("Closing socket and terminating program.");
        socket.close();
        return value.results;
    }
}