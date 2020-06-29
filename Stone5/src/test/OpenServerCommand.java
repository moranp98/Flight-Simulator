package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentHashMap;

public class OpenServerCommand implements Command {

	    int port;
		int times;
	@Override
	public int doCommand(int index, String[] l, ConcurrentHashMap<String, Double> symTable) {
		
		index++;
		while(l[index]==""||l[index]==" ") {
			index++;
		}
		port=Integer.parseInt(l[index]);
		index++;
		while(l[index]==""||l[index]==" ") {
			index++;
		}
		times=Integer.parseInt(l[index]);
		symTable.put("stop", 0.0);
		symTable.put("/controls/flight/speedbrake", 0.0);
		symTable.put("/controls/engines/current-engine/throttle", 0.0);
		symTable.put("/instrumentation/heading-indicator/offset-deg", 0.0);
		symTable.put("/instrumentation/airspeed-indicator/indicated-speed-kt", 0.0);
		symTable.put("/instrumentation/attitude-indicator/indicated-roll-deg", 0.0);
		symTable.put("/instrumentation/attitude-indicator/internal-pitch-deg", 0.0);
		symTable.put("/controls/flight/rudder", 0.0);
		symTable.put("/controls/flight/aileron", 0.0);
		symTable.put("/controls/flight/elevator", 0.0);
		symTable.put("/instrumentation/altimeter/indicated-altitude-ft", 0.0);
		new Thread(()->run(symTable)).start();
		index++;
		
		return index;
	}
	
	private void run(ConcurrentHashMap<String, Double> symTable) {
		try {
			ServerSocket server=new ServerSocket(port);
			server.setSoTimeout(times*100);
			while(symTable.get("stop")==0.0){
				try{
					Socket client=server.accept();
					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String line=null;
					line=in.readLine();
					String [] data=line.split(",");
						try{
								symTable.put("/controls/flight/speedbrake", Double.parseDouble(data[0]));
								symTable.put("/controls/engines/current-engine/throttle", Double.parseDouble(data[1]));
								symTable.put("/instrumentation/heading-indicator/offset-deg", Double.parseDouble(data[2]));
								symTable.put("/instrumentation/airspeed-indicator/indicated-speed-kt", Double.parseDouble(data[3]));
								symTable.put("/instrumentation/attitude-indicator/indicated-roll-deg", Double.parseDouble(data[4]));
								symTable.put("/instrumentation/attitude-indicator/internal-pitch-deg", Double.parseDouble(data[5]));
								symTable.put("/controls/flight/rudder", Double.parseDouble(data[6]));
								symTable.put("/controls/flight/aileron", Double.parseDouble(data[7]));
								symTable.put("/controls/flight/elevator", Double.parseDouble(data[8]));
								symTable.put("/instrumentation/altimeter/indicated-altitude-ft", Double.parseDouble(data[9]));
						}catch(NumberFormatException e){}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}
			server.close();
		} catch (IOException e) {}
		
	}

}
