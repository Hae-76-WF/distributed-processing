package Distributed.Counting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_counter {
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    int counter = 0;    // Counter on the server that is to be incremented

    public static void main(String[] args) throws IOException {
        //create server object
        Server_counter server = new Server_counter();
        //run server
        server.run();
    }

    void run() throws IOException {
        providerSocket = new ServerSocket(2004, 10);
        System.out.println("Waiting for connection");

        while(true) {
            connection = providerSocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());

            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());

            try {
                // read the increment coming from a client
                message = (String) in.readObject();
                System.out.println("client>" + message);

                /*
                * check if maximum has been reached or not
                * then increment counter if it not maximum
                * */


                if (counter != 2300) {
                    if (message.equals("1"))
                        counter += 1;
                    sendMessage("false");

                } else {
                    sendMessage("Full");
                    System.out.println("Counter has reached maximum: " + counter);
                }

            } catch (ClassNotFoundException tt) {
                System.err.println("Data received in unknown format");
            }

        }



    }

    /*
    * method that send back a flag to a client for tell whether counter has reached maximum or not
    * */
    void sendMessage(String msg)
    {
        try{
            // Send back message to the client true or false
            out.writeObject(msg);
            out.flush();

        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

}