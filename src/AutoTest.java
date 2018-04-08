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
	    int tot = 0;
	    for (int i = 0; i < fa.length; i++) {
		      File fs = fa[i];
		      if (!fs.isDirectory()) {
		    	  tot++;
		      }
	    }

	    namelist = new String[tot];
	    int now = 0;
		for (int i = 0; i < fa.length; i++) {
			  File fs = fa[i];
			  if (!fs.isDirectory()) {
		        namelist[now] = path+fs.getName();
		        now = now + 1;
		      }
	    }
	}
	public void run(int id) {
		solve(namelist[id]);
		//for (int i=0; i<namelist.length; i++) {
		//	solve(namelist[i]);
		//}
	}
	public static void solve(String name) {
		System.out.println(name);
		SymbolTable ST = new SymbolTable();
		BuildSymbolTableVisitor V = new BuildSymbolTableVisitor();
		try {
			FileInputStream in = new FileInputStream(name);
			//String input_file = s.next();
			//FileInputStream in = new FileInputStream(input_file);
			Node root = new MiniJavaParser(in).Goal();
			root.accept(V, ST);
			ST.createSymbolTree();
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