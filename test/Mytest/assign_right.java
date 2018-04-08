class Test {
	public static void main(String [] args) {
		A a;
		B b;
		int i;
		int[] c;
		a = new A();
		b = new B();
		i = a.foo(1);
		c = new int[10];
		i = c.length;
		a = b;
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
}