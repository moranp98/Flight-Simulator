package test;

public class lexer {

	public static String[] lex(String[] lines) {
		String[] st= new String[0];
		for(String s:lines) {
			String[] tokens=s.split("(?<=[-+=/()])|(?=[-+=/()])| |\"");
			String[] tmp=new String[st.length+tokens.length];
			for(int i=0;i<st.length;i++) {
				tmp[i]=st[i];
			}
			for(int i=st.length;i<tokens.length;i++) {
				tmp[i]=tokens[i];
				//System.out.println(tokens[i]);
			}
			st=tmp.clone();
		}
		
		
		
		return st;
	}


}
