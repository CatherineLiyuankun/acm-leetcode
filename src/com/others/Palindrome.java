package com.others;

import java.util.Scanner;

/**
 * Created by muzilan on 15/10/21.
 */
public class Palindrome {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        System.out.println(isPalindromeByCharAtSingle(s));
    }
    /**
     * 通过字符串中的对称位置字符串是否相同来判断是否为回文,这里用了一个变量i来对应字符串对称位置的index
     * @param s
     * @return
     */
    public static boolean isPalindromeByCharAtSingle(String s) {
        //通过对称下标的关系使用一个变量即可判断所有对称位置字符是否相同
        for(int i=0;i<s.length()/2;i++){
            //只有当前一半字符串和后一半字符串对应位置相同，那么才是回文，只有有一个对称位置的字符不同就不是回文
            if(s.charAt(i)!=s.charAt(s.length()-i-1)){
                return false;
            }
        }
        return true;
    }
}
