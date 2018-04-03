package symboltable;
import java.util.*;

public class ClassSymbol extends Symbol {
	int cls_depth;
	String cls_super_name;
	ClassSymbol cls_super;
	Hashtable<String, VarSymbol> cls_var;
	Hashtable<String, MethodSymbol> cls_method;
	
	public ClassSymbol()
	{
		cls_var = new Hashtable<String, VarSymbol>();
		cls_method = new Hashtable<String, MethodSymbol>();
	}
	public ClassSymbol(String name, String Super)
	{
		cls_var = new Hashtable<String, VarSymbol>();
		cls_method = new Hashtable<String, MethodSymbol>();
		setName(name);
		cls_super_name = Super;
		//setSuper(Super);
		
		//cls_depth = (cls_super == null) ? 0 : (cls_super.cls_depth+1);
	}
	
	public void setName(String name) {
		sym_name = name;
		VarSymbol This = new VarSymbol("this", name);
		cls_var.put("this", This);
	}
	
	public void setSuper(ClassSymbol Super) { cls_super = Super; }
	public ClassSymbol getSuper() { return cls_super; }
	
	public void addVar(VarSymbol var) { 
		if (cls_var.containsKey(var.getName())) {
			System.out.println("Variable duplicate defination.");
			System.exit(0);
		}
		cls_var.put(var.getName(), var); 
	}
	public Enumeration<VarSymbol> varElements() { return cls_var.elements(); }
	public boolean hasVar(String var) {
		if (cls_var.containsKey(var)) return true;
		if (getSuper() == null) return false;
		return getSuper().hasVar(var);
	}
	public VarSymbol getVar(String var) {
		if (cls_var.containsKey(var))
			return cls_var.get(var);
		else
			return getSuper().getVar(var);
	}
	
	public void addMethod(MethodSymbol method) { 
		if (cls_method.containsKey(method.getName())) {
			System.out.println("Method duplicate defination.");
			System.exit(0);
		}
		cls_method.put(method.getName(), method); 
		}
	public Enumeration<MethodSymbol> methodElements() { return cls_method.elements(); }
	
	public boolean TryAccessObject() {
		if (sym_name.equals("Object")) return true;
		ClassSymbol Super = getSuper();
		while(true) {
			if (Super.getName().equals("Object")) return true;
			if (Super.getName().equals(sym_name)) return false;
			Super = Super.getSuper();
		}
	}
	
	// just for debug
	public String toString() {
		String super_name="null";
		if (cls_super != null) {
			super_name = cls_super.getName();
		}
		String ret = sym_name + " extends " + super_name + "\n{\nVariable:\n";
		Enumeration<VarSymbol> i = varElements();
		while (i.hasMoreElements()) {
			ret = ret + i.nextElement() + "\n";
		}
		ret = ret + "Method:\n";
		Enumeration<MethodSymbol> j = methodElements();
		while (j.hasMoreElements()) {
			ret = ret + j.nextElement() + "\n";
		}
		return ret+"}";
	}
}
