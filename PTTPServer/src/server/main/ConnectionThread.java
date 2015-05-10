/**
 * 
 */
package server.main;

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
    				    				
    				System.out.println(Paths.get(path));
    				byte[] fileBytes = Files.readAllBytes(Paths.get(path));
    				System.out.println(fileBytes);
    				
                    System.out.println(fileBytes);
                    FileEvent partToSend = new FileEvent();
                    partToSend.setFileData(fileBytes);
                    
                    
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
	
	
	
	
}
