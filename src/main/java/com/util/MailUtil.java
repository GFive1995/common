package com.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件
 */
public class MailUtil {

	private static String SMTP_HOST = "smtp.qq.com";			// 主机名
	private static String SMTP_PORT = "25";						// 端口号
	private static String SMTP_PROTOCOL = "smtp";				// 邮件连接协议
	private static String SMTP_AUTH = "true";					// 是否开启身份认证
	private static String AUTH_USER = "974345887@qq.com";		// 发送邮件账号
	private static String AUTH_PASSWORD = "uuqdolnvnytbbcej";	// 发送邮件密码(可以使用服务验证码，不使用真正的密码)
	private static String FROM_ADDRESS = "974345887@qq.com";	// 发送邮箱号
	private static String TO_ADDRESS = "1756096867@qq.com";		// 接收邮箱号
	
	public static void main(String[] args) {
		System.out.println(sendMail());
	}

	public static boolean sendMail() {
		Boolean succ = true;
		Properties properties = new Properties();
		properties.put("mail.smtp.host", SMTP_HOST);			
		properties.put("mail.smtp.port", SMTP_PORT);			
		properties.put("mail.smtp.protocol", SMTP_PROTOCOL);	
		properties.put("mail.smtp.auth", SMTP_AUTH);
//		properties.put("mail.smtp.ssl.enable", "true");			// 设置是否使用ssl安全连接 
		properties.put("mail.debug", "true");					// 设置时候显示dubug信息
		// 获取会话对象
		MyAuthenticator myAuthenticator = new MyAuthenticator(AUTH_USER, AUTH_PASSWORD);
		Session session = Session.getDefaultInstance(properties, myAuthenticator);
		// 获取邮件对象
		Message message = new MimeMessage(session);
		try {
			// 设置发件人邮箱地址
			message.setFrom(new InternetAddress(FROM_ADDRESS));
			// 设置收件人邮箱地址
//			message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(TO_ADDRESS),new InternetAddress(TO_ADDRESS)});
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO_ADDRESS));
			// 设置邮件标题
			message.setSubject("邮件标题");
			// 设置邮件内容
			message.setText("邮件内容");
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport.send(message, message.getAllRecipients());
		} catch (Exception e) {
			succ = false;
			e.printStackTrace();
		}
		return succ;
	}
	
}

/**
 * 邮箱账号密码认证
 */
class MyAuthenticator extends Authenticator {
	private String user;
	private String password;

	public MyAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}
}
