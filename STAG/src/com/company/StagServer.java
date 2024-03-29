package com.company;
import java.io.*;
import java.net.*;

class StagServer
{
    private StagEngine engine;

    public static void main(String args[])
    {
        if(args.length != 2) System.out.println(
                "Usage: java StagServer <entity-file> <action-file>");
        else new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber)
    {
        try {
            //open connection
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            engine = new StagEngine(entityFilename, actionFilename);
            while(true) acceptNextConnection(ss);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void acceptNextConnection(ServerSocket ss)
    {
        try {
            // Next line will block until a connection is received
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in, out);
            out.close();
            in.close();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out)
            throws IOException
    {
        String line = in.readLine();
        String[] splitString = line.split(":", 2);
        //check if the player already exists
        if(!engine.playerExists(splitString[0])){
            engine.addPlayer(splitString[0]);
        } //check if the player is the current player or different
        else if(!engine.getCurrentPlayer().getName().equals(splitString[0])){
            engine.changePlayer(splitString[0]);
        }
        try {
            String response = engine.executeCommand(splitString[1]);
            out.write(response+"\n");
        }catch(Exception e){
            e.printStackTrace();
            out.write(e.toString());
        }
    }
}
