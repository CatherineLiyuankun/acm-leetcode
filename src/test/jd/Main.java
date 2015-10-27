package test.jd;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = 4;
        int [] h = new int[n];

        for(int i=0;i<n;i++) {
            h[i] = sc.nextInt();
        }
        System.out.println(s(h));
    }

    public static int s(int [] h) {
        int sum = 0;
        for (int i = 0; i < h.length/2; i++) {
            sum += getH(h[i]);
        }
        return sum;
    }

    public static int getH(int h) {
        int sum = h;
        int flag = h/2;
        for (int i = 0; i < h; i++) {
            sum += 2*flag;
            flag = flag/2;
        }
        return sum;
    }
}
