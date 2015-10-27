package test.mogujie;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by muzilan on 15/9/19.
 */
public class Bus {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int m = sc.nextInt();
        int [] ban = new int[n];
        int index = 0;
        while(index<n){

            ban[index++] =  sc.nextInt();
        }
        int che = 1;
        int sum = 0;
        for(int i = 0;i<n;i++){

            sum+=ban[i];
            if(sum<=m){
                continue;
            } else{
                che++;
                sum = ban[i-1];
            }
        }
        System.out.println(che);
    }

}
