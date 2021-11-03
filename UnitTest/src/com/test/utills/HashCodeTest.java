package com.test.utills;

public class HashCodeTest {
	public static void main(String[] args) {
		String name="111";
		String name1="111";
		String name2="12";
		System.out.println(name.hashCode());
		System.out.println(name1.hashCode());
		System.out.println(name2.hashCode());
		System.out.println(name.equals(name1));
		System.out.println(name.equals(name2));
	}
}
