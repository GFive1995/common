package com.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Http4实现Http请求
 */
public class HttpClient4 {
	
	public static String doGet(String url) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String result = "";
		try {
			httpClient = HttpClients.createDefault();	// 通过址默认配置创建一个httpClient实例
			HttpGet httpGet = new HttpGet(url);			// 创建httpGet远程连接实例	
			httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");	// 设置请求头信息，鉴权
			// 设置配置请求参数
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(35000)			// 连接主机服务超时时间
					.setConnectionRequestTimeout(35000)	// 请求超时时间
					.setSocketTimeout(60000)			// 数据读取超时时间
					.build();
			httpGet.setConfig(requestConfig);			// 为httpGet实例设置配置
			response = httpClient.execute(httpGet);		// 执行get请求得到返回对象
			HttpEntity entity = response.getEntity();	// 通过返回对象获取返回数据
			result = EntityUtils.toString(entity);		// 通过EntityUtils中的toString方法将结果转换为字符串
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static String doPost(String url, Map<String, Object> paramMap) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		String result = "";
		httpClient = HttpClients.createDefault();	// 创建httpClient实例
		HttpPost httpPost = new HttpPost(url);		// 创建httpPost远程连接实例
		// 配置请求参数实例
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(35000)			// 设置连接主机服务超时时间
				.setConnectionRequestTimeout(35000)	// 设置连接请求超时时间
				.setSocketTimeout(60000)			// 设置读取数据连接超时时间
				.build();
		httpPost.setConfig(requestConfig);			// 为httpPost实例设置配置
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		// 封装post请求参数
		if (null != paramMap && paramMap.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, Object>> entrySet = paramMap.entrySet();		// 通过map集成entrySet方法获取entity
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();	// 循环遍历，获取迭代器
			while (iterator.hasNext()) {
				Entry<String, Object> mapEntry = iterator.next();
				nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8")); // 为httpPost设置封装好的请求参数
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		try {
			httpResponse = httpClient.execute(httpPost);	// httpClient对象执行post请求,并返回响应参数对象
			HttpEntity entity = httpResponse.getEntity();	// 从响应对象中获取响应内容
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != httpResponse) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
