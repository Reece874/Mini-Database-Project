package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.StudentDBModel;
import Model.InfoDisplays;
import Model.LazySingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditStudentController implements Initializable{
	
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
	private Button BtnEditStudent;
	
	@FXML
	private Button BtnCancel;
	
	StudentDBModel connect = new StudentDBModel();
	
	public void Edit(ActionEvent actionevent) {
		try {
			connect.editStudent(LazySingleton.getInstance().getID(), 
					TxtFirstName.getText(), TxtLastName.getText(), TxtUsername.getText(), TxtPassword.getText(), 
					TxtStreetNumber.getText(), TxtCity.getText(), TxtStreetName.getText(), TxtState.getText(), TxtZip.getText());
		}catch(Exception e) {
			InfoDisplays.displayGenericError("Could not edit student");
		}
	}
	
	public void Cacnel(ActionEvent actionevent) {
		SwapScene(actionevent, "/view/StudentSearch.fxml", "Student Search");
		LazySingleton.getInstance().setID(0);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String[] elms = connect.items(LazySingleton.getInstance().getID());
		TxtFirstName.setText(elms[0]);
		TxtLastName.setText(elms[1]);
		TxtUsername.setText(elms[2]);
		TxtPassword.setText(elms[3]);
		TxtStreetNumber.setText(elms[4]);
		TxtStreetName.setText(elms[5]);
		TxtCity.setText(elms[6]);
		TxtState.setText(elms[7]);
		TxtZip.setText(elms[8]);
		
	}

}
