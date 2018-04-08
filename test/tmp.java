class Test {
	public static void main(String [] args) {
		A a;
		int i;
		int[] c;
		a = new A();
		i = a.foo(1);
		c = new int[10];
		i = c.length;
	}
}

class A {
	int A;
	int foo;
	public int foo(int b) {
		int A;
		int foo;
		return 0;
	}
}

class B extends A {
	boolean A;
	boolean foo;
	public int foo(int a) {
		return 1;
	}
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
