package test1;

import java.util.ArrayList;

/**
 * Created by Catherine on 15/8/5.
 */
//有两个有序的集合，集合的每个元素都是一段范围，求其交集，例如集合{[4,8],[9,13]}和{[6,12]}的交集为{[6,8],[9,12]}

public class Test9 {
	class Set {                           //集合数据结构
		int low;
		int high;

		public Set() {
		}

		public Set(int low, int high) {
			this.low = low;
			this.high = high;
		}
	}

	public void qiuJiao(ArrayList<Set> A, ArrayList<Set> B) {
		ArrayList<Set> C = new ArrayList<Set>();
		for (Set a : A) {
			for (Set b : B) {
//将A中每个元素与B中每个元素求交集，如果所得的集合下界大于上界，则抛弃
				Set c = new Set();
				c.low = (a.low > b.low) ? a.low : b.low;
				c.high = (a.high < b.high) ? a.high : b.high;
				if (c.low < c.high) {
					System.out.println("<" + c.low + "," + c.high + ">");
					C.add(c);
				}
			}
		}
	}

	public static void main(String[] args) {
		Test9 Test9 = new Test9();
		Test9.Set t;
		ArrayList<Set> A = new ArrayList<Set>();             //初始化输入数组A
		t = Test9.new Set(4, 8);
		A.add(t);
		t = Test9.new Set(9, 13);
		A.add(t);
		ArrayList<Set> B = new ArrayList<Set>();            //初始化输入数组B
		t = Test9.new Set(6, 12);
		B.add(t);
		t = Test9.new Set(14, 23);
		B.add(t);
		Test9.qiuJiao(A, B);                          //数组求交
		ArrayList<Set> C = new ArrayList<Set>();
	}
}

