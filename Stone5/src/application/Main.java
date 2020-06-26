package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			SimulatorModel sm=new SimulatorModel();
			ViewModel vm=new ViewModel(sm);
			sm.addObserver(vm);		
			FXMLLoader fx=new FXMLLoader();
			BorderPane root = fx.load(getClass().getResource("Mainn.fxml").openStream());
			MainWindowController mwc=fx.getController();
			mwc.setVm(vm);
			vm.addObserver(mwc);
			Scene scene = new Scene(root,1200,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
		SimulatorModel.stop=true;
	}
	
	
}
