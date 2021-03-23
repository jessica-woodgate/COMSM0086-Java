package com.company;
import com.company.DBCommand.Parser;
import com.company.DBExceptions.DBException;
import com.company.DBExceptions.EmptyData;

import java.io.*;
import java.net.*;
import java.util.*;

class DBServer
{
    private String folderName;
    public DBServer(int portNumber)
    {
        try {
            //open connection
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            //create databases directory to store info
            folderName = "."+ File.separator+"databases";
            File parentFolder = new File(folderName);
            if(!parentFolder.exists()) {
                final boolean mkdir = parentFolder.mkdir();
                //create new folder (for new database)
                if (!mkdir) {
                    throw new IOException();
                }
            }
            while(true) processNextConnection(serverSocket);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextConnection(ServerSocket serverSocket)
    {
        try {
            Socket socket = serverSocket.accept();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Connection Established");
            while(true) processNextCommand(socketReader, socketWriter);
        } catch(IOException | EmptyData ioe) {
            System.err.println(ioe);
        } catch(NullPointerException npe) {
            System.out.println("Connection Lost");
        }
    }

    private void processNextCommand(BufferedReader socketReader, BufferedWriter socketWriter) throws IOException, NullPointerException, EmptyData {
        /*String folderName = File.separator + "Users" + File.separator + "jessw" + File.separator
                + "Documents" + File.separator + "Java" + File.separator + "COMSM0086" +
                File.separator + "Testfiles" + File.separator;*/
        //String newFolderName = File.separator + "Users" + File.separator + "jessw" + File.separator
        //+ "Documents" + File.separator + "Java" + File.separator + "COMSM0086" +
        //File.separator + "DB" + File.separator + "Testfiles" + File.separator;

        /*String folderName = "."+File.separator+"databases";
        File parentFolder = new File(folderName);
        if(!parentFolder.exists()) {
            final boolean mkdir = parentFolder.mkdir();
            //create new folder (for new database)
            if (!mkdir) {
                throw new IOException();
            }
        }*/
        boolean parsedOK = true;
        Parser parser = null;

        /*try {
            FileIO fileIO = new FileIO(folderName);
            Database database = fileIO.readFolder(folderName);
            socketWriter.write(database.getDatabase());
            socketWriter.flush();
            //ArrayList<String> listOfTableNames = database.getTableNames();
            //ArrayList<Table> tables = database.getTables();
            //for(int i = 0; i < database.getNumberOfTables(); i++){
            //    fileIO.writeFile(newFolderName, listOfTableNames.get(i), tables.get(i));
            //}
        } catch(DBException | IOException e){
            e.printStackTrace();
        }*/

        while(parsedOK) {
            String incomingCommand = socketReader.readLine();
            parser = new Parser(incomingCommand, folderName);
            parsedOK = parser.getParsedOK();
            while (!parsedOK) {
                socketWriter.write("[ERROR] from: " + incomingCommand);
                socketWriter.write("\n" + parser.getException());
                socketWriter.write("\n" + ((char) 4) + "\n");
                socketWriter.flush();
                incomingCommand = socketReader.readLine();
                parser = new Parser(incomingCommand, folderName);
                parsedOK = parser.getParsedOK();
            }
            socketWriter.write("[OK] Thanks for your message: " + incomingCommand);
            socketWriter.write("\n" + ((char) 4) + "\n");
            socketWriter.flush();
            folderName = parser.getCurrentFolder();
        }

        /*System.out.println("Received message: " + incomingCommand);
        socketWriter.write("[OK1] Thanks for your message: " + incomingCommand);
        socketWriter.write("\n" + ((char)4) + "\n");
        socketWriter.flush();*/


    }

    public static void main(String[] args) {
        try {
            String folderName = "."+ File.separator+"databases";
            File parentFolder = new File(folderName);
            if(!parentFolder.exists()) {
                final boolean mkdir = parentFolder.mkdir();
                //create new folder (for new database)
                if (!mkdir) {
                    throw new IOException();
                }
            }
            Test testing = new Test();
        } catch (DBException | IOException e) {
            e.printStackTrace();
        }
        DBServer server = new DBServer(8888);
    }
}
