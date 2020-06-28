package test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MyInterpreter {

	public static double interpret(String[] lines){
		ConcurrentHashMap<String,Double> symTable=new ConcurrentHashMap<String, Double>();
		HashMap<String,Command> mapCommands=new HashMap<String, Command>();
		symTable.put("returnVal",0.0);
		mapCommands.put("openDataServer", new OpenServerCommand());
		mapCommands.put("connect", new ConnectCommand());
		mapCommands.put("return", new ReturnCommand());
		mapCommands.put("var", new VarCommand());
		parser p=new parser(mapCommands, symTable);
		p.pars(lines);
		return symTable.get("returnVal");
	}
}
