package edu.autocar.basic;

import edu.autocar.util.SqlUtil;

public class Test1 {

	public static void main(String[] args) {
		int[] items = { 1, 2, 3, 4};
		
		String test = SqlUtil.toString(items);
		System.out.println(test);
	}

}
