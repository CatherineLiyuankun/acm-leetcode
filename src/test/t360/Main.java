package test.t360;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by muzilan on 15/9/15.
 */
public class Main {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            primeFactor(n);
        }
    }

    public static void primeFactor(int num) {

        if (num < 2) {// 若输入的数小于2,输出提示信息

            System.out.println(1);
        } else {

            int primeNumber = 2;// 定义最小的质数

            while (primeNumber <= num) {// 在质数小于输入的数时，进行循环

                if (primeNumber == num) {// 当质数等于输入的数时,直接输出

                    System.out.print(num);
                    break;// 跳出循环

                } else if (num % primeNumber == 0) {// 当输入的数与质数的余数为0时,输出这个质数

                    System.out.print(primeNumber + " * ");
                    num = num / primeNumber;// 把剩下的结果赋给num

                } else {// 在余数不为0时,质数递增
                    primeNumber++;
                }
            }
        }
    }

}
