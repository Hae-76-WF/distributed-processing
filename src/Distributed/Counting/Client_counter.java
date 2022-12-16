package Distributed.Counting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/*
* code for client that will increment the counter on server
* */
public class Client_counter {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;


    public static void main(String args[]) throws IOException {
        Client_counter client = new Client_counter();
        while (true) {
            //run client
            client.run();
            // if server returns 'Full' message, the client will stops sending the increment
            if(client.message.equals("Full")){
                System.out.println("Server counter has reached!!");
                break;
            }
        }


    }

    /*
    * method to establish a connection and send the increment to server
    * */
    void run() throws IOException{
        requestSocket = new Socket("localhost", 2004);

        out = new ObjectOutputStream(requestSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(requestSocket.getInputStream());

        try {
            sendMessage("1");
            System.out.println("Sent another");
            message = (String) in.readObject();
        } catch (ClassNotFoundException classNot) {
            System.err.println("data received in unknown format");
        }



        try {
            in.close();
            out.close();
            requestSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    /*
    * method for sending increment to server
    * */
    void sendMessage(String msg){
        try{
            // send increment
            out.writeObject(msg);
            out.flush();

        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

}