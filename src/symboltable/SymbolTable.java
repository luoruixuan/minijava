package symboltable;
import java.util.*;


public class SymbolTable extends Symbol{
	public Hashtable<String, ClassSymbol> classes;
	String mainclass;
	public SymbolTable() {
		ClassSymbol O = new ClassSymbol("Object", null);
		mainclass = null;
		classes = new Hashtable<String, ClassSymbol>();
		classes.put("Object", O);
	}
}

