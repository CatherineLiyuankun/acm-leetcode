package test.mogujie;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by muzilan on 15/9/19.
 */
public class Basketball {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int p = sc.nextInt();
        int n = sc.nextInt();
        int [] my = new int[n];
        int index = 0;

        while (index < n) {
            my[index++] = sc.nextInt();
        }

        int flag = 0;
        for (int i = 0; i <n; i++) {
            my[i] = my[i] %p;
            for (int j =  0; j<i; j++) {
                if (my[i] == my[j]) {
                    flag =i;
                }
            }
        }
        System.out.println(flag);
    }

}
