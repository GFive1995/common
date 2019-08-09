package com.spring;

import java.sql.Connection;

/**
 * 
 * 定义与spring兼容的事务属性的接口。基于类似于EJB CMT属性的传播行为定义。
 * 注意，除非启动一个实际的新事务，否则不会应用隔离级别和超时设置。
 * 此外，请注意，并非所有事务管理器都支持这些高级特性，因此在给定非默认值时可能会抛出相应的异常。
 * 
 * @version 1.0
 */
public interface TransactionDefinition {

	/**
	 * 支持当前事务;如果不存在，创建一个新的。
	 */
	int PROPAGATION_REQUIRED = 0;
	
	/**
	 * 支持当前事务;如果不存在非事务执行。
	 */
	int PROPAGATION_SUPPORTS = 1;
	
	/**
	 * 支持当前事务;如果当前事务不存在，则抛出异常。
	 */
	int PROPAGATION_MANDATORY = 2;
	
	/**
	 * 支持当前事务;创建一个新事务，如果存在当前事务，则挂起当前事务。
	 */
	int PROPAGATION_REQUIRES_NEW = 3;

	/**
	 * 不支持当前事务;而是始终以非事务方式执行。
	 */
	int PROPAGATION_NOT_SUPPORTED = 4;

	/**
	 * 不支持当前事务;如果当前事务存在，则抛出异常。
	 */
	int PROPAGATION_NEVER = 5;

	/**
	 * 如果当前事务存在，则在嵌套事务中执行，其行为类似{#PROPAGATION_REQUIRED}。
	 */
	int PROPAGATION_NESTED = 6;

	/**
	 * 使用底层数据存储的默认隔离级别。
	 * 所有其他级别都对应于JDBC隔离级别。
	 */
	int ISOLATION_DEFAULT = -1;
	
	/**
	 * 指示可能发生脏读取、不可重复读取和幻象读取。
	 * 这个级别的事务允许允许一个事务读取另一个事务未提交的数据，如果未提交的数据回滚，那么第一个事务读取的数据无效。
	 */
	int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;

	/**
	 * 指示防止脏读;可能会发生不可重复读取和幻象读取。
	 * 这个级别的事务禁止读取未提交的数据。
	 */
	int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;

	/**
	 * 指示防止脏读和不可重复读;幻象读取可以发生。
	 * 这个级别的事务禁止读取未提交的行数据，禁止一个事务读取行，另一个事务修改行，第一个事务重新读取获取不同的值。
	 */
	int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;

	/**
	 * 指示防止脏读、不可重复读和虚读。
	 * 这个级别的事务进一步禁止一个事务读取所有满足条件，另一个事务插入一条符合第一个事务条件的数据。
	 */
	int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;


	/**
	 * 使用底层事务系统的默认超时，如果不支持超时，则使用none。
	 */
	int TIMEOUT_DEFAULT = -1;


	/**
	 * 获取传播行为。
	 */
	int getPropagationBehavior();

	/**
	 * 获取隔离级别。
	 */
	int getIsolationLevel();

	/**
	 * 获取事务超时。
	 * 必须返回一个秒数，或者{#TIMEOUT_DEFAULT}。
	 * 只有与{#PROPAGATION_REQUIRED}或{@#PROPAGATION_REQUIRES_NEW}结合使用才有意义。
	 * 注意，如果事务管理器不支持超时，那么当给定的超时不是{#TIMEOUT_DEFAULT}时，它将抛出异常。
	 */
	int getTimeout();

	/**
	 * 获取是否为只读事务。
	 */
	boolean isReadOnly();

	/**
	 * 获取此事务的名称。
	 */
	String getName();
	
}
