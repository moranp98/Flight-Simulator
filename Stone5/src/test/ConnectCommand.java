package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectCommand implements Command {

	int port;
	String ip;
	@Override
	public int doCommand(int index, String[] l, ConcurrentHashMap<String, Double> symTable) {
		index++;
		while(l[index]==""||l[index]==" ") {
			index++;
		}
		ip=l[index];
		index++;
		while(l[index]==""||l[index]==" ") {
			index++;
		}
		port=Integer.parseInt(l[index]);
		symTable.put("stopC",0.0);
		new Thread(()->run(symTable)).start();
		index++;
		
		
		return index;
	}
	
	private void run(ConcurrentHashMap<String, Double> symTable) {
		while(symTable.get("stopC")==0.0){
			try {
				Socket interpreter=new Socket(ip, port);
				PrintWriter out=new PrintWriter(interpreter.getOutputStream());
				while(symTable.get("stopC")==0.0){
					if(!symTable.containsKey("SimX"))
						continue;
					out.println("set SimX "+symTable.get("SimX"));
					out.println("set SimY "+symTable.get("SimY"));
					out.println("set SimZ "+symTable.get("SimZ"));
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

}
