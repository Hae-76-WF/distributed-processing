package Parallel_threading.SocketConnections;

import java.io.*;
import java.net.*;

public class ClientSide{
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    public void run(){
        try{
            requestSocket = new Socket("", 2004);
            System.out.println("Connected to localhost in port 2004");

            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

            do{
                try{
                    sendMessage("Hello");
                    message = (String)in.readObject();
                    System.out.println("server>" + message);
                    sendMessage("Hi my server");
                    message = "bye";

                }
                catch(ClassNotFoundException classNot){
                    System.err.println("data received in unknown format");
                }
            }while(!message.equals("bye"));
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
    }
    void sendMessage(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        ClientSide client = new ClientSide();
        client.run();
    }
}