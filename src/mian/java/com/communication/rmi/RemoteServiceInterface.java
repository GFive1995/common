package com.communication.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;



public interface RemoteServiceInterface extends Remote {

	/**
	 * 
	 * 方法描述:负责查询目前已经注册的所有用户信息
	 *
	 * @return
	 * @throws RemoteException
	 * 
	 * @date 2019年6月26日 下午1:24:17
	 */
	public List<UserInfo> queryAllUserinfo() throws RemoteException;
	
}
