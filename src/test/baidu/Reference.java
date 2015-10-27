package test.baidu;

import java.util.*;
/**
 * Created by muzilan on 15/9/22.
 */
public class Reference {

        public static void main(String[] args) {
            String string = "123";
            System.out.println(string);
            chage(string);
            System.out.println(string);
        }

        public static void chage(String str) {
            str = "abc";
        }
}
