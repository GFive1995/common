package com.datatype;


public class StringDemo {

	public static void main(String[] args) {
		String s1 = "abc";
		String s2 = "abc";
		String s3 = new String("abc");
		System.out.println("s1 == s2:-----:" + (s1 == s2));
		System.out.println("s1 == s3:-----:" + (s1 == s3));
		System.out.println("s1 == s3.intern():-----:" + (s1 == s3.intern()));
	}
	
}
