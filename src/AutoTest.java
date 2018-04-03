import syntaxtree.*;
import symboltable.*;
import java.io.FileInputStream;
import typecheck.TypeCheckVisitor;
import java.io.File;

public class AutoTest{
	String[] namelist;
	public AutoTest() {
		String path = "E:\\作业\\大三下\\编译实习\\minijava\\test\\";
		File f = new File(path);
	    File fa[] = f.listFiles();
		namelist = new String[fa.length];
	    for (int i = 0; i < fa.length; i++) {
		      File fs = fa[i];
		      if (!fs.isDirectory()) {
		        namelist[i] = path+fs.getName();
		      }
	    }
	}
	public void run() {
		for (int i=0; i<namelist.length; i++) {
			solve(namelist[i]);
		}
	}
	public static void solve(String name) {
		if (name.endsWith("error.java")) return;
		System.out.println(name);
		SymbolTable ST = new SymbolTable();
		BuildSymbolTableVisitor V = new BuildSymbolTableVisitor();
		try {
			FileInputStream in = new FileInputStream(name);
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