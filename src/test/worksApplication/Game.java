package test.worksApplication;

/**
 * Created by muzilan on 15/10/23.
 */
public class Game {
    public static void main(String[] args) {
        int[][] ground = {
                {-1,4,5,1},
                {2,-1,2,4},
                {3,3,-1,3},
                {4,2,1,2}};
        int row = 4;
        int col = 4;

        System.out.println("原来的表格如下：");
        print(ground);
        System.out.println(getMaxPoint(ground, row, col));
    }
    public static int getMaxPoint(int[][] ground, int n, int m){
        int max=0;
        int[][] result = new int[n][m];
        //get the max point of first column
        for(int i=0;i<n;i++){
            result[i][0]=ground[i][0];
        }
        //record the max point when you go to ground[i][j] cell
        for(int j=0;j<m;j++){
            for(int i=0;i<n;i++){
                result[i][j]=ground[i][j]+maxRs(result,i,j,n,m);
            }
        }
        //get the max point of last column
        for(int i=0;i<n;i++){
            if(max<result[0][i]){
                max=result[0][i];
            }
        }
        System.out.println("记录表格如下：");
        print(result);
        return max;
    }
    private static int maxRs(int[][] rs,int i, int j,int n, int m) {//求下一层的最小值
        if(j==0){
            return rs[i+1][j]>rs[i+1][j+1]?rs[i+1][j]:rs[i+1][j+1];
        }else if(j==n-1){
            return rs[i+1][j]>rs[i+1][j-1]?rs[i+1][j]:rs[i+1][j-1];
        }else{
            int max = rs[i+1][j]>rs[i+1][j-1]?rs[i+1][j]:rs[i+1][j-1];
            return max>rs[i+1][j+1]?max:rs[i+1][j+1];
        }
    }

    static void print(int[][] rs){//打印二维数组
        for(int i=0;i<rs.length;i++){
            for(int j=0;j<rs.length;j++){
                System.out.print(rs[i][j]+" ");
            }
            System.out.println();;
        }
    }

}



