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
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
//			Circle circle1=new Circle();
//		    circle1.centerXProperty().set(705);
//		    circle1.centerYProperty().set(170);
//		    circle1.setRadius(70);
//		    //circle1.setStroke(Color.RED);
//		    circle1.setFill(Color.GRAY);
//		    circle1.getStrokeDashArray().addAll(2d, 21d);
//		    circle1.setStrokeWidth(3);
//		    root.getChildren().addAll(circle1);
			Scene scene = new Scene(root,900,320);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
