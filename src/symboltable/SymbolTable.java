package symboltable;
import java.util.*;


public class SymbolTable extends Symbol{
	public Hashtable<String, ClassSymbol> classes;
	String mainclass;
	
	// For type check
	public String presentClass, presentMethod;
	
	public SymbolTable() {
		ClassSymbol O = new ClassSymbol("Object", null);
		mainclass = null;
		classes = new Hashtable<String, ClassSymbol>();
		classes.put("Object", O);
	}
	public void SetStatus(String C, String M) {
		presentClass = C;
		presentMethod = M;
	}
	public void SetClass(String C) {
		presentClass = C;
	}
	public void SetMethod(String M) {
		presentMethod = M;
	}
	public boolean isAnsistor(String C1, String C2) {
		return true;
	}
}

