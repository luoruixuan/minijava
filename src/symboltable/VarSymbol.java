package symboltable;

public class VarSymbol extends Symbol {
	String var_type;
	
	VarSymbol ()
	{
	}
	VarSymbol (String name, String type)
	{
		setName(name);
		var_type = type;
	}
	
	void setType(String type)
	{
		var_type = type;
	}
	String getType()
	{
		return var_type;
	}
}
