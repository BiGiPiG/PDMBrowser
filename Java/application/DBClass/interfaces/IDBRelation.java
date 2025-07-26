package application.DBClass.interfaces;

public interface IDBRelation {
	
	long getRelationID();
	
	int getObjectTypeID();
	
	long getOwnerID();
	
	long getCreatorID();
	
	IDBAttributeCollection getAttributeColection();
	
	boolean delete();

}
