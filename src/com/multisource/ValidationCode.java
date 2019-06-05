package com.multisource;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 图片验证码
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月4日 上午9:42:04
 */
public class ValidationCode {

	// 图形验证码的字符集合，系统将随机从这个字符串中选择一些字符作为验证码
	private static String codeChars = "%#0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	
	public static void main(String[] args) {
		StringBuilder validationCode = new StringBuilder();
		BufferedImage image = getImage(validationCode);
		FileOutputStream os;
		try {
			os = new FileOutputStream("d:\\tp.JPEG");
			ImageIO.write(image, "JPEG", os);
			System.out.println(validationCode);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 方法描述:返回图片对象
	 *
	 * @param validationCode
	 * @return
	 * 
	 */
	public static BufferedImage getImage(StringBuilder validationCode) {
		// 获得验证码集合的长度
		int charsLength = codeChars.length();
		// 设置图形验证码的长和宽（图形的大小）
		int width = 90, height = 24;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获得用于输出文字的Graphics对象
		Graphics g = image.getGraphics();
		Random random = new Random();
		// 随机设置要填充的颜色
		g.setColor(getRandomColor(80, 250));
		// 填充图形背景
		g.fillRect(0, 0, width, height);
		// 设置初始字体
		g.setFont(new Font("Times New Roman", Font.ITALIC, height));
		// 随机设置字体颜色
		g.setColor(getRandomColor(120, 180));
		// 验证码的随机字体
		String[] fontNames = { "Times New Roman", "Book antiqua", "Arial" };
		// 随机生成3个到5个验证码
		for (int i = 0; i < 3 + random.nextInt(3); i++) {
			// 随机设置当前验证码的字符的字体
			g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC, height));
			// 随机获得当前验证码的字符
			char codeChar = codeChars.charAt(random.nextInt(charsLength));
			validationCode.append(codeChar);
			// 随机设置当前验证码字符的颜色
			g.setColor(getRandomColor(10, 100));
			// 在图形上输出验证码字符，x和y都是随机生成的
			g.drawString(String.valueOf(codeChar), 16 * i + random.nextInt(7), height - random.nextInt(6));
		}
		// 关闭Graphics对象
		g.dispose();
		return image;
	}
	
	/**
	 * 
	 * 方法描述:返回一个随机颜色(Color对象)
	 *
	 * @param minColor
	 * @param maxColor
	 * @return
	 * 
	 */
	private static Color getRandomColor(int minColor, int maxColor) {
		Random random = new Random();
		// 保存minColor最大不会超过255
		if (minColor > 255)
			minColor = 255;
		// 保存minColor最大不会超过255
		if (maxColor > 255)
			maxColor = 255;
		// 获得红色的随机颜色值
		int red = minColor + random.nextInt(maxColor - minColor);
		// 获得绿色的随机颜色值
		int green = minColor + random.nextInt(maxColor - minColor);
		// 获得蓝色的随机颜色值
		int blue = minColor + random.nextInt(maxColor - minColor);
		return new Color(red, green, blue);
	}

	/**
	 * 
	 * 方法描述:返回验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 */
	public String generateValidationCode(HttpServletRequest request, HttpServletResponse response) throws Exception	{
		// 下面三条记录是关闭客户端浏览器的缓冲区
		// 这三条语句都可以关闭浏览器的缓冲区，但是由于浏览器的版本不同，对这三条语句的支持也不同
		// 因此，为了保险起见，建议同时使用这三条语句来关闭浏览器的缓冲区
		response.setHeader("ragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession();
		// 设置session对象5分钟失效
		session.setMaxInactiveInterval(5 * 60); 
		// 用于保存最后随机生成的验证码
		StringBuilder validationCode = new StringBuilder();
		BufferedImage image = getImage(validationCode);
		// 将验证码保存在session对象中，key为validation_code
		session.setAttribute("validationCode", validationCode.toString());
		OutputStream os = response.getOutputStream();
		// 以JPEG格式向客户端发送图形验证码
		ImageIO.write(image, "JPEG", os);
		return null;
	}

	/**
	 * 
	 * 方法描述:验证验证码
	 *
	 * @param validationCode
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	public String validateValidationCode(String validationCode,
	                                     HttpServletRequest request, HttpServletResponse response) 
	                                    		 throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", "success");
		HttpSession session = request.getSession();
		String validationCodeInSession = (String) session.getAttribute("validationCode");
		validationCode = (validationCode == null) ? "" : validationCode.trim();
		validationCodeInSession = (validationCodeInSession == null) ? null : validationCodeInSession.trim();
		if (!validationCode.equalsIgnoreCase(validationCodeInSession)) {
			map.put("validationCodeErrorMsg", "验证码错误...");
			map.put("result", "error");
		}
		JSONObject jsonObj = JSONObject.parseObject(map.toString());
		return jsonObj.toString();
	}
}