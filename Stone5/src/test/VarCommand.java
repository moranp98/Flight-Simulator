package test;

import java.util.concurrent.ConcurrentHashMap;

public class VarCommand implements Command {

	@Override
	public int doCommand(int index, String[] l, ConcurrentHashMap<String, Double> symTable) {
		index+=2;
		String name=l[index];
		index++;
		symTable.put(name, 0.0);
		return index;
	}

}
