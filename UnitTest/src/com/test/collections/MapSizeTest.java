package com.test.collections;

import java.util.HashMap;
import java.util.Map;

public class MapSizeTest {
	public static void main(String[] args) {
		//�����ε���, �����ε���,������, start offset, end length ����, ���� ���� ���
		//8byte 8byte 32byte, 8byte, 8byte, 2byte
		//66byte
		Map<Integer,byte[]> a= new HashMap<Integer,byte[] >(5000000);
		
		byte[] bytes=new byte[66];
		for(int i=0;i<bytes.length;i++) {
			bytes[i]=(byte)i;
		}
		int maxSize=5000000;
		for(int i=0;i<500000;i++) {
			a.put(i, bytes);
		}
		
		System.out.println(a.get(1).toString());
	}
}
