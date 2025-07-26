package application.DBClass;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import application.DBClass.interfaces.IDBAttribute;
import application.DBClass.interfaces.IDBAttributeCollection;

public class DBAttributeCollection implements IDBAttributeCollection {

	long objectID = 0;
	
	public DBAttributeCollection(long id) {
		objectID = id;
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IDBAttribute get(Object key) {
		
		try {
			
			return new DBAttribute(objectID, key);
			
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public IDBAttribute put(Object key, IDBAttribute value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDBAttribute remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Object, ? extends IDBAttribute> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Object> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IDBAttribute> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<Object, IDBAttribute>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
