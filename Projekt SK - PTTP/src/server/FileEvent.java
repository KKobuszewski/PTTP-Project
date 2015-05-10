package server;

import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

public class FileEvent implements Serializable {
    public FileEvent() {
    }
    
    private static final long serialVersionUID = 1L;
    
    private String destinationDirectory = null;
    private String sourceDirectory = null;
    private String filename = null;
    private long fileSize = 0;
    private byte[] fileData = null;
    private String status = null;
    
    public void encodeBase64(){
    	fileData = Base64.encodeBase64(fileData);
    }
    
    public void decodeBase64(){
    	fileData = Base64.decodeBase64(fileData);
    }
    
    public String getDestinationDirectory() {
        return destinationDirectory;
    }
    
    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }
    
    public String getSourceDirectory() {
        return sourceDirectory;
    }
    
    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public byte[] getFileData() {
        return fileData;
    }
    
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}

