package com.others.Stairs;

/**
 * Created by muzilan on 15/11/7.
 */
public class Stairs3 {

    public static final int N = 10;//有N级楼梯
    public static final int ONE = 1;//一步可以迈1级
    public static final int TWO = 2;//一步可以迈2级
    public static final int THREE = 3;//一步可以迈3级

    public static void main(String[] args) {

        int max_ONE = N / ONE;//如果只迈ONE，最多需要max_ONE
        int max_TWO = N / TWO;//如果只迈TWO，最多需要max_TWO
        int max_Three = N / THREE;//如果只迈THREE，最多需要max_THREE

        for (int i = 0; i <= max_ONE; i++) {
            int temp_ONE = i;//一步1级已迈出的级数
            for (int j = 0; j <= max_TWO - temp_ONE / 2; j++) {
                int temp_TWO = j * 2;//一步2级已迈出的级数
                for (int k = 0; k <= max_Three - temp_ONE / 3 - temp_TWO / 3; k++) {
                    int temp_THREE = k * 3;//一步3级已迈出的级数
                    if (temp_ONE + temp_TWO + temp_THREE == N) System.out.println(i + " " + j + "  " + k);
                }
            }
        }
    }
/*
0 2  2
0 5  0
1 0  3
1 3  1
2 1  2
2 4  0
3 2  1
4 0  2
4 3  0
5 1  1
6 2  0
7 0  1
8 1  0
10 0  0
*/

/*    #include<iostream>
    using namespace std;

    int main()
    {
        int one,two,three,n,count=0;
        //分别表示:走1、2、3级的步数  总阶数  种数

        cout<<"输入阶梯数n:";
        cin>>n;

        //遍历所有的可能性
        for(one = 0; one <= n; one++)
        {
            for(two = 0; two <= n/2; two++)
            {
                for(three=0; three <= n/3; three++)
                {
                    int sum = one*1 + two*2 + three*3;

                    //满足条件的  输出
                    if(sum == n)
                    {
                        cout<<"("<<one<<","<<two<<","<<three<<")"<<endl;
                        count++;
                    }
                }
            }
        }

        cout<<"共有"<<count<<"种"<<endl;
        return 0;
    }*/


}
