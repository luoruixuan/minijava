package typecheck;
import syntaxtree.*;
import java.util.*;
import symboltable.*;
import visitor.*;


public class TypeCheckVisitor extends GJDepthFirst<String, SymbolTable> {

	private String parseType(Type n) {
		   Node c = n.f0.choice;
		   String t = c.getClass().toString();
		   if (t.equals("class syntaxtree.ArrayType")) return "int*";
		   if (t.equals("class syntaxtree.Identifier")) return ((Identifier)c).f0.toString();
		   if (t.equals("class syntaxtree.IntegerType")) return ((IntegerType)c).f0.toString();
		   if (t.equals("class syntaxtree.BooleanType")) return ((BooleanType)c).f0.toString();
		   return null;
	   }
	
   //
   // GJ Auto class visitors
   //

   //public String visit(NodeList n, SymbolTable argu);
   public String visit(NodeListOptional n, SymbolTable argu) {

	   if ( n.present() ) {
	         String ret="";
	         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
	            String s = e.nextElement().accept(this,argu);
	            if (s!=null) {
	            	ret = ret + s;
	            }
	         }
	         return ret;
	      }
	      else
	         return "";
   }
   public String visit(NodeOptional n, SymbolTable argu) {
	   if ( n.present() )
	         return n.node.accept(this,argu);
	      else
	         return "";
   }
   //public String visit(NodeSequence n, SymbolTable argu);
   //public String visit(NodeToken n, SymbolTable argu);

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public String visit(Goal n, SymbolTable argu) {
	   n.f0.accept(this, argu);
	   n.f1.accept(this, argu);
	   return null;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
   public String visit(MainClass n, SymbolTable argu) {
	   argu.setStatus(n.f1.f0.toString(), "main");
	   n.f15.accept(this, argu);
	   return null;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   //public String visit(TypeDeclaration n, SymbolTable argu);

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public String visit(ClassDeclaration n, SymbolTable argu) {
	   argu.setClass(n.f1.f0.toString());
	   n.f4.accept(this, argu);
	   return null;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public String visit(ClassExtendsDeclaration n, SymbolTable argu) {
	   argu.setClass(n.f1.f0.toString());
	   n.f6.accept(this, argu);
	   return null;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   //public String visit(VarDeclaration n, SymbolTable argu);

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public String visit(MethodDeclaration n, SymbolTable argu) {
	   argu.setMethod(n.f2.f0.toString());
	   n.f8.accept(this, argu);
	   String aimType = parseType(n.f1);
	   String s = n.f10.accept(this, argu);
	   if (!argu.isAnsistor(aimType, s)) {
		   System.out.println("Type error. Return type does not match in function " + n.f2.f0.toString()+".");
		   System.exit(0);
	   }
	   return aimType;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   //public String visit(FormalParameterList n, SymbolTable argu);

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   //public String visit(FormalParameter n, SymbolTable argu);

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   //public String visit(FormalParameterRest n, SymbolTable argu);

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   //public String visit(Type n, SymbolTable argu);

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   //public String visit(ArrayType n, SymbolTable argu);

   /**
    * f0 -> "boolean"
    */
   //public String visit(BooleanType n, SymbolTable argu);

   /**
    * f0 -> "int"
    */
   //public String visit(IntegerType n, SymbolTable argu);

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public String visit(Statement n, SymbolTable argu) {
	   return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public String visit(Block n, SymbolTable argu) {
	   return n.f1.accept(this, argu);
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   
   public String visit(AssignmentStatement n, SymbolTable argu) {
	   String var = n.f0.f0.toString();
	   String leftType = argu.getType(var);
	   String rightType = n.f2.accept(this, argu);
	   if (!argu.isAnsistor(leftType, rightType)) {
		   System.out.println("Type error in assignment.");
		   System.exit(0);
	   }
	   argu.varInitialize(var);
	   return null;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public String visit(ArrayAssignmentStatement n, SymbolTable argu) {
	   String var = n.f0.f0.toString();
	   String idType = argu.getType(var);
	   String expType = n.f2.accept(this, argu);
	   String rightType = n.f5.accept(this, argu);
	   if (!expType.equals("int")) {
		   System.out.println("Type error. Array index must be integer.");
		   System.exit(0);
	   }
	   if (!rightType.equals("int")) {
		   System.out.println("Type error in assignment. ");
		   System.exit(0);
	   }
	   if (!idType.equals("int*")) {
		   System.out.println("Type error. " + var + " is not an array.");
		   System.exit(0);
	   }
	   argu.varInitialize(var);
	   return null;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public String visit(IfStatement n, SymbolTable argu) {
	   String expType = n.f2.accept(this, argu);
	   n.f4.accept(this, argu);
	   n.f6.accept(this, argu);
	   if (!expType.equals("boolean")) {
		   System.out.println("Type error. Expression in if statement is not a boolean.");
		   System.exit(0);
	   }
	   return null;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public String visit(WhileStatement n, SymbolTable argu) {
	   String expType = n.f2.accept(this, argu);
	   n.f4.accept(this, argu);
	   if (!expType.equals("boolean")) {
		   System.out.println("Type error. Expression in while statement is not a boolean.");
		   System.exit(0);
	   }
	   return null;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public String visit(PrintStatement n, SymbolTable argu) {
	   String expType = n.f2.accept(this, argu);
	   if (!expType.equals("int")) {
		   System.out.println("Type error. You must print an integer.");
		   System.exit(0);
	   }
	   return null;
   }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public String visit(Expression n, SymbolTable argu) {
	   return n.f0.accept(this, argu);
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public String visit(AndExpression n, SymbolTable argu) {
	   String type0 = n.f0.accept(this, argu);
	   String type2 = n.f2.accept(this, argu);
	   if (!type0.equals("boolean")||!type2.equals("boolean")) {
		   System.out.println("Type error. && operator can only be applied on booleans.");
		   System.exit(0);
	   }
	   return "boolean";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public String visit(CompareExpression n, SymbolTable argu) {
	   String type0 = n.f0.accept(this, argu);
	   String type2 = n.f2.accept(this, argu);
	   if (!type0.equals("int")||!type2.equals("int")) {
		   System.out.println("Type error. < operator can only be applied on integers.");
		   System.exit(0);
	   }
	   return "boolean";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public String visit(PlusExpression n, SymbolTable argu) {
	   String type0 = n.f0.accept(this, argu);
	   String type2 = n.f2.accept(this, argu);
	   if (!type0.equals("int")||!type2.equals("int")) {
		   System.out.println("Type error. + operator can only be applied on integers.");
		   System.exit(0);
	   }
	   return "int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public String visit(MinusExpression n, SymbolTable argu) {
	   String type0 = n.f0.accept(this, argu);
	   String type2 = n.f2.accept(this, argu);
	   if (!type0.equals("int")||!type2.equals("int")) {
		   System.out.println("Type error. - operator can only be applied on integers.");
		   System.exit(0);
	   }
	   return "int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public String visit(TimesExpression n, SymbolTable argu) {
	   String type0 = n.f0.accept(this, argu);
	   String type2 = n.f2.accept(this, argu);
	   if (!type0.equals("int")||!type2.equals("int")) {
		   System.out.println("Type error. * operator can only be applied on integers.");
		   System.exit(0);
	   }
	   return "int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public String visit(ArrayLookup n, SymbolTable argu) {
	   String type0 = n.f0.accept(this, argu);
	   String type2 = n.f2.accept(this, argu);
	   if (!type0.equals("int*")) {
		   System.out.println("Type error. Type " + type0 + " is not an array.");
		   System.exit(0);
	   }
	   if (!type2.equals("int")) {
		   System.out.println("Type error. Array index must be integer.");
		   System.exit(0);
	   }
	   return "int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public String visit(ArrayLength n, SymbolTable argu) {
	   String type0 = n.f0.accept(this, argu);
	   if (!type0.equals("int*")) {
		   System.out.println("Type error. Type " + type0 + " is not an array.");
		   System.exit(0);
	   }
	   return "int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public String visit(MessageSend n, SymbolTable argu) {
	   String explst = n.f4.accept(this, argu);
	   String type0 = n.f0.accept(this, argu);
	   String M = n.f2.f0.toString();
	   //System.out.println(type0);
	   //System.out.println(M);
	   //System.out.println(explst);
	   if (!argu.checkMethodArgsType(type0, M, explst)) {
		   System.out.println("Argument not match.");
		   System.exit(0);
	   }
	   return argu.getMethodType(type0, M);
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public String visit(ExpressionList n, SymbolTable argu) {
	   return n.f0.accept(this, argu) + n.f1.accept(this, argu);
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public String visit(ExpressionRest n, SymbolTable argu) {
	   return " " + n.f1.accept(this, argu);
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public String visit(PrimaryExpression n, SymbolTable argu) {
	   return n.f0.accept(this, argu);
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n, SymbolTable argu) {
	   return "int";
   }

   /**
    * f0 -> "true"
    */
   public String visit(TrueLiteral n, SymbolTable argu) {
	   return "boolean";
   }

   /**
    * f0 -> "false"
    */
   public String visit(FalseLiteral n, SymbolTable argu) {
	   return "boolean";
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Identifier n, SymbolTable argu) {
	   String name = n.f0.toString();
		if (!argu.varIsInitialized(name)) {
			System.out.println("Variable " + name + " not initialized.");
			System.exit(0);
		}
	   return argu.getType(n.f0.toString());
   }

   /**
    * f0 -> "this"
    */
   public String visit(ThisExpression n, SymbolTable argu) {
	   return argu.getType("this");
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public String visit(ArrayAllocationExpression n, SymbolTable argu) {
	   String type3 = n.f3.accept(this, argu);
	   if (!type3.equals("int")) {
		   System.out.println("Type error. Array must be allocated with an integer length.");
		   System.exit(0);
	   }
	   return "int*";
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public String visit(AllocationExpression n, SymbolTable argu) {
	   String cls = n.f1.f0.toString();
	   if (!argu.hasClasses(cls)) {
		   System.out.println("No type " + cls + ".");
		   System.exit(0);
	   }
	   return cls;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public String visit(NotExpression n, SymbolTable argu) {
	   String type1 = n.f1.accept(this, argu);
	   if (!type1.equals("boolean")) {
		   System.out.println("Type error. Expression after ! is not boolean type.");
		   System.exit(0);
	   }
	   return "boolean";
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public String visit(BracketExpression n, SymbolTable argu) {
	   return n.f1.accept(this, argu);
   }

}
