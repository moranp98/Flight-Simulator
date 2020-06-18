package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class MainWindowController implements Initializable{
	
	@FXML
	Simulator simulatorData;
	@FXML
	Joystick joystick;
	@FXML
	RadioButton manual,auto;
	@FXML
	Button connectBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		simulatorData.setSimulatordata(null);
		joystick.joystick();
		
	}
	
	 String ipt="";
	 String portt="";
	
	public void manSelected() {
		auto.setSelected(false);
		joystick.setDisable(false);
	}
	
	public void autoSelected() {
		manual.setSelected(false);
		joystick.setDisable(true);

	}
	
	public void connect() {
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        
        
        VBox texts=new VBox(20);
        texts.setAlignment(Pos.CENTER);
   
        
        Label l1=new Label("ip");
        l1.setFont(Font.font(15));
        
        TextField ip=new TextField();
        ip.maxWidth(10);
        keyPressedIP(ip);

        
        
        Label l2=new Label("port");
        l1.setFont(Font.font(15));
        
        TextField port=new TextField();
        ip.maxWidth(10);
        keyPressedPor(port);
        
        
        dialogVbox.getChildren().addAll(l1,l2);
        VBox.setMargin(l1, new Insets(1, 30, 1, 1));
        VBox.setMargin(l2, new Insets(1, 30, 1, 5));
        VBox.setMargin(ip, new Insets(1, 90, 1, 1));
        VBox.setMargin(port, new Insets(1, 90, 1, 1));
        texts.getChildren().addAll(ip,port);
        
        Button btn=new Button("connect to server");
        btn.setAlignment(Pos.CENTER);
        btnPressed(btn,ip,port);

        
        BorderPane p=new BorderPane(texts, null, null, btn, dialogVbox);
        BorderPane.setMargin(btn, new Insets(1, 1, 1, 70));

        
        Scene dialogScene = new Scene(p, 300, 200);
        dialog.setScene(dialogScene);
        dialog.setTitle("connect to simulator");
        dialog.show();
        
    }
	
	private void keyPressedIP(TextField ip) {
		ip.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				String c=event.getText();
				try {
				Integer.parseInt(c);
				ipt=ipt.concat(c);
				//System.out.println(ipt);
				}
				catch(NumberFormatException e) {
					if(event.getCode()==KeyCode.BACK_SPACE)
						ipt=ip.getText();
					else if(!c.equals(".")) {
						ip.setText(ipt);
						Alert a=new Alert(AlertType.ERROR);
						int o=ip.getLength();
						//System.out.println(o);
						a.setHeaderText("only digits and dots!");
						a.show();
						ip.positionCaret(o);
					}
					else {
						ipt=ipt.concat(c);
					}
				}
			}
		});
	}

	private void keyPressedPor(TextField port) {
		port.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				String c=event.getText();
				try {
				Integer.parseInt(c);
				portt=portt.concat(c);
				}
				catch(NumberFormatException e) {
					if(event.getCode()==KeyCode.BACK_SPACE)
						portt=port.getText();
					else {
						port.setText(portt);
						Alert a=new Alert(AlertType.ERROR);
						int o=port.getLength();
						a.setHeaderText("only digits!");
						a.show();
						port.positionCaret(o);
					}
					}
				}
		});
	}

	private void btnPressed(Button btn,TextField ip,TextField port) {
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ipt=ip.getText();
				portt=port.getText();
				
			}
		});
	}


}
