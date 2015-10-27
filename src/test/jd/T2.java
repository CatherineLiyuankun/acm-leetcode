package test.jd;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by muzilan on 15/9/18.
 * TODO
 * 猴子分桃
 */
public class T2 {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        System.out.println(s(n));
    }

    private static int j = 1;
    public static int s(int n) {
        int i = 1;
        if (j <= n){
            for (;f(n,i) == 0; i++) {
            }
            n = n * i + 1;
            j++;
            return s(n);
        }
        else return n;

    }

    public static int f(int n ,int i) {
        if (((n * i + 1) % (n - 1)) == 0) {
            return 1;
        } else {
            return 0;
        }

    }
}
