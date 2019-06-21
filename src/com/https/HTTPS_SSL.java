package com.https;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class HTTPS_SSL {
 
	private static String PASSWORD;			// 证书密码
	private static String PATH;				// 证书地址
	private static String URL;				// HTTPS请求地址
	private static String DATA;				// 请求参数
	private static int CONNECTTIMEOUTMS;	// 连接超时时间
	private static int READTIMEOUTMS;		// 传输超时时间
	
	private String requestSSL() throws Exception {
		BasicHttpClientConnectionManager connManager;
		// 证书
		char[] password = PASSWORD.toCharArray();
		FileInputStream instream = new FileInputStream(new File(PATH));
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(instream, password);

		// 实例化密钥库 & 初始化密钥工厂
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, password);

		// 创建 SSLContext
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

		SSLConnectionSocketFactory sslConnectionSocketFactory =
				new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
						new DefaultHostnameVerifier());

		connManager =
				new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory> create()
						.register("http", PlainConnectionSocketFactory.getSocketFactory())
						.register("https", sslConnectionSocketFactory).build(), null, null, null);

		HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();

		HttpPost httpPost = new HttpPost(URL);

		RequestConfig requestConfig =
				RequestConfig.custom().setSocketTimeout(READTIMEOUTMS).setConnectTimeout(CONNECTTIMEOUTMS).build();
		httpPost.setConfig(requestConfig);

		StringEntity postEntity = new StringEntity(DATA, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		return EntityUtils.toString(httpEntity, "UTF-8");
	}

}
