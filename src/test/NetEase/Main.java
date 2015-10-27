package test.NetEase;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by muzilan on 15/10/9.
 */
public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        ArrayList arrayList = new ArrayList();
//        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int a = sc.nextInt();
            int [] b = new int[n];
            for(int i=0;i<n; i++) {
                b[i] = sc.nextInt();
            }
            arrayList.add(sum(a,b));
//        }

        for(int i=0;i<arrayList.size();i++)
        {
            int result = ((Integer)arrayList.get(i)).intValue();
            System.out.println(result);
        }


    }

    public static int sum(int a, int [] b) {
        for(int i=0;i<b.length; i++) {
            if (a >= b[i]) {
                a += b[i];
            } else {
                a += gcd(a,b[i]);
            }
        }
        return a;
    }

    public static int gcd(int a, int b) {
        while(b != 0) {
            int temp = a%b;
            a = b;
            b = temp;
        }
        return a;
    }


}
