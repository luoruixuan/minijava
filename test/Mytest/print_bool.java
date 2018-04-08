class Test {
	public static void main(String [] args) {
		A a;
		int i;
		int[] c;
		a = new A();
		i = a.foo(true);
		c = new int[10];
		i = c.length;
	}
}

class A {
	int A;
	int foo;
	public int foo(boolean b) {
		int A;
		int foo;
		System.out.println(b);
		return 0;
	}
}

