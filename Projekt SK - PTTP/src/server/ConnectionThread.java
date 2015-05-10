/**
 * 
 */
package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;

import client.Serializer;

/**
 * @author root
 *
 */
public class ConnectionThread implements Runnable {
	Socket socket = null;
	List<String> results = null;
	
	public ConnectionThread(final Socket socket){
		this.socket = socket;
		
		results = new ArrayList<String>();
		
        File[] files = new File("./").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null. 
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
	}
	
	@Override
	public void run() {
		try {
        	
			BufferedReader inputStream=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//PrintStream outputStream=new PrintStream(socket.getOutputStream());
			ObjectOutputStream outputStreamObj = new ObjectOutputStream(socket.getOutputStream());
			DataInputStream diStream = null;
			
			System.out.println("New connection");
			
			
			
    		String pttpInquiry;
    		while (  true )
    		{
    			inputStream.readLine();
    			pttpInquiry = inputStream.readLine();
    			System. out.print("Client : "+pttpInquiry+"\n");
    			String[] pttpInquiryParts = pttpInquiry.split(" ");
    			String path = pttpInquiryParts[1];
    			
    			for (int ii = 2; ii < (pttpInquiryParts.length-1); ii++) {
    				path += " ";
    				path = path.concat(pttpInquiryParts[ii]);
    			}
    			
    			System. out.println(path);
    			
    			if (Files.exists(Paths.get(path))){
    				File file = new File(path);
    				System.out.println(file);
    				/*diStream = new DataInputStream(new FileInputStream(file));
    				
    				long len = (int) file.length();
                    byte[] fileBytes = new byte[(int) len];
                    int read = 0;
                    int numRead = 0;
                    
                    // it has to be done to enable sending data of any size
                    
                    while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                    	fileBytes.length - read)) >= 0) {
                        read = read + numRead;
                    }*/
    				System.out.println(Paths.get(path));
    				byte[] fileBytes = Files.readAllBytes(Paths.get(path));
    				System.out.println(fileBytes);
    				
                    System.out.println(fileBytes);
                    FileEvent partToSend = new FileEvent();
                    partToSend.setFileData(fileBytes);
                    partToSend.setFileSize(fileBytes.length);
                    
                    
        			outputStreamObj.writeObject(partToSend);
    			}
    			else {
    				outputStreamObj.writeObject(results);
    			}
    			
      			
      			break;
    		}
    		
     		socket.close();
     		inputStream.close();
    		outputStreamObj.close();
            
        }catch (SocketException e) {
        	
        	JDialog dialog = new JDialog();
    		dialog.setLocationRelativeTo(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setAlwaysOnTop(false);
    		dialog.add(	new JLabel("Client has interrupted connection!"),
    					BorderLayout.CENTER);
    		dialog.setSize(new Dimension(200,100));
    		dialog.setVisible(true);
    		    		
        	System.out.println("Client has interrupted connection!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	
	
	/**
	     * Sending FileEvent object.
	     */
	    public void sendFile(String sourceFilePath, ObjectOutputStream outputStream) {
	    	FileEvent fileEvent = new FileEvent();
	        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
	        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
	        //fileEvent.setDestinationDirectory(destinationPath);
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
	        }
	        else {
	        	System.out.println("path specified is not pointing to a file");
	        	fileEvent.setStatus("Error");
	        }
	        //Now writing the FileEvent object to socket
	        try {
	        	outputStream.writeObject(fileEvent);
	            System.out.println("Done...Going to exit");
	            //Thread.sleep(3000);
	            //System.exit(0);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        } //catch (InterruptedException e) {
	        //	e.printStackTrace();
	        //}
	        
	    }
}
