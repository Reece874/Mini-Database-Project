package Model;

public class LazySingleton {
	
	private static LazySingleton singleton = null; 
	private int studentID; 
	
	private LazySingleton() {
		studentID = 0; 
	}
	
	public static LazySingleton getInstance() {
		if(singleton == null) {
			singleton = new LazySingleton();
		}
		return singleton; 
	}
	
	public int getID() {
		return studentID;
	}
	
	public void setID(int id) {
		studentID = id; 
	}

}
