package com.lyk.acm;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

/**
 * Created by Catherine on 15/8/11.
 */
public class AcmIO {
	public static void main(String [] args) throws IOException {
		//输入
		Scanner1();
//		Scanner2();
//		Read();
//		Buffer();
//		//输出
//		print();
//		format();

//		bigNumber();
//		string();
	}
	public static void print() {
//		输出一般可以直接用 System.out.print() 和 System.out.println()，前者不输出换行，而后者输出。
//		比如：
		int n = 4;
		int m = 4;
		System.out.println(n);   // n 为 int 型
//		同一行输出多个整数可以用
		System.out.println(new Integer(n).toString() + " " + new Integer(m).toString());
//		也可重新定义：
		PrintWriter cout = new PrintWriter(new BufferedOutputStream(System.out));
		cout.println(n);
	}

	public static void format() {
//		import java.text.*;
		//这里0指一位数字，#指除0以外的数字
		DecimalFormat f = new DecimalFormat("#.00#");
		DecimalFormat g = new DecimalFormat("0.000");
		double a = 123.45678, b = 0.120;
		System.out.println(f.format(a));
		System.out.println(g.format(a));
		System.out.println(f.format(b));
		System.out.println(g.format(b));
	}

	//大数字
//	BigInteger 和 BigDecimal 是在java.math包中已有的类，前者表示整数，后者表示浮点数
//	用法：
//	不能直接用符号如+、-来使用大数字，例如：
//	(import java.math.*)   // 需要引入 java.math 包
	public static void bigNumber() {
		BigInteger a = BigInteger.valueOf(100);
		BigInteger b = BigInteger.valueOf(50);
		BigInteger c = a.add(b);   // c = a + b;
//		主要有以下方法可以使用：
//		BigInteger add(BigInteger other)
//		BigInteger subtract(BigInteger other)
//		BigInteger multiply(BigInteger other)
//		BigInteger divide(BigInteger other)
//		BigInteger mod(BigInteger other)
//		int compareTo(BigInteger other)
//		static BigInteger valueOf(long x)
//		输出大数字时直接使用
		System.out.println(c);
		System.out.println(a.compareTo(b));
		System.out.println(a.subtract(b));
	}

	public static void string() {
//		String 类用来存储字符串，可以用charAt方法来取出其中某一字节，计数从0开始：
		String a = "Hello";    // a.charAt(1) = 'e'
		System.out.println(a.charAt(1));
//		用substring方法可得到子串，如上例
		System.out.println(a.substring(0, 4));     // output "Hell"
		System.out.println(a.substring(2));
		System.out.println("length:" + a.length());
		System.out.println("replace:" + a.replace('l', 'k'));
		System.out.println("a:" + a);
//		注意第2个参数位置上的字符不包括进来。这样做使得 s.substring(a, b) 总是有 b-a个字符。
//		字符串连接可以直接用 + 号，如
		String b = "world";
		System.out.println(a + ", " + b + "!");    // output "Hello, world!"
//		如想直接将字符串中的某字节改变，可以使用另外的StringBuffer类。
		StringBuffer stringBuffer = new StringBuffer("0123456");
		stringBuffer.setCharAt(0, 'q');
		System.out.println(stringBuffer);
	}



//import java.io.*;
//	import java.util.*;
// 从控制台接收一个字符串，然后将其打印出来。在这个题目中，我们需要用到BufferedReader类和InputStreamReader类
/*	读一个整数：   int n = cin.nextInt();         相当于   scanf("%d", &n);   或 cin >> n;
	读一个字符串：String s = cin.next();         相当于   scanf("%s", s);    或 cin >> s;
	读一个浮点数：double t = cin.nextDouble();   相当于   scanf("%lf", &t); 或 cin >> t;
	读一整行：     String s = cin.nextLine();     相当于   gets(s);           或 cin.getline(...);
	判断是否有下一个输入可以用 cin.hasNext() 或 cin.hasNextInt() 或 cin.hasNextDouble() 等*/
	public static void Scanner1() {
		Scanner cin = new Scanner(System.in);
		Scanner cin2 = new Scanner(new BufferedInputStream(System.in));
		int a, b;
		while(cin.hasNextInt())
		{
			a = cin.nextInt();
			b = cin.nextInt();
			System.out.println(a + b);
		}
	}

	public static void Scanner2() {
		Scanner sc = new Scanner(System.in);

		System.out.println("请输入你的姓名：");

		String name = sc.nextLine();

		System.out.println("请输入你的年龄：");

		int age = sc.nextInt();

		System.out.println("请输入你的工资：");

		float salary = sc.nextFloat();

		System.out.println("你的信息如下：");

		System.out.println("姓名："+name+"\n"+"年龄："+age+"\n"+"工资："+salary);
	}

	//从控制台接收一个字符，然后将其打印出来,
	// 但是System.out.read()只能针对一个字符的获取，
	// 同时，获取进来的变量的类型只能是char,当我们输入一个数字，
	// 希望得到的也是一个整型变量的时候，我们还得修改其中的变量类型，这样就显得比较麻烦
	public static void Read() throws IOException{
		System.out.print("Enter a Char:");

		char i = (char) System.in.read();

		System.out.println("your char is :"+i);
	}

	//从控制台接收一个字符串，然后将其打印出来。在这个题目中，我们需要用到BufferedReader类和InputStreamReader类
	public static void Buffer() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String str = null;

		System.out.println("Enter your value:");

		str = br.readLine();

		System.out.println("your value is :"+str);
	}

/*	5、其他注意的事项
			(1) Java 是面向对象的语言，思考方法需要变换一下，里面的函数统称为方法，不要搞错。
			(2) Java 里的数组有些变动，多维数组的内部其实都是指针，所以Java不支持fill多维数组。
	数组定义后必须初始化，如 int[] a = new int[100];
	(3) 布尔类型为 boolean，只有true和false二值，在 if (...) / while (...) 等语句的条件中必须为boolean类型。
	在C/C++中的 if (n % 2) ... 在Java中无法编译通过。
			(4) 下面在java.util包里Arrays类的几个方法可替代C/C++里的memset、qsort/sort 和 bsearch:
			Arrays.fill()
			Arrays.sort()
			Arrays.binarySearch()*/
}
