package test.NetEase;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by muzilan on 15/10/9.
 */
public class Test {
    public static void main(String args[]) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        ArrayList arrayList = new ArrayList();
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            arrayList.add(sum(n));
        }

        for (int i = 0; i < arrayList.size(); i++) {
            int result = ((Integer) arrayList.get(i)).intValue();
            System.out.println(result);
        }

    }

    public static int sum(int n) {
        if (n>0) {
            if (n == 1) {
                return 1;
            } else {
                return sum(n - 1);
            }
        }
        return n;
    }
}
