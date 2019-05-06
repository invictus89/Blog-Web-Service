package edu.autocar.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SqlUtil {
	public static String toString(int[] items) {
		return Arrays.stream(items)
				.mapToObj(Integer::toString)
				.collect(Collectors.joining(","));
	}
	
	public static String toString(String[] items) {
		return Arrays.stream(items)
				.map(v->"'" + v + "'")
				.collect(Collectors.joining(","));
	}
	
	public static String toString(List<String> list) {
		return list.stream()
				.map(v->"'" + v + "'")
				.collect(Collectors.joining(","));
	}
}
