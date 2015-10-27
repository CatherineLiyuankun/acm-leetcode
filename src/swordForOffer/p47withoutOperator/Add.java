package swordForOffer.p47withoutOperator;

/**
 * Created by muzilan on 15/10/24.
 * Sword Offer 47
 */
public class Add {
    public static void main(String args[]) {

        int a = 5;
        int b = 7;
        System.out.println(addWithoutOperator(a, b));
        swapWithoutOperator1(a, b);
        swapWithoutOperator2(a, b);
        System.out.println(getMaxNumWithoutOperator(a,b));
    }

    /**
     * 不用操作符加减乘除，求两个数的和
     */
    public static int addWithoutOperator(int num1, int num2) {
        int sum, carry;
        do {
            sum = num1 ^ num2;
            carry = (num1 & num2) << 1;
            num1 = sum;
            num2 = carry;
        } while (num2 != 0);

        return num1;
    }

    /**
     * 不用新的变量，交换两个数的值
     */
    public static void swapWithoutOperator1(int num1, int num2) {
        num1 = num1 + num2;
        num2 = num1 - num2;
        num1 = num1 - num2;
        System.out.println(num1 + " " + num2);
    }
    public static void swapWithoutOperator2(int num1, int num2) {
        num1 = num1 ^ num2;
        num2 = num1 ^ num2;
        num1 = num1 ^ num2;
        System.out.println(num1 + " " + num2);
    }
    /**
     * 不用判断语句，<,> 求两个数的最大值
     */
    public static int getMaxNumWithoutOperator(int num1, int num2) {
        return (num1 + num2 + Math.abs(num1 - num2))/2;
    }
}
