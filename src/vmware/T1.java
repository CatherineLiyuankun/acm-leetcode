package vmware;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


    /**
 * Created by muzilan on 15/9/17.
 */
public class T1 {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int _foo_size = 0;
        _foo_size = Integer.parseInt(in.nextLine());
        int[] _foo = new int[_foo_size];
        int _foo_item;
        for(int _foo_i = 0; _foo_i < _foo_size; _foo_i++) {
            _foo_item = Integer.parseInt(in.nextLine());
            _foo[_foo_i] = _foo_item;
        }

        findingDigits(_foo);

    }
        /*
 * Complete the function below.
 */

        static void findingDigits(int[] foo) {
            int length = foo.length;
            int [] count = new int[length];
            for (int i = 0; i < length; i++) {
                int num = foo[i];
                if((num % 10) == 0)
                    count[i]++;
                num /= 10;
            }
            for (int i = 0; i < length; i++) {
                System.out.println(count[i]);
            }
        }
}

