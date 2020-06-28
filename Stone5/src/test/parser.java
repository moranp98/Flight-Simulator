package test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class parser {

	HashMap<String,Command> map;
	ConcurrentHashMap<String,Double> symTable;
	
	public parser(HashMap<String,Command> map,ConcurrentHashMap<String, Double> s) {
		super();
		this.map = map;
		symTable=s;
	}
	
	
	public void pars(String [] lines) {
		String[] lex=lexer.lex(lines);
		for(int i=0;i<lex.length;) {
			if(lex[i]=="") {
				i++;
				continue;
			}
			Command c = map.get(lex[i]);
			i=c.doCommand(i, lex,symTable);
		}
	}
	
}
