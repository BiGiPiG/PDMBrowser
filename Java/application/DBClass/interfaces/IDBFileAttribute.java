package application.DBClass.interfaces;

import java.util.Date;

public interface IDBFileAttribute extends IDBAttribute {
	
	String getFileName();
	
	Date getLastModifyDate();
	
	Date getCreationDate();
	
	long getCreatorID();
	
	long getSize();
	
	byte[] read();
	
	void write(String fileName, Date creationDate, Date lastModifyDate, long creatorID, byte[] data);
	
}
