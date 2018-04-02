import syntaxtree.*;
import symboltable.*;
import java.io.FileInputStream;
import typecheck.TypeCheckVisitor;


public class Main{
	public static void main(String args[]) {
		//AutoTest tester = new AutoTest();
		//tester.run();
		
		SymbolTable ST = new SymbolTable();
		BuildSymbolTableVisitor V = new BuildSymbolTableVisitor();
		try {
			FileInputStream in = new FileInputStream(args[0]);
			Node root = new MiniJavaParser(in).Goal();
			root.accept(V, ST);
			TypeCheckVisitor TV = new TypeCheckVisitor();
			root.accept(TV, ST);
			System.out.println("Accpet.");
		}catch (ParseException e){
			e.printStackTrace();
		}catch (TokenMgrError e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}