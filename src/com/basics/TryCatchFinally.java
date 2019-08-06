package com.basics;

/**
 * 
 * try catch finally 验证分析
 * 1、如果try语句中有return语句，返回当前try中变量此时对应的值，此后对变量做任何修改，不影响try中return的值。
 * 2、如果finally语句中有return语句，try或catch中返回语句忽略。
 * 3、如果finally抛出异常，整个try catch finally抛出异常。
 * 
 * 使用注意
 * 尽量在try或者catch中使用return语句。通过finally中达到对try或者catch返回值修改不可靠。
 * finally中尽量避免使用return，因为会屏蔽try、catch中的异常信息。
 * finally中避免再次抛出异常，否则整个包含try语句块的方法会抛出异常，并且屏蔽掉try、catch中的异常。
 * 
 * @version 1.0
 */
public class TryCatchFinally {

	public static void main(String[] args) {
//		System.out.println(tryCatchFinally_01());
//		System.out.println(tryCatchFinally_02());
//		System.out.println(tryCatchFinally_03());
//		System.out.println(tryCatchFinally_04());
//		System.out.println(tryCatchFinally_05());
//		System.out.println(tryCatchFinally_06());
//		System.out.println(tryCatchFinally_07());
	}
	
	public static String tryCatchFinally_01() {
		String t = "";
		try {
			t = "try";
			return t;
		} catch (Exception e) {
			t = "catch";
			return t;
		} finally {
			t = "finally";
		}
	}
	
	@SuppressWarnings("finally")
	public static String tryCatchFinally_02() {
		String t = "";
		try {
			t = "try";
			return t;
		} catch (Exception e) {
			t = "catch";
			return t;
		} finally {
			t = "finally";
			return t;
		}
	}
	
	public static String tryCatchFinally_03() {
		String t = "";
		try {
			t = "try";
			Integer.valueOf(null);
			return t;
		} catch (Exception e) {
			t = "catch";
			return t;
		} finally {
			t = "finally";
		}
	}
	
	@SuppressWarnings("finally")
	public static String tryCatchFinally_04() {
		String t = "";
		try {
			t = "try";
			Integer.valueOf(null);
			return t;
		} catch (Exception e) {
			t = "catch";
			return t;
		} finally {
			t = "finally";
			return t;
		}
	}
	
	public static String tryCatchFinally_05() {
		String t = "";
		try {
			t = "try";
			Integer.valueOf(null);
			return t;
		} catch (Exception e) {
			t = "catch";
			Integer.valueOf(null);
			return t;
		} finally {
			t = "finally";
		}
	}
	
	@SuppressWarnings("finally")
	public static String tryCatchFinally_06() {
		String t = "";
		try {
			t = "try";
			Integer.valueOf(null);
			return t;
		} catch (Exception e) {
			t = "catch";
			Integer.valueOf(null);
			return t;
		} finally {
			t = "finally";
			return t;
		}
	}
	
	@SuppressWarnings("finally")
	public static String tryCatchFinally_07() {
		String t = "";
		try {
			t = "try";
			Integer.valueOf(null);
			return t;
		} catch (Exception e) {
			t = "catch";
			Integer.valueOf(null);
			return t;
		} finally {
			t = "finally";
			Integer.valueOf(null);
			return t;
		}
	}
}
