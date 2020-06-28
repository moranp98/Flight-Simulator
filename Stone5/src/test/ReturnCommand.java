package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import Expression.ExpressionConvertor;

public class ReturnCommand implements Command {

	@Override
	public int doCommand(int index, String[] l, ConcurrentHashMap<String, Double> symTable) {

		
		List<String> equ =new ArrayList<String>();
		index++;
		while(index<l.length) {
			if(l[index]!=""&&l[index].trim().length()!=0) {
				if(symTable.containsKey(l[index]))
					l[index]=symTable.get(l[index]).toString();
				equ.add(l[index]);
			}
			index++;
		}

		String exp=ExpressionConvertor.infixToPostfix(equ);
		//System.out.println(exp);
		double res=ExpressionConvertor.calculatePostfix(exp);
		//System.out.println(res);
		symTable.put("returnVal", res);
		index++;
		

		
		return index;
	}

}
