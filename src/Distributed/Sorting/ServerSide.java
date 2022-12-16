package Distributed.Sorting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerSide{
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    SerializedArrayObject message;


    public void run(){
        try{
            //creating the server socket
            providerSocket = new ServerSocket(2004, 10);
            System.out.println("Waiting for connection");

            //accepting connections from client
            connection = providerSocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());

            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            //sendMessage("Connection successful");


            try{
                //Receiving unsorted array from client and store it in message variable
                message = (SerializedArrayObject)in.readObject();
                System.out.println(Arrays.toString(message.arr));

                /*
                * Sorting and then sending the results back to the client
                * */
                sendMessage(new SerializedArrayObject(insertion_sort(message.arr), true));
            }
            catch(ClassNotFoundException classnot){
                System.err.println("Data received in unknown format");
            }

        }
        catch(IOException ioException){
            ioException.printStackTrace();

        }finally{
            // Closing connection
            try{
                in.close();
                out.close();
                providerSocket.close();
            }catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Method to send the results of the sorted array to client
     * @param msg is sorted array t send
     * */
    public void sendMessage(SerializedArrayObject msg){
        try{
            // send the array back to client
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    /**
     * Method that uses the insertion sort algorithm to sort an array
     * @param values is the array to sort
     * @return sorted array
     * */
    private int [] insertion_sort(int [] values){
        for (int i = 1; i < values.length; i++) {
            int key = values[i];
            int j = i - 1;
            while (j >= 0 && values[j] > key) {
                values[j + 1] = values[j];
                j = j - 1;
            }
            values[j + 1] = key;
        }
        return values;
    }

    public static void main(String args[]){
        ServerSide server = new ServerSide();

        //run the server forever
        while(true){
            server.run();
        }
    }
}