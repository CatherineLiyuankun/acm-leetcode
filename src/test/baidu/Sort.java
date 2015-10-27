package test.baidu;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by muzilan on 15/9/14.
 */
public class Sort {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int [] h = new int[n];

        for(int i=0;i<n;i++) {
            h[i] = sc.nextInt();
        }
        System.out.println(sort(h));
    }

    public static int sort(int [] h) {
        for (int i = 0; i < h.length; i++) {
            for (int j = h.length - 1; j > i; j--) {
                if (h[j-1] > h[j]) {
                    int temp = h[j-1];
                    h[j-1] = h[j];
                    h[j] = temp;
                }
            }
        }
        for(int i=0;i<h.length;i++) {
            System.out.println(h[i]);
        }
        return 0;
    }

}
