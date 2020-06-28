package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EqualComperatorCommand implements Command {

	@Override
	public int doCommand(int index, String[] l, ConcurrentHashMap<String, Double> symTable) {
		int oldind=index - 1;
		index++;
		while(l[index].trim().length()==0 || l[index]=="") {
			index++;
		}
		if(l[index]=="bind")
			index=binder(index,l,symTable);
		else {
			while(l[oldind].trim().length()==0 || l[oldind]=="") {
				oldind--;
		}
			String var=l[oldind];
			List<String> li=new ArrayList<String>();
			
		}
			
		return index;
	}
	
	private int binder(int index, String[] l, ConcurrentHashMap<String, Double> symTable) {
		
		return index;
	}

}
