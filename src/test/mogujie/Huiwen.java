package test.mogujie;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by muzilan on 15/9/19.
 * 蘑菇街
 * <p/>
 * 添加一个字符使其可变为回文字符
 */
public class Huiwen {
    public static void main(String args[]) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        String my = sc.next();
        int length = my.length();

        char[] string = my.toCharArray();
        int flag = 1;

        for (int i = 0; i <= length / 2; i++) {

            if (string[i] == string[length - 1 - i]) {
                if (i == length / 2) {
                    System.out.println("YES");
                    flag = 0;
                }
                continue;
            } else {
                if (length - 3 - i >= 0 && string[i + 1] == string[length - 3 - i]) {
                    if (i == length / 2 - 1) {
                        System.out.println("YES");
                        flag = 0;
                    }

                }
            }
            if (flag == 1) {
                System.out.println("NO");
            }
        }
    }
}
