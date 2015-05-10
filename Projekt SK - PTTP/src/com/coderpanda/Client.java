package com.coderpanda;

import java.io.*;
import java.net.Socket;

import server.FileEvent;

public class Client {
    private Socket socket = null;
    private boolean isConnected = false;
    
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private FileOutputStream fileOutputStream = null;
    private File dstFile = null;
    private String sourceFilePath = "C:/studia/paper_1_2_5.pdf";
    private FileEvent fileEvent = null;
    private String destinationPath = "C:/tmp/downloads/";
    
    public Client() {
    	super();
    }
	
    /**
     * Connect with server code running in local host or in any other host
     */
    public void connect() {
        while (!isConnected) {
            try {
                socket = new Socket("localhost", 4445);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                isConnected = true;
            } catch (IOException e) {
            	System.out.println("Connection error!");
                e.printStackTrace();
            }
        }
    }
	
    /**
     * Reading the FileEvent object and copying the file to disk.
     */
    public void downloadFile() {
        try {
            fileEvent = (FileEvent) inputStream.readObject();
            if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
                System.out.println("Error occurred ..So exiting");
                System.exit(0);
            }
            String outputFile = fileEvent.getDestinationDirectory() + fileEvent.getFilename();
            if (!new File(fileEvent.getDestinationDirectory()).exists()) {
                 new File(fileEvent.getDestinationDirectory()).mkdirs();
            }
            dstFile = new File(outputFile);
            fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(fileEvent.getFileData());
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Output file : " + outputFile + " is successfully saved ");
            Thread.sleep(3000);
            System.exit(0);
                
       } catch (IOException e) {
            e.printStackTrace();
       } catch (ClassNotFoundException e) {
            e.printStackTrace();
       } catch (InterruptedException e) {
            e.printStackTrace();
       }
    }
    
    /**
     * Sending FileEvent object.
     */
    public void sendFile() {
        fileEvent = new FileEvent();
        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath);
        fileEvent.setFilename(fileName);
        fileEvent.setSourceDirectory(sourceFilePath);
        File file = new File(sourceFilePath);
        if (file.isFile()) {
            try {
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }
                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Success");
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {
            System.out.println("path specified is not pointing to a file");
            fileEvent.setStatus("Error");
        }
        //Now writing the FileEvent object to socket
        try {
            outputStream.writeObject(fileEvent);
            System.out.println("Done...Going to exit");
            Thread.sleep(3000);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
	
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        client.sendFile();
    }
}


