package test.Microsoft;

/**
 * Created by muzilan on 15/9/29.
 */
/*
@author: jarg
@TODO: 动态规划求Fibonacci数列
*/

import java.io.InputStreamReader;
import java.util.Scanner;

public class F
{
    public static void main(String[] args)
    {
//        InputStreamReader ir = new InputStreamReader(System.in);
//        try
//        {
//            System.out.print("输入参数n: ");
//            int n = Integer.parseInt("" + (char)ir.read());
//            System.out.println("Fibonacci(" + n +")=" + fibonacci(n));
//        }
//        catch(Exception e)
//        {
//            System.out.println("invalid input.");
//        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        System.out.println("Fibonacci(" + n +")=" + fibonacci(n));

    }

    /* 求Fibonacci数列 */
    public static long fibonacci(int n) {
        long f1 = 1, f2 = 1;
        long m = 0;
        if(n <= 2) {
            return 1;
        } else {
            for(int i = 3; i <= n; i++) {
                m = f1 + f2;
                f2 = f1;
                f1 = m;
            }
            return m;
        }
    }
}
