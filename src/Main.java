import visitor.*;
import syntaxtree.*;
import java.io.FileInputStream;


class MyVisitor extends DepthFirstVisitor {
	public void visit(VarDeclaration n) {
		Identifier id = (Identifier)n.f1;
		System.out.println("VarName:"+id.f0.toString());
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
	}
}

public class Main{
	public static void main(String args[]) {
		try {
			FileInputStream in = new FileInputStream(args[0]);
			Node root = new MiniJavaParser(in).Goal();
			root.accept(new MyVisitor());
			System.out.println(((Node)root).getClass().getName());
		}catch (ParseException e){
			e.printStackTrace();
		}catch (TokenMgrError e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}	
	}
}