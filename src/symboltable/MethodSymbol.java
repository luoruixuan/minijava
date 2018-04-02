package symboltable;
import java.util.*;

public class MethodSymbol extends Symbol {
	String method_type;
	Vector<String> args_type;
	Hashtable<String, VarSymbol> local_var;
	
	MethodSymbol()
	{
		args_type = new Vector<String>();
		local_var = new Hashtable<String, VarSymbol>();
	}
	MethodSymbol(String name, String ret_type)
	{
		args_type = new Vector<String>();
		local_var = new Hashtable<String, VarSymbol>();
		setName(name);
		setType(ret_type);
	}
	
	void setType(String ret_type) { method_type = ret_type; }
	String getType() { return method_type; }
	
	void addArg(VarSymbol var)
	{
		args_type.addElement(var.getType());
		local_var.put(var.getName(), var);
	}
	public Enumeration<String> argElements() { return args_type.elements(); }
	
	void addVar(VarSymbol var) { local_var.put(var.getName(), var); }
	public Enumeration<VarSymbol> varElements() { return local_var.elements(); }
	public String toString() {
		String ret = method_type + " " + sym_name + "(";
		Enumeration i = argElements();
		while (i.hasMoreElements()) {
			ret = ret + i.nextElement() + ", ";
		}
		ret = ret.substring(0,ret.length()-2)+")\n{\n";
		i = varElements();
		while (i.hasMoreElements()) {
			ret = ret + i.nextElement() + "\n";
		}
		return ret+"}";
	}
}
