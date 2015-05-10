package client.main;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;



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
			//e.printStackTrace();
			serverFiles = (List<String>) serverResponse;
			for (int ii =0; ii < serverFiles.size(); ii++)
				System.out.println(serverFiles.get(ii));
		}
		
		socket.close();
		inputStream.close();
		outputStream.close();
 		consoleReader.close();
	}
	
}
