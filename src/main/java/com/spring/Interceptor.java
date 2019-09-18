package com.spring;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * 拦截器
 * 新增修改时只能录入 数字、汉字、下划线
 *
 */
public class Interceptor extends HandlerInterceptorAdapter {

	private static final List<String> URLLISTS = Arrays.asList("create", "update");	// 拦截地址
	private static final String REGEX = "^[\u4E00-\u9FA5A-Za-z0-9_]+$";				// 拦截正则表达式
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		for (String string : URLLISTS) {
			if (url.indexOf(string) != -1) {
				String json = request.getParameter("json");
				Map<String, Object> jsonMap = JSONArray.parseObject(json);
				if (jsonMap!=null && jsonMap.size()!=0) {
					Pattern pattern = Pattern.compile(REGEX);
					for (Entry<String, Object> entry : jsonMap.entrySet()) {
						Matcher matcher = pattern.matcher(entry.getValue().toString());
						if (!matcher.matches()) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}
