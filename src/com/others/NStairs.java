package com.others;

import java.util.Scanner;

/**
 * 题目：一个台阶总共有n 级，如果一次可以跳1 级，也可以跳2 级。
 *求总共有多少总跳法，并分析算法的时间复杂度。http://zhedahht.blog.163.com/blog/static/25411174200731844235261/
 * http://blog.csdn.net/v_july_v/article/details/6879101/
 */
public class NStairs {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        System.out.println(fibonacciSolution1(n));
        System.out.println(fibonacciSolution2(n));
        System.out.println(fibonacciSolution3(n));
        System.out.println(fibonacciSolution4(n));
    }

    //递归方法计算的时间复杂度是以n的指数的方式递增的
    public static long fibonacciSolution1(long n){
        if(n<=0) return 0;
        if(n==1) return 1;
        if(n==2) return 2;
        return fibonacciSolution1(n - 1)+ fibonacciSolution1(n - 2);
    }

    //时间复杂度是O(n)   http://zhedahht.blog.163.com/blog/static/25411174200722991933440/
    public static long fibonacciSolution2(long n){
        if(n<=0) return 0;
        if(n==1) return 1;
        if(n==2) return 2;
        long fibMinus2 = 1;
        long fibMinus1 = 2;
        long fibN = 0;
        for (long i = 2; i < n; i++) {
            fibN = fibMinus1 + fibMinus2;

            fibMinus2 = fibMinus1;
            fibMinus1 = fibN;
        }

        return fibN;
    }

    //可以跳上1级台阶，也可以跳上2 级……它也可以跳上n 级，此时跳上一个n级的台阶总共有多少种跳法？http://blog.csdn.net/silenough/article/details/7184784
    public static long fibonacciSolution3(long n){
        if(n<=0) return 0;
        if(n==1) return 1;
        if(n==2) return 2;
        return 2 * fibonacciSolution3(n - 1);
    }

    public static long fibonacciSolution4(long n){
        if(n<=0) return 0;
        if(n==1) return 1;
        if(n==2) return 2;
        long fibMinus1 = 2;
        long fibN = 0;
        for (long i = 2; i < n; i++) {
            fibN = 2 * fibMinus1;

            fibMinus1 = fibN;
        }
        return fibN;
    }
}
