package application.DBClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseHolder {
	
	private static Connection conn;
	
	static {
		
		File file = new File(System.getProperty("user.home") + "/connection.txt");
		
		StringBuilder text = new StringBuilder();
		
		try(FileReader reader = new FileReader(file))
        {
            int c;
            while((c=reader.read())!=-1){
                text.append((char)c);
            } 
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        }
		
		String[] data = text.toString().trim().split(" ");
		
		try {
			conn = DriverManager.getConnection(data[0], data[1], data[2]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Statement getStatement() throws SQLException {
			
		return conn.createStatement();
		
	}

}
