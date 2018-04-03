class Test {
	public static void main(String [] args) {
		A a;
		int i;
		a = new A();
		i = a.foo();
	}
}

class A extends B {
	int A;
	int foo;
	public int foo() {
		int A;
		int foo;
		return 0;
	}
}

class B extends A {
	boolean A;
	boolean foo;
}



//class A {
//	void foo(int n) {
//		int i = 0;
//		while (i < 10) {
//			int n = 10;
//			i++;
//		}
//	}
//}

//public class Test {
//	public static void main(String [] args) {
//		A a = new A();
//		a.foo(2018);
//	}
//}
