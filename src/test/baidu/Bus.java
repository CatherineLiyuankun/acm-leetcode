package test.baidu;

import java.io.BufferedInputStream;
import java.util.Scanner;



public class Bus {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int pre = 0;
        int [] a = new int[n];
        int [] b = new int[n];
        int [] p = new int[n];

        a[0] = sc.nextInt();
        b[0] = sc.nextInt();
        p[0] = b[0] - a[0];

        for(int i=1;i<n;i++) {
            a[i] = sc.nextInt();
            b[i] = sc.nextInt();
            pre = p[i-1] + b[i] - a[i];
            if(pre < 0) {
                pre = 0;
            }
            p[i] = pre;
        }
        System.out.println(max(p, n));
    }

    public static int max(int [] p, int n) {
        int result = 0;

        for(int i=0;i<n;i++) {
            if (p[i] > result) {
                result = p[i];
            }
        }
        return result;
    }
}



