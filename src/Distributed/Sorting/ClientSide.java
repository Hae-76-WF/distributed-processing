package Distributed.Sorting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ClientSide{
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    SerializedArrayObject message;

    String host;
    int port;
    SerializedArrayObject arr_sort;

    /**
     * Initializing the constructor with particulars of a given server i.e. port number and host address
     * along with the sub-array to be sent for sorting
     * */
    public ClientSide(String host, int port, SerializedArrayObject arr_sort){
        this.host = host;
        this.port = port;
        this.arr_sort = arr_sort;
    }

    /*
    * run method for making the full connections to a server
    * */
    public int[] run(){
        try{
            //creating a socket connection
            requestSocket = new Socket(this.host, this.port);
            System.out.println("Connected to: "+this.host+"in port: "+this.port);

            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());


            try{
                //invoking the send method
                sendMessage(this.arr_sort);
                // Receiving sorted array from server
                message = (SerializedArrayObject)in.readObject();
                System.out.println("server>" + Arrays.toString(message.results));

            }
            catch(ClassNotFoundException classNot){
                System.err.println("data received in unknown format");
            }

        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                requestSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
        return message.results;
    }

    /**
     * Method to send the unsorted subarray to server for sorting
     * @param x serialized array
     * */
    public void sendMessage(SerializedArrayObject x){
        try{
            // Send the unsorted array
            out.writeObject(x);
            out.flush();

        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

}