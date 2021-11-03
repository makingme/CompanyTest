package com.test.collections;

import java.util.HashMap;
import java.util.Map;

public class MapSizeTest {
	public static void main(String[] args) {
		//파일인덱스, 라인인덱스,구분자, start offset, end length 정보, 상태 정보 기록
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
