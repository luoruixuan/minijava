package symboltable;

public class VarSymbol extends Symbol {
	String var_type;
	
	public VarSymbol ()
	{
	}
	public VarSymbol (String name, String type)
	{
		setName(name);
		var_type = type;
	}
	
	public void setType(String type)
	{
		var_type = type;
	}
	public String getType()
	{
		return var_type;
	}
	public String toString() {return var_type + ": "+ sym_name;}
}
