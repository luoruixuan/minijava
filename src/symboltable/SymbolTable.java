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
	public void setStatus(String C, String M) {
		presentClass = C;
		presentMethod = M;
	}
	public void setClass(String C) {
		presentClass = C;
	}
	public void setMethod(String M) {
		presentMethod = M;
	}
	
	public String getType(String var) {
		ClassSymbol cls = classes.get(presentClass);
		MethodSymbol method = cls.cls_method.get(presentMethod);
		
		if (!method.local_var.contains(var)) {
			System.out.println("Using variable "+var+" before defination.");
			System.exit(0);
		}
		VarSymbol varsym = method.local_var.get(var);
		
		return varsym.getType();
	}
	
	public boolean hasClasses(String C) {
		if (classes.contains(C)) return true;
		if (C == "int") return true;
		if (C == "boolean") return true;
		if (C == "int*") return true;
		return false;
	}
	public ClassSymbol getLca(String C1, String C2) {
		if (!hasClasses(C1)) {
			System.out.println("BUG: No such class symbol: "+C1);
			System.exit(1);
		}
		if (!hasClasses(C2)) {
			System.out.println("BUG: No such class symbol: "+C2);
			System.exit(1);
		}
		
		ClassSymbol cls1, cls2;
		cls1 = classes.contains(C1) ? classes.get(C1) : classes.get("Object");
		cls2 = classes.contains(C2) ? classes.get(C2) : classes.get("Object");
		while(cls1.cls_depth > cls2.cls_depth) cls1 = cls1.getSuper();
		while(cls1.cls_depth < cls2.cls_depth) cls2 = cls2.getSuper();
		if (cls1.cls_depth != cls2.cls_depth || cls1.getName() != cls2.getName()) {
			System.out.println("BUG: Class "+C1+" and class "+C2+" are not in the same tree!");
			System.exit(1);	
		}
		return cls1;
	}
	
	public boolean isAnsistor(String C1, String C2) {
		ClassSymbol lca = getLca(C1, C2);
		return lca.getName() == C1 || lca.getName() == C2;
	}
	
	public boolean checkMethodArgsType(String C, String M, String Arg_types) {
		ClassSymbol cls = classes.get(C);
		MethodSymbol method = cls.cls_method.get(M);
		String[] arg_types = Arg_types.split(" ");
		int length = arg_types.length;
		
		if (length != method.argSize()) return false;
		for (int i = 0; i < length; i++)
			if (arg_types[i] != method.argElementAt(i)) return false;
		return true;
	}
}

