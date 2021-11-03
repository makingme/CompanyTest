package com.test.collections;

public class EnumTest {
	public static void main(String[] args) {
		String a="UMSUI";
		
		if (a.equals(MasterTableKind.UMSUI.toString())) {
			System.out.println(true);
		}
		System.out.println(MasterTableKind.UMSUI);
	}
}


enum MasterTableKind {
    UMSUI,SYSAPI;
}