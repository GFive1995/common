package com.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * 汉字转换拼音
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月24日 上午10:24:36
 */
public class PinyinTool {

	public static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

	public static enum Type {
		UPPERCASE, // 大写
		LOWERCASE, // 小写
		FIRSTUPPER // 第一个字符大写
	}

	public PinyinTool() {
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}

	public static String toPinYin(String str) throws BadHanyuPinyinOutputFormatCombination {
		return toPinYin(str, "", Type.UPPERCASE);
	}

	public static String toPinYin(String str, String spera) throws BadHanyuPinyinOutputFormatCombination {
		return toPinYin(str, spera, Type.UPPERCASE);
	}

	/**
	 * 
	 * 汉字转换拼音
	 *
	 * @param str	汉字
	 * @param spera 分割
	 * @param type	类型
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 * 
	 * @author wangcy
	 * @date 2018年11月28日11:45:56
	 */
	public static String toPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
		if (str == null || str.trim().length() == 0)
			return "";
		if (type == Type.UPPERCASE)
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		else
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

		String py = "";
		String temp = "";
		String[] t;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((int) c <= 128)
				py += c;
			else {
				t = PinyinHelper.toHanyuPinyinStringArray(c, format);
				if (t == null)
					py += c;
				else {
					temp = t[0];
					if (type == Type.FIRSTUPPER)
						temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
					py += temp + (i == str.length() - 1 ? "" : spera);
				}
			}
		}
		return py.trim();
	}

	public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
		System.out.println("葫芦====" + toPinYin("葫芦"));
		System.out.println("葫芦====" + toPinYin("葫芦", ";", Type.UPPERCASE));
		String name = toPinYin("葫芦", ";", Type.UPPERCASE);
		String pinyin = "";
		String[] names = name.split(";");
		for (int i = 0; i < names.length; i++) {
			pinyin += names[i].substring(0, 1);
		}
		System.out.println(pinyin);
	}
	
}
