package application.DBClass;

import application.DBClass.interfaces.IDBObject;
import application.DBClass.interfaces.IDBObjectCollection;
import application.DBClass.interfaces.IDBRelation;
import application.DBClass.interfaces.IDBRelationCollection;
import application.DBClass.interfaces.ISession;

public class Session implements ISession {

	public long getUserID() {
		return 1;
	}

	public IDBObject getObject(long objectID) {
		
		try {
			
			return new DBObject(objectID);
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public IDBRelation getRelation(IDBObject parent, IDBObject child, int relationType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDBObjectCollection getObjectCollection(int objectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDBRelationCollection getRelationCollection(int relationType) {
		// TODO Auto-generated method stub
		return null;
	}

}
