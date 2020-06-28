package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Scanner;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class MainWindowController implements Initializable, Observer{
	
	@FXML
	Simulator simulatorData;
	@FXML
	Joystick joystick;
	@FXML
	RadioButton manual,auto;
	@FXML
	Button connectBtn;
	@FXML
	Label heading,alt,speed;
	@FXML
	TextArea text;
	
	ViewModel vm;

	public void setVm(ViewModel vm) {
		this.vm = vm;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		simulatorData.setSimulatorData(null,(double)0,(double)0,(double)0);
		joystick.joystick();
		simulatorData.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				simulatorData.drawX(event.getX(), event.getY());
				if(simulatorData.pressedBtn) {
					int[][] sol=vm.calculate(simulatorData.simulatorData, simulatorData.cRow, simulatorData.cCol, simulatorData.gx, simulatorData.gy,porti);
					simulatorData.drawLine(sol);
				}
			}
		});
	}
	
	 String ipt="";
	 String portt="";
	 int porti;
	
	public void manSelected() {
		auto.setSelected(false);
		joystick.setDisable(false);
	}
	
	public void autoSelected() {
		manual.setSelected(false);
		joystick.setDisable(true);
		if(!text.getText().equals("")) {
			vm.autopilot(text.getText());
		}

	}
	
	public void connect() {

		heading.textProperty().bind(vm.heading.asString());
		alt.textProperty().bind(vm.alt.asString());
		speed.textProperty().bind(vm.speed.asString());
		
		
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
        l2.setFont(Font.font(15));
        
        TextField port=new TextField();
        port.maxWidth(10);
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
				vm.connect(ipt, Integer.parseInt(portt));
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	
	public void load() {
		FileChooser fc=new FileChooser();
		List<String[]> lines = new ArrayList<String[]>();
		double lat,lon,lot;
		fc.setTitle("open csv file");
		//fc.setInitialDirectory(new File("./resources"));
		fc.setSelectedExtensionFilter(new ExtensionFilter("cvs","cvs"));
		File cho=fc.showOpenDialog(heading.getScene().getWindow());
		try {
			BufferedReader buf=new BufferedReader(new FileReader(cho));
			String row=null;
			String[]data;
			try {
				row=buf.readLine();
				data=row.split(",");
				lon=Double.parseDouble(data[0]);
				lat=Double.parseDouble(data[1]);
				row=buf.readLine();
				data=row.split(",");
				lot=Double.parseDouble(data[0]);
				
				//System.out.println("the pos is "+lat+","+lon+" and the lot is "+lot);
				
				row=null;
				while((row=buf.readLine())!=null) {
					lines.add(row.split(","));
				}
				String[][] array = new String[lines.size()][0];
				lines.toArray(array);
				simulatorData.setSimulatorData(array, lat, lon, lot);
				
				buf.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void calculate() {
		if(simulatorData.picked==false)
			return;
		
		simulatorData.pressedBtn=true;
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox texts=new VBox(20);
        texts.setAlignment(Pos.CENTER);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        TextField port=new TextField();
        Label l2=new Label("port");
        l2.setFont(Font.font(15));
        dialogVbox.getChildren().addAll(l2);
        VBox.setMargin(l2, new Insets(1, 30, 1, 5));
        VBox.setMargin(port, new Insets(1, 90, 1, 1));
        texts.getChildren().addAll(port);
        Button btn=new Button("connect to server");
        btn.setAlignment(Pos.CENTER);
        BorderPane p=new BorderPane(texts, null, null, btn, dialogVbox);
        BorderPane.setMargin(btn, new Insets(1, 1, 1, 70));
        Scene dialogScene = new Scene(p, 300, 200);
        dialog.setScene(dialogScene);
        dialog.setTitle("connect to server");
        dialog.show();
       btn.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			porti=Integer.parseInt(port.getText());
			int[][] sol=vm.calculate(simulatorData.simulatorData, simulatorData.cRow, simulatorData.cCol, simulatorData.gx, simulatorData.gy,porti);
		    simulatorData.drawLine(sol);
		    dialog.close();
			
		}
	});
		
	}

	public void loadText() {
		FileChooser fc=new FileChooser();
		List<String> lines = new ArrayList<String>();
		fc.setTitle("open text file");
		fc.setSelectedExtensionFilter(new ExtensionFilter("TEXT files (*.txt)", "*.txt"));
		File cho=fc.showOpenDialog(heading.getScene().getWindow());
		 try {
		      Scanner myReader = new Scanner(cho);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        //t+="\r\n"+data;
		        //System.out.println(t);
		        //System.out.println("------");
		        text.appendText("\r\n"+data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      
		    }
		  }
}
