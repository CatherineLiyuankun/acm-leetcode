package swordForOffer.p41FindNumbersWithSum;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by muzilan on 15/10/20.
 */
public class FindTwoNumbersWithSum {
    public static void main(String args[]){
        int[] array = {1, 2, 4, 7, 11, 15};
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int sum = sc.nextInt();
        if (!findTwoNumbersWithSum(array, sum)) {
            System.out.println("can't find");
        }
        findContinuousSequence(sum);
    }

    /*
     * 问题1：输入一个递增排序的数组和一个数字s.在数组中查找两个数使他们的和为s，如果有多对数字的和等于s，输出一对即可
     */
    public static boolean findTwoNumbersWithSum(int[] sortedArray, int sum) {
        boolean found = false;
        if (sortedArray == null) {
            return found;
        }
        int small = 0;
        int big = sortedArray.length - 1;
        while (small < big) {
            int curSum = sortedArray[small] + sortedArray[big];
            if (curSum == sum) {
                System.out.println(sortedArray[small] + " " + sortedArray[big]);
                found = true;
                return found;
            } else if (curSum > sum) {
                big--;
            } else {
                small++;
            }
        }
        return found;
    }

    /*
	 * 问题2:输入一个正整数s，打印出所有和为s的连续正数序列（至少含有两个数）
	 * 例如输入15，由于1+2+3+4+5=4+5+6=7+8=15，所有打印结果为3个连续序列 1~5 、4~6、7~8
	 */
    public static void findContinuousSequence(int sum) {
        int small = 1;
        int big = 2;
        int curSum = small + big;
        int middle = (sum + 1) / 2;

        while (small < middle) {
             if (curSum == sum) {
                 printContinuousSequence(small, big);
             }
             while (curSum > sum && small < middle) {
                 curSum -= small;
                 small++;
                 if (curSum == sum) {
                     printContinuousSequence(small, big);
                 }
             }
             big ++;
             curSum += big;
        }

    }

    public static void printContinuousSequence(int small, int big) {
        for (int i = small; i < big; i++) {
            System.out.print(i + " ");
        }
        System.out.println(big);
    }


}

