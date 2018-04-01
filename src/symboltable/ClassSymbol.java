package symboltable;
import java.util.*;

import syntaxtree.Node;

public class ClassSymbol extends Symbol {
	ClassSymbol cls_super;
	Hashtable<String, VarSymbol> cls_var;
	Hashtable<String, MethodSymbol> cls_method;
	
	public ClassSymbol()
	{
		cls_var = new Hashtable<String, VarSymbol>();
		cls_method = new Hashtable<String, MethodSymbol>();
	}
	public ClassSymbol(String name, ClassSymbol Super)
	{
		cls_var = new Hashtable<String, VarSymbol>();
		cls_method = new Hashtable<String, MethodSymbol>();
		setName(name);
		setSuper(Super);
	}
	
	public void setSuper(ClassSymbol Super) { cls_super = Super; }
	public ClassSymbol getSuper() { return cls_super; }
	
	public void addVar(VarSymbol var) { cls_var.put(var.getName(), var); }
	public Enumeration<VarSymbol> varElements() { return cls_var.elements(); }
	
	public void addMethod(MethodSymbol method) { cls_method.put(method.getName(), method); }
	public Enumeration<MethodSymbol> methodElements() { return cls_method.elements(); }
}