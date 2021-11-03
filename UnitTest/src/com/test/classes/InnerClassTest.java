package com.test.classes;

import java.util.HashMap;
import java.util.Map;

public class InnerClassTest {
	
	class Store{
		public Store(String name, int age) {
			this.name=name;
			this.age=age;
		}
		String name="default";
		int age=0;
		
		@Override
		public String toString() {

			return "name: "+name+", age:"+age;
		}
	}
	
	Map<String, Store> sMap=new HashMap<String, Store>();
	
	public InnerClassTest() {
		sMap.put("1", new Store("1¹ø",1));
		sMap.put("2", new Store("2¹ø",2));
		sMap.put("3", new Store("3¹ø",3));
	}

	
	
}
