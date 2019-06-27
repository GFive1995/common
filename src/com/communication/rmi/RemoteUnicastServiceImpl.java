package com.communication.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * RMI 接口RemoteServiceInterface具体实现
 * 
 * @version 1.0
 * @date 2019年6月26日 下午1:25:18
 */
public class RemoteUnicastServiceImpl extends UnicastRemoteObject implements RemoteServiceInterface {

	private static final long serialVersionUID = 1L;

	/**
	 * Remote Object没有默认构造函数
	 * @throws RemoteException
	 */
	protected RemoteUnicastServiceImpl() throws RemoteException {
		super();
	}

	public List<UserInfo> queryAllUserinfo() throws RemoteException {
		List<UserInfo> userInfos = Lists.newArrayList();
		
		UserInfo userInfo1 = new UserInfo();
		userInfo1.setUserAge(18);
		userInfo1.setUserDesc("userDesc_18");
		userInfo1.setUserName("userName_18");
		userInfo1.setUserSex(true);
		userInfos.add(userInfo1);
		
		UserInfo userInfo2 = new UserInfo();
		userInfo2.setUserAge(20);
		userInfo2.setUserDesc("userDesc_20");
		userInfo2.setUserName("userName_20");
		userInfo2.setUserSex(false);
		userInfos.add(userInfo2);
		
		return userInfos;
	}

}
