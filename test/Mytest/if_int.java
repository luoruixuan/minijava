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
		if (b) {
			foo = 0;
		}
		else {
			foo = 1;
		}
		return foo;
	}
}

