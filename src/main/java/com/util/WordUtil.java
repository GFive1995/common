package com.util;

import java.util.Arrays;
import java.util.List;

import com.spire.doc.Document;

/**
 * 查询Word是否存在关键字，并保留word路径到txt
 */
public class WordUtil {

	private static List<String> KEYWORDS = Arrays.asList("肉包子", "肉包子A");	// 查询关键字，多个关键字逗号分隔
	private static String PATH = "D:\\word测试";								// Word文件夹路径
	private static String FILEPATH = "D:\\word测试\\result.txt";				// 保存txt文件路径
	
	public static void main(String[] args) {
		StringBuffer resultTxt = new StringBuffer();
		if (KEYWORDS!=null && KEYWORDS.size()!=0) {
			List<String> fileNames = FileUtil.getFileNames(PATH);
			for (String keyword : KEYWORDS) {
				System.out.println("开始搜索。关键字："+keyword);
				resultTxt.append("开始搜索。关键字："+keyword+"\n");
				if (fileNames!=null && fileNames.size()!=0) {
					for (String fileName : fileNames) {
						Document doc = new Document(PATH+"\\"+fileName);
						String result = doc.getText();
						if (result.indexOf(keyword) != -1) {
							System.out.println("关键字："+keyword+"----------文件路径："+PATH+"\\"+fileName);
							resultTxt.append("关键字："+keyword+"----------文件路径："+PATH+"\\"+fileName+"\n");
						}
					}
				}
			}
			FileUtil.createFile(resultTxt.toString(), FILEPATH);
		}
	}
	
}
