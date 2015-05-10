package client;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import server.FileEvent;

public class  PTTPClient
{
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception
	{
		Socket socket=new Socket("localhost",8000);
		
		// Buffered Input Stream for reading bytes for server
		ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
		//BufferedReader inputStream=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// Print Stream for writing inquires to server
		PrintStream outputStream=new PrintStream(socket.getOutputStream());
		
		// Buffered Reader for reading path from console -> Change to GUI
		BufferedReader consoleReader=new BufferedReader(new InputStreamReader(System.in));
		
		// File stream for writing
		String outputPath = "./Wyniki/";
		if (!new File(outputPath).exists()) 
			new File(outputPath).mkdirs();
		FileOutputStream fileOut = null;
		
		String pttpInquiry = null;
		String message = null;
		File file = null;
		List<String> serverFiles = null;
		Object serverResponse = null;
		//int bufferSize = socket.getReceiveBufferSize();
		
		System.out.print("Client : ");
		String filename = consoleReader.readLine();
		pttpInquiry = "GET "+filename+" PTTP/1.0";
		outputStream.println("");
		outputStream.println(pttpInquiry);
		
		
		
		
		serverResponse = inputStream.readObject();
		System.out.println(serverResponse.getClass());
		System.out.println(serverResponse);
		try {
			//file = (File) serverResponse;
			fileOut = new FileOutputStream(new File(outputPath+filename));
			System.out.println(outputPath+filename);
			FileEvent data = (FileEvent) serverResponse;
			fileOut.write( data.getFileData() );
		} catch (ClassCastException e) {
			e.printStackTrace();
			serverFiles = (List<String>) serverResponse;
			for (int ii =0; ii < serverFiles.size(); ii++)
				System.out.println(serverFiles.get(ii));
		}
		
		socket.close();
		inputStream.close();
		outputStream.close();
 		consoleReader.close();
	}
	
	
	
	/**
	     * Reading the FileEvent object and copying the file to disk.
	     */
	    public void downloadFile(ObjectInputStream inputStream) {
	    	FileEvent fileEvent = null;
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
	            File dstFile = new File(outputFile);
	            FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
	            fileOutputStream.write(fileEvent.getFileData());
	            fileOutputStream.flush();
	            fileOutputStream.close();
	            System.out.println("Output file : " + outputFile + " is successfully saved ");
	            Thread.sleep(3000);
	            System.exit(0);
	            
	        } catch (ClassCastException e) {
				e.printStackTrace();
				List<String> serverFiles = (List<String>) fileEvent;
				System.out.println(serverFiles);
			} catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
}
