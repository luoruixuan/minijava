import visitor.*;
import syntaxtree.*;
import symboltable.*;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;


public class Main{
	public static void main(String args[]) {
		SymbolTable ST = new SymbolTable();
		BuildSymbolTableVisitor V = new BuildSymbolTableVisitor();
		try {
			FileInputStream in = new FileInputStream(args[0]);
			Node root = new MiniJavaParser(in).Goal();
			root.accept(V, ST);
		}catch (ParseException e){
			e.printStackTrace();
		}catch (TokenMgrError e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		Hashtable<String, ClassSymbol> h = ST.classes;
		Enumeration i=h.keys();
		while(i.hasMoreElements()){
			String T = (String)i.nextElement();
			ClassSymbol C = h.get(T);
			System.out.println(C);
		}
	}
}