package Controller;

import java.io.IOException;

import Model.StudentDBModel;
import Model.InfoDisplays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentController {
	
	@FXML
	private Label LblTitle;
	
	@FXML
	private Label LblStudent;
	
	@FXML
	private Label LblAddress;
	
	@FXML
	private TextField TxtFirstName;
	
	@FXML
	private TextField TxtLastName;
	
	@FXML
	private TextField TxtUsername;
	
	@FXML
	private TextField TxtPassword;
	
	@FXML
	private TextField TxtStreetName;
	
	@FXML
	private TextField TxtStreetNumber;
	
	@FXML
	private TextField TxtCity;
	
	@FXML
	private TextField TxtState;
	
	@FXML
	private TextField TxtZip;
	
	@FXML
	private Button BtnAddStudent;
	
	@FXML
	private Button BtnCancel;
	
	StudentDBModel connect = new StudentDBModel();
	
	public void Add(ActionEvent actionevent) {
		connect.addNewStudent(TxtFirstName.getText(), TxtLastName.getText(), TxtUsername.getText(), 
				TxtPassword.getText(), TxtStreetNumber.getText(), TxtCity.getText(), TxtStreetName.getText(), TxtState.getText(), TxtZip.getText());
	}
	
	public void Cacnel(ActionEvent actionevent) {
		SwapScene(actionevent, "/view/StudentSearch.fxml", "Add Student");
	}
	
	public void SwapScene(ActionEvent actionevent, String resource, String SceneName){
		try {
			Parent secondRoot = FXMLLoader.load(getClass().getResource(resource));
			Scene secondScene = new Scene(secondRoot);
			Stage window = (Stage)((Node)actionevent.getSource()).getScene().getWindow();
			window.setScene(secondScene);
			window.setTitle(SceneName); 
			window.show();	
		}catch(IOException e) {
			InfoDisplays.displayGenericError("Could not swap to " + SceneName);
		}
	}

}
