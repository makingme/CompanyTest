package com.test.classes;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.test.classes.InnerClassTest.Store;

public class InnerClassCall {
	public static void main(String[] args) {
		
		
		InnerClassTest a=new InnerClassTest();
		
		
		Map as=a.sMap;
		
		Set<String> mapSet=as.keySet();
		
		
		Set<Map.Entry<String,Store>> see=as.entrySet();
		
		for(Map.Entry<String, Store> data: see) {
			System.out.print(data.getKey()+": ");
			Store store=data.getValue();
			System.out.println(store.toString());
		}
		
		
		Iterator<String> mapIter =mapSet.iterator();
		while(mapIter.hasNext()) {
			String key=mapIter.next();
			Store store=(Store)as.get(key);
		//	System.out.println("Key"+key+"Value:"+store.name+store.age);
		}
		
		
		
		

	}

}
