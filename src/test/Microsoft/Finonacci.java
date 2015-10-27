package test.Microsoft;

import java.util.Scanner;

/**
 * Created by muzilan on 15/9/29.
 * 题目3 : Fibonacci
 时间限制:10000ms
 单点时限:1000ms
 内存限制:256MB
 描述
 Given a sequence {an}, how many non-empty sub-sequence of it is a prefix of fibonacci sequence.

 A sub-sequence is a sequence that can be derived from another sequence by deleting some elements without changing the order of the remaining elements.

 The fibonacci sequence is defined as below:

 F1 = 1, F2 = 1

 Fn = Fn-1 + Fn-2, n>=3

 输入
 One line with an integer n.

 Second line with n integers, indicating the sequence {an}.

 For 30% of the data, n<=10.

 For 60% of the data, n<=1000.

 For 100% of the data, n<=1000000, 0<=ai<=100000.

 输出
 One line with an integer, indicating the answer modulo 1,000,000,007.

 样例提示
 The 7 sub-sequences are:

 {a2}

 {a3}

 {a2, a3}

 {a2, a3, a4}

 {a2, a3, a5}

 {a2, a3, a4, a6}

 {a2, a3, a5, a6}

 样例输入
 6
 2 1 1 2 2 3
 样例输出
 7
 */



public class Finonacci {
//    因为Fibonacci数列本身是指数增长的, 32位的有符号整数所能表示的位置只有前46个:
//
    public static final int Fibonacci[] =
            {
                    0,1,1,2,3,5,8,13,21,34,55,89,144,233,377,610,987,1597,
                    2584,4181,6765,10946,17711,28657,46368,75025,121393,196418,
                    317811,514229,832040,1346269,2178309,3524578,5702887,9227465,
                    14930352,24157817,39088169,63245986,102334155,165580141,267914296,
                    433494437,701408733,1134903170,1836311903,-1323752223
            };

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        int[] index = new int[n];
        for(int i=0;i<n;i++) {
            a[i] = in.nextInt();
        }
        fibonacciIndex(a, index);
        System.out.println(getSub(index));
//        getSub(index);

    }

    /* 求第i个位置Fibonacci数列的值
    Fibonacci(0)=1
    * */
    public static long fibonacci(int n) {
        long f1 = 1, f2 = 1;
        long m = 0;
        if(n <= 2) {
            return 1;
        } else {
            for(int i = 3; i <= n; i++) {
                m = f1 + f2;
                f2 = f1;
                f1 = m;
            }
            return m;
        }
    }

    /**
     * * 求每个数在Fibonacci数列中的位置 * *
     *
     * @param a 原始数组 *
     * @param index   每个数在Fibonacci数列中的位置的数组 *
     */
    public static void fibonacciIndex(int [] a, int [] index) {
        for(int i=0;i<a.length;i++) {
            if (a[i] == 0) {
                index[i] = 0;
            } else if (a[i] == 1) {
                index[i] = 1;
            } else  {
                index[i] = binarySearch(a[i]);
            }
        }
    }


    /**
     * * 二分查找算法 * *
     *
     * @param des      查找元素 *
     * @return des的数组下标，没找到返回-1
     */
    public static int binarySearch(int des) {

        int low = 3;
        int high = 30;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (des == fibonacci(middle)) {
                return middle;
            } else if (des < fibonacci(middle)) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return -1;
    }



    /**
     * * 得到子数列的个数 * *
     *
     * @param index   每个数在Fibonacci数列中的位置的数组 *
     * @return 子序列的个数
     */
    public static int getSub(int [] index) {
        int result = 0;
        int t = -1;
        for(int i=1; i<index.length; ++i) {
            if (index[i] == -1) {
                continue;
            } else {
                int flag = (index[i] > index[i - 1]) ? 1 : 0;
                if (t == flag) {
//                    System.out.print(index[i] + " ");
                }
                else    //新序列
                {
                    t = (index[i] > index[i - 1]) ? 1 : 0;
                    result++;
//                    System.out.println();
//                    System.out.print(index[i - 1] + " " + index[i]);
                }
            }
        }
        return result;
    }


}
