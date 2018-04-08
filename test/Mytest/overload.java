class Test {
	public static void main(String [] args) {
		A a;
		int i;
		int[] c;
		a = new A();
		i = a.foo(1, c);
		c = new int[10];
		i = c.length;
	}
}
class A{
	int A;
	int foo;
	public int foo(int b) {
		int A;
		int foo;
		return 0;
	}
}

class B extends A {
	public int foo(boolean b) {
		return 0;
	}
}