package Model;

public class TableItem {
	
	String ID, FirstName, LastName, Username, Password, Address; 
	
	public TableItem(String ID, String FirstName, String LastName, String Username, String Password, String Address) {
		this.ID = ID; 
		this.FirstName = FirstName; 
		this.LastName = LastName; 
		this.Username = Username; 
		this.Password = Password; 
		this.Address = Address; 
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	

}
