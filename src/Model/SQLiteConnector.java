package Model;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConnector {
	
	public static Connection connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:StudentsData.sqlite";
			Connection conn = DriverManager.getConnection(url);	
			return conn; 
		}catch(Exception e){
			e.printStackTrace();
			return null; 
		}
	}

}
