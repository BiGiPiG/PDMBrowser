package application.DBClass;

import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.Gson;

import application.DBClass.interfaces.IDBAttribute;

public class DBAttribute implements IDBAttribute{
	
	long id;
	int attributeID;
	
	public DBAttribute(long objectID, Object attributeID) throws Exception{
		
		Statement stat = DataBaseHolder.getStatement();
		
		if(attributeID instanceof String) {
			
			stat.execute("SELECT attribute_type_id FROM storage.attribute_types where caption = \"" + (String)attributeID + "\"");
			
			ResultSet resultSet = stat.getResultSet();
			
			int count = 0;
			
			while(resultSet.next()) {
				
				attributeID = resultSet.getInt(1);
				
				count++;
				
			}
			
			if(count == 0) throw new Exception("Атрибут не найден.");
			
		}
		
		stat.execute("SELECT * FROM storage.attributes where type = " + attributeID + " and set_object = " + objectID);
		
		ResultSet resultSet = stat.getResultSet();
		
		int count = 0;
		
		while(resultSet.next()) {
			
			count++;
			
		}
		
		if(count == 0) throw new Exception("Атрибут не найден.");
		
		this.id = objectID;
		this.attributeID = (int)attributeID;
		
	}
	
	public Object getValue() {
		
		Gson json = new Gson();
		
		try {
			
			Statement stat = DataBaseHolder.getStatement();
			
			stat.execute("SELECT value, system_type FROM storage.attributes cross join storage.attribute_types where attribute_types.attribute_type_id = storage.attributes.type and storage.attributes.set_object = " + id + " and attribute_types.attribute_type_id = " + attributeID);
			
			ResultSet result = stat.getResultSet();
			
			result.next();
			
			Object val = json.fromJson(result.getString("value"), Object.class);
			
			String type = result.getString("system_type");
			
			if(type.contentEquals("string")) {
				
				if(val.toString().startsWith("#")) {
					
					val = getComplexValue(val.toString());
					
				}
				
				return val.toString();
				
			}
			
			return val;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private String getComplexValue(String complexVal) {
		
		complexVal = complexVal.replaceFirst("#", "").replace('+', '&');
		
		String[] vals = complexVal.split("&");
		
		complexVal = "";
		
		for (String val : vals) {
			
			int id = Integer.parseInt(val.trim());
			
			try {
				complexVal += new DBAttribute(this.id, id).getValue().toString();
			} catch (Exception e) {
			}
			
		}
		
		return complexVal;
		
	}
	
	public void setValue(Object val) {
		
	}

}
