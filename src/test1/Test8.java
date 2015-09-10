package test1;

import java.util.ArrayList;

/**
 * Created by Catherine on 15/8/5.
 */
//任意2n个整数，从其中选出n个整数，使得选出的n个整数和同剩下的n个整数之和的差最小。
public class Test8 {
	public ArrayList[] getArray() {
		return array;
	}

	public void setArray(ArrayList[] array) {
		this.array = array;
	}

	public ArrayList[] array;

	public static void main(String[] args) {
		Test8 m = new Test8();
//		m.setArray();
		m.pick();
	}


	public ArrayList[] pick() {

		return this.array;
	}


}
