package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

public class SimulatorServer {

	public boolean stop=false;
	private int x,y,heading=360,speed,alt,port;
	
	
	public SimulatorServer() {
		server();
	}

	private void runServer() {
		try {
			ServerSocket server=new ServerSocket(port);
			server.setSoTimeout(10000);
			while(!stop){
				try{
					Socket client=server.accept();
					new Thread(()->runClient(client)).start();
					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String line=null;
					while(!(line=in.readLine()).equals("bye")){
						try{
							if(line.startsWith("set x"))
								x=Integer.parseInt(line.split(" ")[2]);
							if(line.startsWith("set y"))
								y=Integer.parseInt(line.split(" ")[2]);
							if(line.startsWith("set heading"))
								heading=Integer.parseInt(line.split(" ")[2]);
							if(line.startsWith("set speed"))
								speed=Integer.parseInt(line.split(" ")[2]);
							if(line.startsWith("set alt"))
								alt=Integer.parseInt(line.split(" ")[2]);
						}catch(NumberFormatException e){}
					}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}
			server.close();
		} catch (IOException e) {}
	}
	
	private void runClient(Socket client){
		while(!stop){
			try {
				PrintWriter out=new PrintWriter(client.getOutputStream());
				while(!stop){
					out.println(x+","+y+","+heading+","+speed+","+alt);
					out.flush();
					try {Thread.sleep(40);} catch (InterruptedException e1) {}
				}
				out.close();
			} catch (IOException e) {
				try {Thread.sleep(1000);} catch (InterruptedException e1) {}
			}
		}
	}
	
	private void server() {
		Random r=new Random();
		port=r.nextInt(1001)+5000;
		System.out.println("the port is "+port);
		new Thread(()->runServer()).start();

	}
}
