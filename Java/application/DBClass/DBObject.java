package application.DBClass;

import java.sql.ResultSet;
import java.sql.Statement;

import application.DBClass.interfaces.IDBAttributeCollection;
import application.DBClass.interfaces.IDBObject;

public class DBObject implements IDBObject{
	
	long id;
	
	public DBObject(long id) throws Exception {
		
		Statement stat = DataBaseHolder.getStatement();
		
		stat.execute("SELECT * FROM storage.objects where object_id = " + id);
		
		ResultSet resultSet = stat.getResultSet();
		
		int count = 0;
		
		while(resultSet.next()) {
			
			count++;
			
		}
		
		if(count == 0) throw new Exception("Объект не найден.");
		
		this.id = id;
	}

	public long getObjectID() {
		return id;
	}

	@Override
	public int getObjectTypeID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getOwnerID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCreatorID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean editing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finishEditing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IDBAttributeCollection getAttributeColection() {
		
		return new DBAttributeCollection(id);
		
	}

}
