package test.didi;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int m = 0;
        int n = 0;
        ArrayList arrayList = new ArrayList();

        while (sc.hasNextInt() || sc.hasNext(";")) {
            while (sc.hasNextInt()) {
                arrayList.add(sc.nextInt());
                n++;
            }
            while (sc.hasNext(";")) {
                m++;
                while (sc.hasNextInt()) {
                    arrayList.add(sc.nextInt());
                }
            }
        }


        int[][] mutex = new int[m][n];
        int length =  m * n;
        int index = 0;



        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mutex[i][j] = Integer.parseInt(arrayList.get(index).toString());
                ;
            }
        }

        System.out.println(sum(mutex, m ,n));
    }

    public static int sum(int [][] mutex , int m, int n) {
        int sum = 0;
        int max = 0;
        for (int i = 0; i < m-1; i++) {
            for (int j = 0; j < n-1; j++) {
                sum = mutex[i][j] + mutex[i][j+1] + mutex[i+1][j] + mutex[i+1][j+1];
                if (sum > max) {
                    max = sum;
                }
            }
        }
        return sum;
    }
}
