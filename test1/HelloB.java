package test1;

/**
 * Created by Catherine on 15/8/5.
 */
public class HelloB extends HelloA
{
	public HelloB()
	{
		System.out.println("B constructor");
	}
	{
		System.out.println("I’m B class");
	}
	static
	{
		System.out.println("static B");
	}
	public static void main(String[] args)
	{
		new HelloB();
	}
}
class HelloA
{
	public HelloA()
	{
		System.out.println("A constructor");
	}
	{
		System.out.println("I’m A class");
	}
	static
	{
		System.out.println("static A");
	}
}
