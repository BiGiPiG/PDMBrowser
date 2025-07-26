package application.DBClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class DBObjectCollection {
	
	private static List<DBObject> objects = new ArrayList<>();
	
	private int type;
	
	public DBObjectCollection(int type){
		this.type = type;
	}
	
	//public void add(String caption) {
		
	//	objects.add(new DBObject(caption, type));
		
	//}
	
	public DBObject[] select(){
		
		List<DBObject> selectObj = new ArrayList<>();
		
		Gson json = new Gson();
		
		DBObject[] array = new DBObject[0];
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storage", "root", "82548391")){
			
			Statement stat0 = conn.createStatement();
			
			stat0.execute("SELECT object_id FROM storage.objects where object_type = " + type);
			
			ResultSet result0 = stat0.getResultSet();
			
			while(result0.next()) {
				
				long id = result0.getLong("object_id");
				
				Statement stat1 = conn.createStatement();
				
				stat1.execute("SELECT value FROM storage.attributes where set_object = " + id + " and type = -1");
				
				ResultSet result1 = stat1.getResultSet();
				
				result1.next();
				
				String caption = json.fromJson(result1.getString("value"), String.class);
				
				if(caption.startsWith("#")) {
					
					caption = caption.substring(1);
					
					String[] atrs = caption.split("\\+");
					
					caption = "";
					
					for (String atr : atrs) {
						
						Statement stat2 = conn.createStatement();
						
						stat2.execute("SELECT value FROM storage.attributes where set_object = " + id + " and type = " + atr.trim());
						
						ResultSet result2 = stat2.getResultSet();
						
						while(result2.next()) {
							
							caption += json.fromJson(result2.getString("value"), String.class) + " ";
							
						}
						
					}
					
				}
				
				selectObj.add(new DBObject(id));
				
			}
			
			array = new DBObject[selectObj.size()];
			int i = 0;
			
			for (DBObject obj : selectObj) {
				
				array[i] = obj;
				i++;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array;
		
		//return null;
		
	}
	
	public DBObject[] consistFrom(){
		
		List<DBObject> selectObj = new ArrayList<>();
		
		Gson json = new Gson();
		
		DBObject[] array = new DBObject[0];
		
		
		
		return array;
		
	}

}
