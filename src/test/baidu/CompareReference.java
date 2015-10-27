package test.baidu;

/**
 * Created by muzilan on 15/9/13.
 */
public class CompareReference {
    public static void main(String [] args) {
        float f = 42.0f;
        float[] f1 = new float[2];
        float[] f2 = new float[2];
        float[] f3 = f1;
        long x = 42;
        f1[0] = 42.0f;

        // == 基本数据类型 比较值， 复合数据类型 比较地址
        System.out.println("f1==f2:  " + (f1==f2)); //比较地址 new 出来的不同对象， false
        System.out.println("x==f1[0]:  " + (x==f1[0])); //比较值   true ？ 自动类型转化？
        System.out.println("f1==f3:  " + (f1==f3)); //比较地址 true
//        System.out.println("f2==f1[1]:  " + (f2==f1[1]));

    }
}
