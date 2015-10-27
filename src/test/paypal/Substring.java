package test.paypal;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by muzilan on 15/10/24.
 * substrings
 * <p/>
 * Time Limit: 4000/2000 MS (Java/Others) Memory Limit: 65536/65536 K (Java/Others)
 * <p/>
 * Problem Description:
 * <p/>
 * Paypalor is learning a new type of programming language. Now he is concerning with the grammar of the language, especially the identifiers. One day, he found himself got interested in the substrings of a string. He is interested in a special type of substrings, which starts and ends with given string of Sfrom and Sto, note that it is possible Sfrom and Sto . Two substrings are considered to be different if the contents are not same, their occurrence position in the string does not matter. He wonders how many different substrings there are. Please help him count it.
 * <p/>
 * 输入
 * <p/>
 * There are several groups of test cases. Each test case of input consists of three lines. The first line contains a string t. The second and the third lines contain the indicator tag of  Sfrom and Sto. Note that all the three lines are non-empty strings of lowercase english letters. The maximum length of each string is 2000 characters.
 * <p/>
 * 输出
 * <p/>
 * For each test case , output the amount of different substrings of t starts with Sfromand ends with Sto.
 * <p/>
 * 样例输入
 * <p/>
 * abababab
 * <p/>
 * a
 * <p/>
 * b
 * <p/>
 * aba
 * <p/>
 * ab
 * <p/>
 * ba
 * <p/>
 * round
 * <p/>
 * ro
 * <p/>
 * on
 * <p/>
 * 样例输出
 * <p/>
 * 4
 * <p/>
 * 1
 * <p/>
 * 0
 */

public class Substring {
    public static void main(String args[]) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        String s, sFrom, sTo;
        ArrayList arrayList = new ArrayList();
//        while (sc.hasNext()) {
        s = sc.next();
        sFrom = sc.next();
        sTo = sc.next();
        arrayList.add(countSubstring(s, sFrom, sTo));
//        }

        for (int index = 0; index < arrayList.size(); index++) {
            int count = Integer.parseInt(arrayList.get(index).toString());
            System.out.println(count);
        }
    }

    public static int countSubstring(String s, String sFrom, String sTo) {
        int count = 0;
//        String reg = sFrom + "(.*)" + sTo;
//        Pattern p = Pattern.compile(reg);
//        Matcher m = p.matcher(s);
//        while(m.find()) {
//            System.out.println(m.group());
//            count++;
//        }

        ArrayList fromList = new ArrayList();
        ArrayList toList = new ArrayList();

        int i = 0;
        int j = 0;
        int f,t;

        while (i < s.length()) {
            f =s.indexOf(sFrom, i);
            if (f == -1) {
                break;
            }
            fromList.add(f);
            i = f + 1;
        }

        while (j < s.length()) {
            t =s.indexOf(sTo, j);
            if (t == -1) {
                break;
            }
            toList.add(t);
            j = t + 1;
        }


        for (int index = 0; index < fromList.size(); index++) {
            int from = Integer.parseInt(fromList.get(index).toString());
            System.out.println(from);
        }

        for (int index = 0; index < toList.size(); index++) {
            int to = Integer.parseInt(toList.get(index).toString());
            System.out.println(to);
        }

        for (int index = 0; index < fromList.size(); index++) {
            int from = Integer.parseInt(fromList.get(index).toString());
            for (int toIndex = 0; j < toList.size(); toIndex++) {
                int to = Integer.parseInt(toList.get(toIndex).toString());
                System.out.println(to);
            }
        }


        return fromList.size() * toList.size();
    }
}
