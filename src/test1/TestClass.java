package test1;

import java.util.ArrayList;

/**
 * Created by Catherine on 15/8/23.
 */
public class TestClass {

	private static int[] list = {88, 459, 5262, 88, -17, 677, 88, 677, -17, 459, 5262};

	public static int repetNubmer(int list[]){
		int result=list[0];
		for(int i=1,size = list.length;i<size;i++){
			//相同的两个数据异或操作为0，任何数据与0异或结果为该数据本身。重复2遍的将全部为0，最后剩下的数字即为重复奇数次的数据。
			result = result^list[i];
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(repetNubmer(list));
	}
}




