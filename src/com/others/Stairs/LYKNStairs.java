package com.others.Stairs;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * 题目1：一个台阶总共有n 级，如果一次可以跳1 级，也可以跳2 级。
 * 求总共有多少总跳法，并分析算法的时间复杂度。http://zhedahht.blog.163.com/blog/static/25411174200731844235261/
 * http://blog.csdn.net/v_july_v/article/details/6879101/
 */
public class LYKNStairs {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        System.out.println(fibonacciSolution1(n));
        System.out.println(fibonacciSolution2(n));
        System.out.println(fibonacciSolution3(n));
        System.out.println(fibonacciSolution4(n));
    }

    //递归方法：计算的时间复杂度是以n的指数的方式递增的
    public static long fibonacciSolution1(long n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        return fibonacciSolution1(n - 1) + fibonacciSolution1(n - 2);
    }

    //非递归：时间复杂度是O(n)   http://zhedahht.blog.163.com/blog/static/25411174200722991933440/
    public static long fibonacciSolution2(long n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
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

    /**
     * 题目2：一个台阶总共有n 级，可以跳上1级台阶，也可以跳上2 级，第5级，第8级台阶不能走
     */
    public static long fibonacciSolution2Limit(long n) {
        if (n <= 0 || n == 5 || n == 8) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
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

    /**
     * 题目3：一个台阶总共有n 级，可以跳上1级台阶，也可以跳上2 级……它也可以跳上n 级，
     * 此时跳上一个n级的台阶总共有多少种跳法？http://blog.csdn.net/silenough/article/details/7184784
     */
    public static long fibonacciSolution3(long n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        return 2 * fibonacciSolution3(n - 1);
    }

    public static long fibonacciSolution4(long n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        long fibMinus1 = 2;
        long fibN = 0;
        for (long i = 2; i < n; i++) {
            fibN = 2 * fibMinus1;

            fibMinus1 = fibN;
        }
        return fibN;
    }
}

/**
 * 题目4：一个台阶总共有n 级，如果一次可以跳1 级，也可以跳2 级。
 * 求总共有多少总跳法,记录每步的走法，输出所有可能的走法
 */

class TreeNode {
    private long value;

    private TreeNode left;
    private TreeNode right;
    private TreeNode father;

    public static ArrayList<TreeNode> leaves = new ArrayList<TreeNode>();

    public TreeNode(int value, TreeNode left, TreeNode right, TreeNode father) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.father = father;
    }

    public TreeNode(int value) {
        this(value, null, null, null);
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public TreeNode getFather() {
        return father;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public static void createStepTree(long n, TreeNode father) {
        final int step_1 = 1; //一步一级
        final int step_2 = 2; //一步二级

        if (n == 0) {
            leaves.add(father);
            return;

        } else if (n == 1) {//若台阶数为1,则只能走一步一级
            TreeNode left = new TreeNode(step_1, null, null, father);
            createStepTree(n - step_1, left);
        }
        else if (n >= 2) { //若台阶数为2,第一步可以走一级,也可以走两阶
            TreeNode left = new TreeNode(step_1, null, null, father);
            father.setLeft(left);
            createStepTree(n - step_1, left);

            TreeNode right = new TreeNode(step_2, null, null, father);
            father.setRight(right);
            createStepTree(n - step_2, right);
        }
    }

    public void display()
    {
        System.out.print(getValue()+"	");
    }

    public void display(TreeNode leaf)
    {
        leaf.display();
        if(leaf.getFather()!=null && leaf.getFather().getValue() != 0)
        {
            display(leaf.getFather());
        }
    }

    public static void main(String[] args)
    {
        TreeNode root = new TreeNode(0);
        createStepTree(10, root);

        int count = 0;
        for(TreeNode temp:TreeNode.leaves)
        {
            temp.display(temp);
            System.out.println();
            count++;
        }
        System.out.println("*****************" + count);

    }
}

