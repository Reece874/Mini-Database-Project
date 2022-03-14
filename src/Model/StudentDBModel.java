package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class StudentDBModel {
	
	Connection conn = null; 
	
	public StudentDBModel() {
		conn = SQLiteConnector.connector();
		if(conn == null) {
			InfoDisplays.displayGenericError("Connection Failed");
			System.exit(1);
		}
	}
	
	public boolean isDBConnected() {
		try {
			return !conn.isClosed();
		}catch(SQLException e) {
			e.printStackTrace();
			return false; 
		}
	}
	
	public void resetConnection() {
		try {
			if(conn.isClosed()) {
				conn = SQLiteConnector.connector();
				if(conn == null) {
					InfoDisplays.displayGenericError("Connection Failed");
				}
			}
		}catch(SQLException e) {
			InfoDisplays.displayGenericError("Error");
		}
	}

	public ObservableList<TableItem> searchParams(String ID, String FirstName, String LastName, 
			String Username, String StreetNum, String StreetName, String City, String State, String Zip){
		resetConnection();
		ObservableList<TableItem> list = FXCollections.observableArrayList();
		PreparedStatement preparedStatement = null; 
		String query = ("select * from students JOIN address ON students.AddressID = address.AddressID where StudentID=IFNULL(?,StudentID)"
				+ " and FirstName=IFNULL(?,FirstName) and LastName=IFNULL(?,LastName) and Username=IFNULL(?,Username) and "
				+ " StreetNumber=IFNULL(?,StreetNumber) and StreetName=IFNULL(?,StreetName) and City=IFNULL(?,City) and State=IFNULL(?,State)"
				+ "and ZipCode=IFNULL(?,ZipCode)");


		try {
			preparedStatement = conn.prepareStatement(query);
			if(ID != null) {preparedStatement.setInt(1, Integer.parseInt(ID));}
			preparedStatement.setString(2, FirstName);
			preparedStatement.setString(3, LastName);
			preparedStatement.setString(4, Username);
			if(StreetNum != null) {preparedStatement.setInt(5, Integer.parseInt(StreetNum));}
			preparedStatement.setString(6, StreetName);
			preparedStatement.setString(7, City);
			preparedStatement.setString(8, State);
			if(Zip != null) {preparedStatement.setInt(9, Integer.parseInt(Zip));}
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				String address = rs.getInt("StreetNumber") + " " + rs.getString("StreetName") + " " + rs.getString("City") + ", " + rs.getString("State") + " " +  rs.getInt("ZipCode");
				list.add(new TableItem(rs.getString("StudentID"), rs.getString("FirstName"), rs.getString("LastName"), 
						rs.getString("Username"), rs.getString("Password"), address));
			}
			return list; 
		}catch (SQLException e) {
			return null; 
		}catch(NumberFormatException e) {
			InfoDisplays.displayGenericError("ID must be an integer");
			return null; 
		}
		finally {
			try {
				conn.close();
			}catch(SQLException e) {
				InfoDisplays.displayGenericError("Something Went Wrong");
			}
		}
	}
	
	public void addNewStudent(String First, String Last, String User, String Pass, String Num, String City, String StreetName, String State, String Zip) {
		resetConnection();
		if(checkParams(0, First, Last, User, Pass, Num, City, StreetName, State, Zip) && containsUsername(User, 0)) {
			try {
				add(First, Last, User, Pass, Integer.parseInt(Num), City, StreetName, State, Integer.parseInt(Zip));
			}catch(Exception e) {
				InfoDisplays.displayGenericError("Street Number and Zip Code must be integers");
			}finally {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}	
		}	
	}
	
	private void add(String First, String Last, String User, String Pass, int Num, String City, String StreetName, String State, int Zip) {
		String query = "insert into students (FirstName, LastName, Username, Password, AddressID) VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, First);
			preparedStatement.setString(2, Last);
			preparedStatement.setString(3, User);
			preparedStatement.setString(4, Pass);
			
			int x = findAddress(Num, City, StreetName, State, Zip);
			if(x == 0) {
				insertAddress(Num, City, StreetName, State, Zip);
				x = findAddress(Num, City, StreetName, State, Zip);
			}
			
			preparedStatement.setInt(5, x);
			preparedStatement.executeUpdate();

		}catch(Exception e) {
			InfoDisplays.displayGenericError("Something Went Wrong");
		}finally {
			try {
				conn.close();
			}catch(SQLException e) {
				InfoDisplays.displayGenericError("Something Went Wrong");
			}
		}
	}
	
	public void editStudent(int ID, String First, String Last, String User, String Pass, String Num, String City, String StreetName, String State, String Zip) {
		resetConnection();
		if(checkParams(ID, First, Last, User, Pass, Num, City, StreetName, State, Zip) && containsUsername(User, ID)) {
			try {
				edit(ID, First, Last, User, Pass, Integer.parseInt(Num), City, StreetName, State, Integer.parseInt(Zip));
			}catch(Exception e) {
				InfoDisplays.displayGenericError("Street Number and Zip Code must be integers");
			}finally {
				try {
					conn.close();
				}catch(SQLException e) {
					InfoDisplays.displayGenericError("Something Went Wrong");
				}
			}
		}		
	}
	
	private void edit(int ID, String First, String Last, String User, String Pass, int Num, String City, String StreetName, String State, int Zip) {
		String query = "UPDATE students set FirstName=?, LastName=?, Username=?, Password=?, AddressID=? where StudentID=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			
			preparedStatement.setString(1, First);
			preparedStatement.setString(2, Last);
			preparedStatement.setString(3, User);
			preparedStatement.setString(4, Pass);
			preparedStatement.setInt(6, ID);
			
			int x = findAddress(Num, City, StreetName, State, Zip);
			if(x == 0) {
				insertAddress(Num, City, StreetName, State, Zip);
				x = findAddress(Num, City, StreetName, State, Zip);
			}
			
			preparedStatement.setInt(5, x);
			preparedStatement.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			}catch(SQLException e) {
				InfoDisplays.displayGenericError("Something Went Wrong");
			}
		}
	}
	
	private boolean checkParams(int ID, String First, String Last, String User, String Pass, String Num, String City, String StreetName, String State, String Zip) {
		if(First == "" || Last == "" || User == "" || Pass == "" || Num == "" || City == "" || StreetName == "" || State == "" || Zip == "") {
			InfoDisplays.displayGenericError("Something is not filled out");
			return false; 
		}
		return true; 
	}
	
	public boolean containsUsername(String user, int ID) {
		PreparedStatement preparedStatement = null; 
		ResultSet results = null; 
		String query = "select * from students where Username=?";
		String queryIsTheSameUser = "select * from students where Username=? and StudentID=?";
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, user);
			results = preparedStatement.executeQuery();
			if(results.next() && ID != 0) {
				preparedStatement = conn.prepareStatement(queryIsTheSameUser);
				preparedStatement.setString(1, user);	
				preparedStatement.setInt(2, ID);	
				results = preparedStatement.executeQuery();
				if(results.next()) {
					return true; 
				}
				InfoDisplays.displayGenericError("Username is already in use");
				return false; 
			}	
			if(results.next()) {
			InfoDisplays.displayGenericError("Username is already in Use");
			return false; 
			}
			return true; 
		}catch(SQLException e) {
			InfoDisplays.displayGenericError("Something Went Wrong");
			return false; 
		}
		
	}
	
	private int findAddress(int Num, String City, String StreetName, String State, int Zip) {
		String queryAddress = "select * from address where StreetNumber=? and StreetName=? and City=? and State=? and ZipCode=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(queryAddress);
			preparedStatement.setInt(1, Num);
			preparedStatement.setString(2, StreetName);
			preparedStatement.setString(3, City);
			preparedStatement.setString(4, State);
			preparedStatement.setInt(5, Zip);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				return rs.getInt("AddressID"); 
			}else {
				return 0; 
			}
		}catch(SQLException e) {
			InfoDisplays.displayGenericError("Something Went Wrong");
			return 0; 
		}
	}
	
	private void insertAddress(int Num, String City, String StreetName, String State, int Zip) {
		String queryAddress = "insert into address (StreetNumber, StreetName, City, State, ZipCode) VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(queryAddress);
			preparedStatement.setInt(1, Num);
			preparedStatement.setString(2, StreetName);
			preparedStatement.setString(3, City);
			preparedStatement.setString(4, State);
			preparedStatement.setInt(5, Zip);
			preparedStatement.executeUpdate();
		}catch(SQLException e) {
			InfoDisplays.displayGenericError("Something Went Wrong");
		}
	}
	
	public void removeStudent(int ID) {
		resetConnection();
		String query = "delete from students where StudentID=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			preparedStatement.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			}catch(SQLException e) {
				InfoDisplays.displayGenericError("Something Went Wrong");
			}
		}
	}
	
	//Method to get each individual item from a database row into a table 
	public String[] items(int ID) {
		resetConnection();
		String query = "select * from students JOIN address ON students.AddressID = address.AddressID where StudentID=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			ResultSet rs = preparedStatement.executeQuery();	
			String[] items = {rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Username"), 
					rs.getString("Password"), "" + rs.getInt("StreetNumber"), rs.getString("StreetName"), 
					rs.getString("City"), rs.getString("State"), "" + rs.getInt("ZipCode")}; 
			
			return items; 
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace(); 
			}
		}
		return null; 
	}
	
	
}
