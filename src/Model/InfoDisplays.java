package Model;

import javafx.scene.control.Alert;

public class InfoDisplays {
	
	public static void displayGenericError(String info) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(info);
		alert.showAndWait();
	}
	
	public static void displayGenericInformation(String info) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(info);
		alert.showAndWait();
	}
	
}
