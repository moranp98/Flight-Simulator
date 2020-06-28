package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Simulator extends Canvas{

	String[][] simulatorData;
	double lat,lon,lot;
	int cRow,cCol;
	double x,y;
	boolean picked=false,pressedBtn=false;
	int gx,gy;
	double w,h;
	
	public void setSimulatorData(String[][] simulatorData, double lat, double lon, double lot) {
		this.simulatorData = simulatorData;
		this.lat = lat;
		this.lon = lon;
		this.lot = lot;
		cRow=0;
		cCol=0;
		redraw();
	}

	public void redraw() {
		if(simulatorData!=null) {
			double W=getWidth();
			double H=getHeight();
			w=W/(simulatorData[0].length/8);
			h=H/(simulatorData.length/8);
			
			GraphicsContext gc=getGraphicsContext2D();
			
			for(int i=0;i<simulatorData.length/8;i++) {
				for(int j=0;j<simulatorData[i].length/8;j++) {
					double hue = Color.GREEN.getHue() + (Color.RED.getHue() - Color.BLUE.getHue()) * 
							(Integer.parseInt(simulatorData[i][j]) - 100) / (1000 - 100) ;
					Color color = Color.hsb(hue, 1.0, 1.0);
					gc.setFill(color);
					gc.fillRect(j*w, i*h, w, h);
				}
			}
			
			//gc.setFill(Color.BLACK);
			//gc.fillRect(cCol*w, cRow*h, w, h);
			Image img=null;
			try {
				img=new Image(new FileInputStream("./resources/plane.png"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("bla");
			}
			ImageView iv = new ImageView(img);
			iv.setRotate(135);
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			Image rotatedImage = iv.snapshot(params, null);
			gc.drawImage(rotatedImage, cCol, cRow,w,h);
		}
	}
	
	public void drawLine(int[][] flightPath) {
		double W=getWidth();
		double H=getHeight();
		double w=W/(simulatorData[0].length/8);
		double h=H/(simulatorData.length/8);
		GraphicsContext gc=getGraphicsContext2D();
		gc.clearRect(0, 0, w, h);
		redraw();
		drawX(x, y);
		Image img=null;
		try {
			img=new Image(new FileInputStream("./resources/line4.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("bla");
		}
		for(int i=0;i<flightPath.length;i++)
			for(int j=0;j<flightPath[i].length;j++)
				if(flightPath[i][j]==1) {
					if(i==gx && j==gy)
						break;
					gc.drawImage(img, j*w, i*h, w, h);
				}
	}
	
	public void drawX(double ex,double ey) {
		GraphicsContext gc=getGraphicsContext2D();
		gc.clearRect(0, 0, w, h);
		redraw();
		Image img=null;
		try {
			img=new Image(new FileInputStream("./resources/X.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("bla");
		}
		x=ex;
		y=ey;
		gc.drawImage(img, x, y,w,h);
		picked=true;
		System.out.println("i is "+y/h+" j is "+x/w);
		gx=(int) (y/h);
		gy=(int) (x/w);
	}
}
