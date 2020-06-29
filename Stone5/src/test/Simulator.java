package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentHashMap;

import application.SimulatorModel;

public class Simulator {
	
	ConcurrentHashMap<String, Double> aircraft;
	//double simX,simY,simZ;
	private int port;
	private volatile boolean stop;
	SimulatorModel sm;
	
	public Simulator(int port,SimulatorModel sm) {
		this.port=port;
		this.sm=sm;
		aircraft=new ConcurrentHashMap<String, Double>();
		aircraft.put("/controls/flight/speedbrake", (double)0);
		aircraft.put("/controls/engines/current-engine/throttle", (double)0);
		
		sm.command.set("get /instrumentation/heading-indicator/offset-deg");
		aircraft.put("/instrumentation/heading-indicator/offset-deg", Double.parseDouble(sm.val.get()));
		sm.val.set(null);
		
		sm.command.set("get /instrumentation/airspeed-indicator/indicated-speed-kt");
		aircraft.put("/instrumentation/airspeed-indicator/indicated-speed-kt", Double.parseDouble(sm.val.get()));
		sm.val.set(null);
		
		sm.command.set("get /instrumentation/attitude-indicator/indicated-roll-deg");
		aircraft.put("/instrumentation/attitude-indicator/indicated-roll-deg", Double.parseDouble(sm.val.get()));
		sm.val.set(null);
		
		sm.command.set("get /instrumentation/attitude-indicator/internal-pitch-deg");
		aircraft.put("/instrumentation/attitude-indicator/internal-pitch-deg", Double.parseDouble(sm.val.get()));
		sm.val.set(null);
		
		
		aircraft.put("/controls/flight/rudder", (double)0);
		aircraft.put("/controls/flight/aileron", (double)0);
		aircraft.put("/controls/flight/elevator", (double)0);
		
		sm.command.set("get /instrumentation/altimeter/indicated-altitude-ft");
		aircraft.put("/instrumentation/altimeter/indicated-altitude-ft", Double.parseDouble(sm.val.get()));
		sm.val.set(null);
		
		new Thread(()->runServer()).start();
		new Thread(()->runClient()).start();
	}
	
	private void runClient(){
		while(!stop){
			try {
				Socket interpreter=new Socket("127.0.0.1", port+1);
				PrintWriter out=new PrintWriter(interpreter.getOutputStream());
				while(!stop){
					out.println(aircraft.get("/controls/flight/speedbrake")+","+aircraft.get("/controls/engines/current-engine/throttle")+","+
							aircraft.get("/instrumentation/heading-indicator/offset-deg")+","+
							aircraft.get("/instrumentation/airspeed-indicator/indicated-speed-kt")+","+
							aircraft.get("/instrumentation/attitude-indicator/indicated-roll-deg")+","+
							aircraft.get("/instrumentation/attitude-indicator/internal-pitch-deg")+","+
							aircraft.get("/controls/flight/rudder")+","+aircraft.get("/controls/flight/aileron")+","+
							aircraft.get("/controls/flight/elevator")+","+aircraft.get("/instrumentation/altimeter/indicated-altitude-ft"));
					out.flush();
					try {Thread.sleep(100);} catch (InterruptedException e1) {}
				}
				out.close();
				interpreter.close();
			} catch (IOException e) {
				try {Thread.sleep(1000);} catch (InterruptedException e1) {}
			}
		}
	}
	
	private void runServer(){
		try {
			ServerSocket server=new ServerSocket(port);
			server.setSoTimeout(1000);
			while(!stop){
				try{
					Socket client=server.accept();
					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String line=null;
					while(!(line=in.readLine()).equals("bye")){
						try{
							if(line.startsWith("set /controls/flight/speedbrake")) {
								double val=Double.parseDouble(line.split(" ")[2]);
								if(aircraft.get("/controls/flight/speedbrake")!=val) {
									aircraft.put("/controls/flight/speedbrake", Double.parseDouble(line.split(" ")[2]));
									sm.command.set("set /controls/flight/speedbrake "+val);
								}
							}
							if(line.startsWith("set /controls/engines/current-engine/throttle")) {
								double val=Double.parseDouble(line.split(" ")[2]);
								if(aircraft.get("/controls/engines/current-engine/throttle")!=val) {
									aircraft.put("/controls/engines/current-engine/throttle", Double.parseDouble(line.split(" ")[2]));
									sm.command.set("set /controls/engines/current-engine/throttle "+val);
								}
							}
							if(line.startsWith("set /controls/flight/rudder")) {
								double val=Double.parseDouble(line.split(" ")[2]);
								if(aircraft.get("/controls/flight/rudder")!=val) {
									aircraft.put("/controls/flight/rudder", Double.parseDouble(line.split(" ")[2]));
									sm.command.set("set /controls/flight/rudder "+val);
								}
							}
							if(line.startsWith("set /controls/flight/aileron")) {
								double val=Double.parseDouble(line.split(" ")[2]);
								if(aircraft.get("/controls/flight/aileron")!=val) {
									aircraft.put("/controls/flight/aileron", Double.parseDouble(line.split(" ")[2]));
									sm.command.set("set /controls/flight/aileron "+val);
								}
							}
							if(line.startsWith("set /controls/flight/elevator")) {
								double val=Double.parseDouble(line.split(" ")[2]);
								if(aircraft.get("/controls/flight/elevator")!=val) {
									aircraft.put("/controls/flight/elevator", Double.parseDouble(line.split(" ")[2]));
									sm.command.set("set /controls/flight/elevator "+val);
								}
							}
							
						}catch(NumberFormatException e){}
					}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}
			server.close();
		} catch (IOException e) {}
	}

	public void close() {
		stop=true;
	}
}
