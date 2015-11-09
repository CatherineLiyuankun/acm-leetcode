package com.others;

/**
 * Created by muzilan on 15/10/27.
 * http://blog.csdn.net/sjf0115/article/details/8607378
 */
public class GCD {
    public static void main(String args[]) {
        int a = 33;
        int b = 72;
        System.out.println(gcd1(a, b));
        System.out.println(gcd2(a, b));
        System.out.println(gcd3(a, b));
        System.out.println(gcd4(a, b));
    }
/**********************************************************************************************/
    /**
     * 欧几里得辗转相除法：取模 gcd(a,b) = gcd(b, a%b)
     */
    public static int gcd1(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd1(b, a % b);
        }
    }

    /**
     * 欧几里得辗转相除法：非递归取模
     */
    public static int gcd2(int a, int b) {
        while (b != 0) {
            int temp = a;
            a = b;
            b = temp % b;
        }
        return a;
    }

    /**********************************************************************************************/

    /**
     * 欧几里得辗转相除法：gcd(a,b) = gcd(a-b, b)
     * 此解法用减法而不是除法，这样迭代的次数比除法要多，当遇到f(10000000,1)的情况时这不是一个好方法。
     */
    public static int gcd3(int a, int b) {
        if (a < b) {
            return gcd3(b, a);
        }
        if (b == 0) {
            return a;
        } else {
            return gcd3(a - b, b);
        }
    }
    /**********************************************************************************************/

    /**
     * 对于x,y,如果y = k * y1,x = k * x1,则f(y,x) = K*f(x1,y1)；
     如果x = p * x1, 假设p是素数，且 y % p != 0 ,即y不能被p整除，则f(x,y) = f(x1,y).
     可以利用上面两点进行改进。因为2是素数，同时对于二进制表示的大整数而言可以很容易的将除以2和乘以2的算法转换为移位运算，从而避免大整数除法。
     可以充分利用2进行分析：
     若x,y都为偶数（2肯定是公约数），则f(x,y) = 2*f(x / 2,y / 2) = 2*f(x>>1,y>>1);
     若x为偶数，y为奇数(2肯定不是公约数),则f(x,y) = f(x / 2, y / 2) = f(x>>1, y)
     若x为奇数，y为偶数2肯定不是公约数)，则f(x,y)= f(x, y / 2) = f(x, y>>1)
     若x,y都为奇数（2肯定不是公约数），则f(x,y) = f(y, x-y)    (x-y肯定为偶数) = f（y, (x-y)/2）

     这个算法的好处就是用移位操作来代替除法操作，大大节约时间。
     */
    public static int isEvenOdd(int n){
        if(n % 2 == 0){
            return 1;
        }
        else{
            return 0;
        }
    }

    public static int gcd4(int a, int b) {
        //如果a < b
        if(a < b){
            return gcd4(b,a);
        }
        if(b == 0){
            return a;
        }
        //若x,y都为偶数
        if(isEvenOdd(a) == 1 && isEvenOdd(b) == 1){
            return 2 * gcd4(a>>1,b>>1);
        }
        //若x,y都为奇数
        else if(isEvenOdd(a) == 0 && isEvenOdd(b) == 0){
            return gcd4(b,a-b);
        }
        //若x是偶数y是奇数
        else if(isEvenOdd(a) == 1 && isEvenOdd(b) == 0){
            return gcd4(a>>1,b);
        }
        //若x是奇数y是偶数
        else{
            return gcd4(a,b>>1);
        }

    }

}