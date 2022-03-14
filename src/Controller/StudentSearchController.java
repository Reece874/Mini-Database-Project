package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.StudentDBModel;
import Model.InfoDisplays;
import Model.LazySingleton;
import Model.Replace;
import Model.TableItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StudentSearchController implements Initializable{
	
	@FXML
	private Label LblAllStudents; 

	@FXML
	private Label LblSearchResults; 
	
	@FXML
	private Label LblSearch; 
	
	@FXML
	private TextField TxtFirstName; 
	
	@FXML
	private TextField TxtLastName;
	
	@FXML
	private TextField TxtUsername;
	
	@FXML
	private TextField TxtID;
	
	@FXML
	private TextField TxtStreetNum;
	
	@FXML
	private TextField TxtStreetName;
	
	@FXML
	private TextField TxtCity;
	
	@FXML
	private TextField TxtState;
	
	@FXML
	private TextField TxtZip;
	
	@FXML
	private TableView<TableItem> TblSearchRes;
	
	@FXML 
	private TableColumn<TableItem, String> ColStudentID;
	
	@FXML 
	private TableColumn<TableItem, String> ColFirstName;
	
	@FXML 
	private TableColumn<TableItem, String> ColLastName;
	
	@FXML 
	private TableColumn<TableItem, String> ColUsername;
	
	@FXML 
	private TableColumn<TableItem, String> ColPassword;
	
	@FXML 
	private TableColumn<TableItem, String> ColAddress;
	
	@FXML
	private Button BtnSearch; 
	
	@FXML
	private Button BtnAddStudent; 
	
	@FXML
	private Button BtnRemoveStudent; 
	
	@FXML
	private Button BtnEditStudent; 
	
	
	StudentDBModel connect = new StudentDBModel();
	
	public void OnSearch(ActionEvent actionevent) {
		TblSearchRes.setItems(connect.searchParams(Replace.replaceBlankWithNull(TxtID.getText()), Replace.replaceBlankWithNull(TxtFirstName.getText()), 
				Replace.replaceBlankWithNull(TxtLastName.getText()), Replace.replaceBlankWithNull(TxtUsername.getText()), Replace.replaceBlankWithNull(TxtStreetNum.getText()),
				Replace.replaceBlankWithNull(TxtStreetName.getText()), Replace.replaceBlankWithNull(TxtCity.getText()), Replace.replaceBlankWithNull(TxtState.getText()), 
				Replace.replaceBlankWithNull(TxtZip.getText())));
	}
	
	public void OnBtnAddStudent(ActionEvent actionevent) {
		SwapScene(actionevent, "/view/AddStudent.fxml", "Add Student");
	}
	
	public void OnBtnEditStudent(ActionEvent actionEvent) {
		if(TblSearchRes.getSelectionModel().getSelectedItem() != null) {
			LazySingleton.getInstance().setID(Integer.parseInt(TblSearchRes.getSelectionModel().getSelectedItem().getID()));
			SwapScene(actionEvent, "/view/EditStudent.fxml", "Edit Student");
		}else {
			InfoDisplays.displayGenericInformation("No Student is Selected");
		}
	}
	
	public void OnBtnRemove(ActionEvent actionevent) {
		if(TblSearchRes.getSelectionModel().getSelectedItem() != null) {
			connect.removeStudent(Integer.parseInt(TblSearchRes.getSelectionModel().getSelectedItem().getID()));
			OnSearch(actionevent);
		}else {
			InfoDisplays.displayGenericInformation("No Student is Selected");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ColStudentID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		ColFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
		ColLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
		ColUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
		ColPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
		ColAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
		
		if(connect.isDBConnected()) {
			TblSearchRes.setItems(connect.searchParams(null, null, null, null, null, null, null, null, null));
		}else {
			InfoDisplays.displayGenericError("Database is not Connected");
		}
		
	}
	
	public void SwapScene(ActionEvent actionEvent, String resource, String SceneName){
		try {
			Parent secondRoot = FXMLLoader.load(getClass().getResource(resource));
			Scene secondScene = new Scene(secondRoot);
			Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
			window.setScene(secondScene);
			window.setTitle(SceneName); 
			window.show();	
		}catch(IOException e) {
			InfoDisplays.displayGenericError("Could not swap to " + SceneName);
		}
	}
	
}
