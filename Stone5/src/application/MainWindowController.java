package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainWindowController implements Initializable{
	
	@FXML
	Simulator simulatorData;
	@FXML
	Joystick joystick;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		simulatorData.setSimulatordata(null);
		joystick.joystick();
		
	}
	
	
}
