package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Java原生HttpURLConnection实现Http请求
 */
public class HttpURL {
	
	public static String doGet(String httpurl) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		String result = null;
		try {
			URL url = new URL(httpurl);										// 创建远程URL对象
			connection = (HttpURLConnection) url.openConnection();			// 通过远程URL对象打开一个连接并强制转换成HttpURLConnection类
			connection.setRequestMethod("GET");								// 设置连接方法为：GET
			connection.setConnectTimeout(15000);							// 设置连接服务器超时时间：15000毫秒
			connection.setReadTimeout(60000);								// 设置读取远程返回数据时间：60000毫秒
			connection.connect();											// 发送请求
			if (connection.getResponseCode() == 200) {						// 通过connection获取输入流
				is = connection.getInputStream();
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));// 封装输入流并设置字符集
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}
				result = sbf.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != br) {
					br.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection.disconnect();
		}
		return result;
	}

	public static String doPost(String httpUrl, String param) {
		HttpURLConnection connection = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedReader br = null;
		String result = null;
		try {
			URL url = new URL(httpUrl);										// 创建远程URL对象
			connection = (HttpURLConnection) url.openConnection();			// 通过远程URL对象打开一个连接并强制转换成HttpURLConnection类
			connection.setRequestMethod("POST");							// 设置连接方法为：POST
			connection.setConnectTimeout(15000);							// 设置连接服务器超时时间：15000毫秒
			connection.setReadTimeout(60000);								// 设置读取远程返回数据时间：60000毫秒
			connection.setDoOutput(true);									// 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
			connection.setDoInput(true);									// 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
			// 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
			connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
			os = connection.getOutputStream();								// 通过连接对象获取一个输出流
			os.write(param.getBytes());										// 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
			if (connection.getResponseCode() == 200) {						// 通过连接对象获取一个输入流，向远程读取
				is = connection.getInputStream();
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));// 封装输入流并设置字符集
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}
				result = sbf.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != br) {
					br.close();
				}
				if (null != os) {
					os.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection.disconnect();
		}
		return result;
	}
}
