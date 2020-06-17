package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Simulator extends Canvas{

	int[][] simulatorData;

	public void setSimulatordata(int[][] simulatordata) {
		simulatorData = simulatordata;
		redraw();
	}
	
	public void redraw() {
		if(simulatorData!=null) {
			double W=getWidth();
			double H=getHeight();
			double w=W/simulatorData[0].length;
			double h=H/simulatorData.length;
			
			GraphicsContext gc=getGraphicsContext2D();
			
			for(int i=0;i<simulatorData.length;i++) {
				for(int j=0;j<simulatorData[i].length;j++) {
					if(simulatorData[i][j]!=0)
						gc.fillRect(j*w, i*h, w, h);
				}
			}
		}
	}
	
}
