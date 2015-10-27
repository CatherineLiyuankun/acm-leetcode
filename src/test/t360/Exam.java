package test.t360;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by muzilan on 15/9/15.
 */
public class Exam {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int [] policy = new int[n];
        int [] english = new int[n];
        int [] math = new int[n];
        int [] major = new int[n];
        int [] flag = new int[n];

        for(int i=0;i<n;i++) {
            policy[i] = sc.nextInt();
            english[i] = sc.nextInt();
            math[i] = sc.nextInt();
            major[i] = sc.nextInt();
            flag[i] = judge(policy, english, math, major, i);

        }
        for(int i=0;i<n;i++) {
            if (flag[i] == 1) {
                System.out.println("Fail");

            }else if (flag[i] == 2) {
                System.out.println("Zifei");

            }else if (flag[i] == 3) {
                System.out.println("Gongfei");
            }
        }
    }

    public static int judge(int [] policy, int [] english, int [] math, int [] major, int i) {
        int sum  = policy[i] +  english[i] + math[i] + major[i];
        if (policy[i] < 60 || english[i] < 60 || math[i] < 90 || major[i] < 90 || sum < 310) {
            return 1;
        } else {
            if (sum >= 350) {
                return 3;
            } else {
                return 2;
            }
        }
    }
}
