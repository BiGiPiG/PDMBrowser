package application.DBClass.interfaces;

public interface IDBObject {
	
	long getObjectID();
	
	int getObjectTypeID();
	
	long getOwnerID();
	
	long getCreatorID();
	
	boolean editing();
	
	boolean finishEditing();
	
	IDBAttributeCollection getAttributeColection();

}
