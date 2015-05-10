/**
 * 
 */
package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * @author root
 *
 */
public class ConnectionThread implements Runnable {
	Socket socket = null;
	
	public ConnectionThread(final Socket socket){
		this.socket = socket;
		
	}
	
	@Override
	public void run() {
		try {
        	
			BufferedReader inputStream=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream outputStream=new PrintStream(socket.getOutputStream());
			
    		String s;
    		while (  true )
    		{
    			s=inputStream.readLine();
      			if (s.equalsIgnoreCase("END"))
      			{
    				outputStream.println("BYE");
        				break;
      			  }
      			if (s.equalsIgnoreCase("YES")){
      				outputStream.println("FILE");
      			}else{
      				outputStream.println("DO U WANT FILE? type: /YES/");
      			}
    			System. out.print("Client : "+s+"\n");
    			System.out.print("Server : ");
    			
    		}
    		
     		socket.close();
     		inputStream.close();
    		outputStream.close();
            
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
