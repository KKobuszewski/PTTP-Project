package server.main;

import java.io.Serializable;

import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

public class FileEvent implements Serializable {
    public FileEvent() {
    }
    
    private static final long serialVersionUID = 1L;
    
    private byte[] fileData = null;
    
    public void encodeBase64(){
    	fileData = Base64.encodeBase64(fileData);
    }
    
    public void decodeBase64(){
    	fileData = Base64.decodeBase64(fileData);
    }
    
    
    public byte[] getFileData() {
        return fileData;
    }
    
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}

