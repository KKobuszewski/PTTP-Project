package server;

import java.io.File;

public class DisplayDirectoryAndFile{
 
	public static void main (String args[]) {
 
		displayIt(new File("C:\\Program Files"));
	}
 
	public static void displayIt(File node){
 
		System.out.println(node.getAbsoluteFile());
 
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				displayIt(new File(node, filename));
			}
		}
 
	}
}
