package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import test.TestSetter;

public class SimulatorModel extends Observable {
	
	static boolean stop=false;
	Random r=new Random();
	int PORT=5000+r.nextInt(1000);
	String command=null;
	public SimulatorModel() {
		plane=new ConcurrentHashMap<String, Float>();
		plane.put("heading", (float) 0);
		plane.put("speed", (float) 0);
		plane.put("alt", (float) 0);
		System.out.println("port is "+PORT);
		new Thread(()->runServer()).start();
		
	}

	ConcurrentHashMap<String, Float> plane;
	
	public void connect(String ip,int port) {
		new Thread(()->runClient(ip,port)).start();
	}


	private void runServer() {
		System.out.println("opening server...");
		try {
			ServerSocket srv=new ServerSocket(PORT);
			System.out.println("server is alive");
			srv.setSoTimeout(1000000000);
			
			while(!stop) {
				Socket client=srv.accept();
				System.out.println("server connected");
				BufferedReader in =new BufferedReader(new InputStreamReader(client.getInputStream()));
				String line=null;
				line=in.readLine();
				String[] data=line.split(",");
				plane.put("speed", Float.parseFloat(data[0]));
				plane.put("heading", Float.parseFloat(data[12]));
				plane.put("alt", Float.parseFloat(data[9]));
				setChanged();
				notifyAll();
				in.close();
				
			}
			srv.close();
			System.out.println("server was closed");
		} catch (IOException e) {
			System.out.println("server eror");
		}
	}
	
	private void runClient(String ip,int port) {
		while(!stop) {
		try {
			Socket sck=new Socket(ip, port);
			PrintWriter out=new PrintWriter(sck.getOutputStream());
			while(command!=null) {
				out.println(command);
				out.flush();
			}
			out.close();
			sck.close();
		} 
		catch (IOException e) {
			
		}
		}
	}

	public String calculate(String[][]array,int sx,int sy,int gx,int gy,int port) {
		String sol="";
		TestSetter.runServer(port);
		try {
			Socket s=new Socket("127.0.0.1", port);
			PrintWriter out=new PrintWriter(s.getOutputStream());
			BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			int i,j;
			for(i=0;i<array.length/8;i++) {
				for(j=0;j<(array[i].length-1)/8;j++) {
					out.print(array[i][j]+",");
					//System.out.print(array[i][j]+",");
					}
				out.println(array[i][j]);
				//System.out.println(array[i][j]);
			}
			out.println("end");
			//System.out.println("end");
			out.println(sx+","+sy);
			//System.out.println(sx+","+sy);
			out.println(gx+","+gy);
			//System.out.println(gx+","+gy);
			out.flush();
			sol=in.readLine();
			//System.out.println(sol);
			in.close();
			out.close();
			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestSetter.stopServer();
		return sol;
	}

}
