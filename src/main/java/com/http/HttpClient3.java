package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * Http3实现Http请求
 */
public class HttpClient3 {
	
	public static String doGet(String url) {
		InputStream is = null;
		BufferedReader br = null;
		String result = null;
		HttpClient httpClient = new HttpClient();								// 创建httpClient实例
		httpClient.getHttpConnectionManager().getParams()						// 先获取连接管理器对象，再获取参数对象,再进行参数的赋值
				.setConnectionTimeout(15000);									// 设置http连接主机服务超时时间：15000毫秒
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);	// 设置get请求超时为60000毫秒
		// 设置请求重试机制，默认重试次数：3次，参数设置为true，重试机制可用，false相反
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));
		try {
			int statusCode = httpClient.executeMethod(getMethod);				// 执行GET方法
			if (statusCode != HttpStatus.SC_OK) {								// 判断是否成功
				System.err.println("Method faild: " + getMethod.getStatusLine());
			} else {
				is = getMethod.getResponseBodyAsStream();						// 通过getMethod实例，获取远程的一个输入流
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));	// 封装输入流并设置字符集
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp).append("\r\n");
				}
				result = sbf.toString();
			}

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
			getMethod.releaseConnection();
		}
		return result;
	}

	public static String doPost(String url, Map<String, Object> paramMap) {
		InputStream is = null;
		BufferedReader br = null;
		String result = null;
		HttpClient httpClient = new HttpClient();						// 创建httpClient实例对象
		httpClient.getHttpConnectionManager()
				.getParams()
				.setConnectionTimeout(15000);							// 设置httpClient连接主机服务器超时时间：15000毫秒
		PostMethod postMethod = new PostMethod(url);					// 创建post请求方法实例对象
		postMethod.getParams()
				.setParameter(HttpMethodParams.SO_TIMEOUT, 60000);		// 设置post请求超时时间
		NameValuePair[] nvp = null;
		if (null != paramMap && paramMap.size() > 0) {
			nvp = new NameValuePair[paramMap.size()];					// 创建键值参数对象数组，大小为参数的个数
			Set<Entry<String, Object>> entrySet = paramMap.entrySet();	// 循环遍历参数集合map
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();	
			int index = 0;
			while (iterator.hasNext()) {
				Entry<String, Object> mapEntry = iterator.next();
				// 从mapEntry中获取key和value创建键值对象存放到数组中
				try {
					nvp[index] = new NameValuePair(mapEntry.getKey(),
							new String(mapEntry.getValue().toString().getBytes("UTF-8"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				index++;
			}
		}
		if (null != nvp && nvp.length > 0) {							// 判断nvp数组是否为空	
			postMethod.setRequestBody(nvp);								// 将参数存放到requestBody对象中
		}
		try {
			int statusCode = httpClient.executeMethod(postMethod);		// 执行POST方法
			if (statusCode != HttpStatus.SC_OK) {						// 判断是否成功
				System.err.println("Method faild: " + postMethod.getStatusLine());
			}
			
			is = postMethod.getResponseBodyAsStream();					// 获取远程返回的数据
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));// 封装输入流并设置字符集
			StringBuffer sbf = new StringBuffer();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sbf.append(temp).append("\r\n");
			}
			result = sbf.toString();
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
			postMethod.releaseConnection();
		}
		return result;
	}
}
