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
	public int foo(int c) {
		return 1;
	}
}
