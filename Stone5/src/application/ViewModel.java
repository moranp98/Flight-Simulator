package application;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public class ViewModel extends Observable implements Observer {

	SimulatorModel sm;
	FloatProperty heading,speed,alt;
	
	public ViewModel(SimulatorModel sm) {
		super();
		this.sm = sm;
		heading=new SimpleFloatProperty();
		speed=new SimpleFloatProperty();
		alt=new SimpleFloatProperty();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o==sm) {
			heading.set(sm.plane.get("heading"));
			speed.set(sm.plane.get("speed"));
			alt.set(sm.plane.get("alt"));
		}
		
	}
	
	public void connect(String ip,int port) {
		sm.connect(ip, port);
	}

	public int[][] calculate(String[][] array,int sx,int sy,int gx,int gy,int port) {
		String sol=sm.calculate(array, sx, sy, gx, gy,port);
		String[] solution=sol.split(",");
		int arr[][]=new int[array.length][array[0].length];
		int x=sx;
		int y=sy;
		for(int i=0;i<solution.length;i++) {
			//System.out.println(solution[i]);
			if(solution[i].equals("Left"))
				y--;
			else if(solution[i].equals("Down"))
				x++;
			else if(solution[i].equals("Right"))
				y++;
			else
				x--;
			arr[x][y]=1;
			
				
		}
		
		return arr;
	}


	public double autopilot(String intr) {
		intr.replace('"', ' ');
		String in[]=intr.split("\r\n");
		return sm.autopilot(in);
	}
}
