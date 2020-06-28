package test;

import java.util.concurrent.ConcurrentHashMap;

public interface Command {

	public int doCommand(int index,String[] l,ConcurrentHashMap<String,Double> symTable);
	
}
