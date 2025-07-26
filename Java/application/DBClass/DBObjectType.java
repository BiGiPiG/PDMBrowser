package application.DBClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

public class DBObjectType {
	
	private String caption;
	
	private int id; 

	public DBObjectType(int id, String caption) {
		this.id = id;
		this.caption = caption;
	}
	
	public String getName() {
		return caption;
	}
	
	public int getID() {
		return id;
	}
	
	public List<DBObjectType> getChildTypes(){
		
		List<DBObjectType> types = new ArrayList<>();
		
		Gson json = new Gson();
		
		int[] typeIDs = new int[0];
		
		try (Statement stat = DataBaseHolder.getStatement()){
			
			stat.execute("SELECT childs_types FROM storage.object_types where object_type_id = " + id);
			
			ResultSet result = stat.getResultSet();
			
			while(result.next()) {
				
				typeIDs = json.fromJson(result.getString(1), int[].class);
				
			}
			
			if(typeIDs == null) return types;
			
			for (int id : typeIDs) {
				
				stat.execute("SELECT caption FROM storage.object_types where object_type_id = " + id);
				
				result = stat.getResultSet();
				
				while(result.next()) {
					
					types.add(new DBObjectType(id, result.getString(1)));
					
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return types;
	}
	/*
	public DBObjectType addChildType(DBObjectType objectType){
		return child.put(objectType.getName(), objectType);
	}
	
	public DBObjectType removeChildType(String name){
		return child.remove(name);
	}*/
	
}
