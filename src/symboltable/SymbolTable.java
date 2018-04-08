package symboltable;
import java.util.*;


public class SymbolTable extends Symbol{
	public Hashtable<String, ClassSymbol> classes;
	public Hashtable<String, Integer> degree;
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
		
		if (!cls.hasVar(var) && !method.hasVar(var)) {
			System.out.println("Using variable \""+var+"\" before defination.");
			System.exit(0);
		}
		VarSymbol varsym;
		if (method.hasVar(var))
			varsym = method.local_var.get(var);
		else
			varsym = cls.getVar(var);
		
		return varsym.getType();
	}
	
	public boolean varIsInitialized(String var) {
		ClassSymbol cls = classes.get(presentClass);
		MethodSymbol method = cls.cls_method.get(presentMethod);
		
		if (!cls.hasVar(var) && !method.hasVar(var)) {
			System.out.println("Using variable \""+var+"\" before defination.");
			System.exit(0);
		}
		VarSymbol varsym;
		if (method.hasVar(var))
			varsym = method.local_var.get(var);
		else
			varsym = cls.getVar(var);
		
		return varsym.isInitialized();
	}
	
	public void varInitialize(String var) {
		ClassSymbol cls = classes.get(presentClass);
		MethodSymbol method = cls.cls_method.get(presentMethod);
		
		if (!cls.hasVar(var) && !method.hasVar(var)) {
			System.out.println("Using variable \""+var+"\" before defination.");
			System.exit(0);
		}
		VarSymbol varsym;
		if (method.hasVar(var))
			varsym = method.local_var.get(var);
		else
			varsym = cls.getVar(var);
		varsym.initialize();
	}
	
	public boolean hasClasses(String C) {
		if (classes.containsKey(C)) return true;
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
		cls1 = classes.containsKey(C1) ? classes.get(C1) : classes.get("Object");
		cls2 = classes.containsKey(C2) ? classes.get(C2) : classes.get("Object");
		while(cls1.cls_depth > cls2.cls_depth) cls1 = cls1.getSuper();
		while(cls1.cls_depth < cls2.cls_depth) cls2 = cls2.getSuper();
		if (cls1.cls_depth != cls2.cls_depth || !cls1.getName().equals(cls2.getName())) {
			System.out.println("BUG: Class "+C1+" and class "+C2+" are not in the same tree!");
			System.exit(1);	
		}
		return cls1;
	}
	
	public boolean isAnsistor(String C1, String C2) {
		if (C1.equals(C2)) return true;
		ClassSymbol lca = getLca(C1, C2);
		return lca.getName().equals(C1);
	}
	
	public boolean checkMethodArgsType(String C, String M, String Arg_types) {
		ClassSymbol cls = classes.get(C);
		MethodSymbol method = cls.cls_method.get(M);

		if (Arg_types.equals("")) Arg_types = " ";
		String[] arg_types = Arg_types.split(" ");
		int length = arg_types.length;
		
		if (length != method.argSize()) return false;
		for (int i = 0; i < length; i++)
			if (!isAnsistor(method.argElementAt(i), arg_types[i])) return false;
		return true;
	}
	public String getMethodType(String C, String M) {
		ClassSymbol cls = classes.get(C);
		MethodSymbol method = cls.cls_method.get(M);
		
		return method.getType();
	}
	
	public void createSymbolTree() {
		Enumeration<ClassSymbol> i = classes.elements();
		while(i.hasMoreElements()) {
			ClassSymbol temp = i.nextElement();
			if (temp.getName().equals("Object")) continue;
			ClassSymbol Super = classes.get(temp.cls_super_name);
			temp.setSuper(Super);
		}
		
		i = classes.elements();
		while(i.hasMoreElements()) {
			ClassSymbol temp = i.nextElement();
			if (!temp.TryAccessObject()) {
				System.out.println("Cyclic inheritance found in class \""+temp.getName()+"\"");
				System.exit(0);
			}
		}
		
		i = classes.elements();
		while(i.hasMoreElements()) {
			ClassSymbol temp = i.nextElement();
			if (!temp.CheckOverLoading()) {
				System.out.println("Function overloaded.");
				System.exit(0);
			}
		}
	}
	
	// just for debug
	public String toString() {
		String ret = "";
		Enumeration<ClassSymbol> i = classes.elements();
		while(i.hasMoreElements()){
			ClassSymbol C = i.nextElement();
			ret = ret + C + "\n";
		}
		return ret;
	}
}

