package com.communication.rmi;

import java.rmi.Naming;
import java.util.List;

/**
 * 
 * 客户端调用RMI
 * 
 * @version 1.0
 * @date 2019年6月26日 下午1:45:04
 */
public class RemoteClient {

	public static void main(String[] args) throws Exception {
		// 使用Java名称服务技术进行RMI接口查找
		RemoteServiceInterface remoteServiceInterface = (RemoteServiceInterface) Naming.lookup("rmi://127.0.0.1:1099/queryAllUserinfo");
		List<UserInfo> userInfos = remoteServiceInterface.queryAllUserinfo();
		
		System.out.println("userInfos.size()=" + userInfos.size());
	}
	
}
