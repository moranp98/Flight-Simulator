package application;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class Joystick extends Pane{
	
	
	public void joystick() {
		
		Circle circle1=new Circle();
	    circle1.centerXProperty().set(120);
	    circle1.centerYProperty().set(130);
	    circle1.setRadius(100);
	    circle1.setFill(Color.LIGHTGRAY);
	    
	    Circle joy=new Circle();
	    joy.centerXProperty().set(120);
	    joy.centerYProperty().set(130);
	    joy.setRadius(50);
	    joy.setFill(Color.DIMGRAY);
	    joys(joy);
	    
	    Label ail=new Label("<- aileron ->");
	    ail.setLayoutX(60);
	    ail.setLayoutY(3);
	    ail.setFont(Font.font(20));
	    
	    Label ele=new Label("<- elevator ->");
	    ele.setLayoutX(170);
	    ele.setLayoutY(100);
	    ele.setFont(Font.font(20));
	    ele.setRotate(90);
	    
	    Slider rud=new Slider(-1, 1, 0);
	    rud.setLayoutX(55);
	    rud.setLayoutY(255);
	    
	    Label rudl=new Label("rudder");
	    rudl.setLayoutX(95);
	    rudl.setLayoutY(225);
	    rudl.setFont(Font.font(20));
	    
	    Slider thr=new Slider(0, 1, 0.5);
	    thr.setLayoutX(-90);
	    thr.setLayoutY(120);
	    thr.setRotate(90);
	    
	    Label thrl=new Label("throttle");
	    thrl.setLayoutX(-25);
	    thrl.setLayoutY(100);
	    thrl.setFont(Font.font(20));
	    thrl.setRotate(90);
	    
	    
	    this.getChildren().addAll(circle1,joy,ail,ele,rud,rudl,thr,thrl);

}
	
	private void joys(Circle joy) {
		
		double origX=joy.getCenterX();
		double origY=joy.getCenterY();
		
		joy.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				joy.setOnMouseDragged(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						double x=event.getX();
						double y=event.getY();
						
						if(x>=75&&x<=165)
							joy.setCenterX(event.getX());
						
						if(y>=85&&y<=175)
							joy.setCenterY(event.getY());
						
					}
				});
				
				joy.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						joy.setCenterX(origX);
						joy.setCenterY(origY);
						
					}
				});
				
			}

		});
		
		
	}
}
