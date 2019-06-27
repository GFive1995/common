package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import com.google.common.collect.Lists;

public class FileUtil {

	/**
	 * 
	 * 方法描述:判断路径文件是否存在，如果不存在则创建
	 *
	 * @param path
	 * 
	 * @author wangcy
	 * @date 2019年6月26日 下午4:44:51
	 */
	public static void mkdir(String path) {
        try {
        	File file = new File(path);
        	if (!file.getParentFile().exists()) { 
        		file.getParentFile().mkdirs();
        	}
        	if (file.exists()) { 
        		file.delete();
        	}
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 方法描述:生成文件
	 *
	 * @param jsonString	文件内容
	 * @param filePath		文件地址
	 * @author wangcy
	 * @date 2019年6月27日 下午4:51:00
	 */
	public static void createFile(String jsonString, String filePath) {
		Writer write = null;
		try {
        	mkdir(filePath);
            File file = new File(filePath);
            write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
	
	/**
	 * 
	 * 方法描述:获取路径下所有文件名
	 *
	 * @param filePath
	 * @return
	 * 
	 * @author wangcy
	 * @date 2019年6月27日 下午4:53:42
	 */
	public static List<String> getFileNames(String filePath) {
		List<String> fileNames = Lists.newArrayList();
		File file = new File(filePath + File.separator);
		File[] files = file.listFiles();
		if (files!=null && files.length!=0) {
			for (File tempFile : files) {
				if (tempFile.isFile()) {
					fileNames.add(tempFile.getName());
				}
			}
		}
		return fileNames;
	}
	
	/**
	 * 
	 * 方法描述:获取文件的内容
	 *
	 * @param filePath
	 * @return
	 * 
	 * @author wangcy
	 * @date 2019年6月27日 下午4:54:45
	 */
	public static String getFileInfo(String filePath) {
		StringBuilder result = new StringBuilder();   
		BufferedReader br = null;
		try {   
            br = new BufferedReader(new FileReader(filePath + File.separator));	
            String s = "";
            while ((s = br.readLine()) != null) {												
            	result.append(System.lineSeparator() + s);										
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
        }
		return result.toString();
	}
	
}
