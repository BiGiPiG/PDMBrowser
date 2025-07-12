public interface ISession {

	IDBObject getObject(long objectID);
	
	IDBRelation getRelation(IDBObject parent, IDBObject child, int relationType);
	
	IDBObjectCollection getObjectCollection(int objectType);
	
	IDBRelationCollection getRelationCollection(int relationType);
	
}
