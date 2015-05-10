package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerPTTP {

    public static void main(String[] args) throws IOException {
        
    	ServerSocket serverSocket = new ServerSocket(8000); // This socket enables 
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        
        //System.out.println(results);
        
    	System.out.println("Server is running...");
        while (true) {
            //final Socket socket = serverSocket.accept(); - connection with 
        	
        	
        	executorService.submit(new ConnectionThread(serverSocket.accept()));
            // WARNING! - 11-th thread cancels one of the earlier
        	
        	
        }
        
    }
    
}

