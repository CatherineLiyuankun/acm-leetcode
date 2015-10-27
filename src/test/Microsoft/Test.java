package test.Microsoft;

import java.util.Scanner;

/**
 * Created by muzilan on 15/9/29.
 */
public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 4;
        int[] a = new int[n];
        int[] index = new int[n];
        for(int i=0;i<n;i++) {
            a[i] = i;
            index[i] = i*i;
        }
        fibonacciIndex(a, index);
        for(int i=0;i<n;i++) {
            System.out.println(a[i]);
        }
        for(int i=0;i<n;i++) {
            System.out.println(index[i]);
        }

    }
    public static void fibonacciIndex(int [] a, int [] b) {
        for(int i=0;i<a.length;i++) {
            b[i] = i;
            a[i] = i*i;
        }
    }
}
