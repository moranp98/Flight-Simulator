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
		symTable.put("SimX", 0.0);
		symTable.put("SimY", 0.0);
		symTable.put("SimZ", 0.0);
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
								symTable.put("SimX", Double.parseDouble(data[0]));
								symTable.put("SimY", Double.parseDouble(data[1]));
								symTable.put("SimZ", Double.parseDouble(data[2]));
						}catch(NumberFormatException e){}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}
			server.close();
		} catch (IOException e) {}
		
	}

}
