package com.others;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by muzilan on 15/10/27.
 */
public class MatchParenthesis {
    public static void main(String args[]) {
//        Scanner sc = new Scanner(System.in);
//        String s = sc.next();
        String s = ")((a(b)()(l)s))";
        System.out.println(matchParenthesis(s));
    }

    public static boolean matchParenthesis(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (!stack.empty()) {
                    if (stack.peek() == '(') {
                        stack.pop();
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            }
        }
        if (stack.empty()) {
            return true;
        } else {
            return false;
        }
    }
}
