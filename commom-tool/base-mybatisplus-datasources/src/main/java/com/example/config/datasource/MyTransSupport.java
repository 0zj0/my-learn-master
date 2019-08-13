package com.example.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

@Component
public class MyTransSupport {

	/**
	 * 数据库1 基本事务
	 */
	@Autowired
	private DataSourceTransactionManager oneTransactionManager;

	/**
	 * 数据库2 基本事务
	 */
	@Autowired
	private DataSourceTransactionManager twoTransactionManager;

	private TransactionDefinition getTxDef(){
		org.springframework.transaction.support.DefaultTransactionDefinition def = new org.springframework.transaction.support.DefaultTransactionDefinition();
		def.setName(this.getClass().getName()+System.currentTimeMillis());
		def.setReadOnly(false);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		return def;
	}

	/**
	 * 开启业务核心库事务
	 * @return
	 */
	public TransactionStatus getTxStatus1(){
		return oneTransactionManager.getTransaction(getTxDef());
	}

	/**
	 * 提交业务核心库事务
	 * @return
	 */
	public boolean commitOrRoll1(TransactionStatus status, boolean r){
		if(r){
			oneTransactionManager.commit(status);
		}else{
			oneTransactionManager.rollback(status);
		}
		return r;
	}

	/**
	 * 开启会员基本库事务
	 * @return
	 */
	public TransactionStatus getTxStatus2(){
		return twoTransactionManager.getTransaction(getTxDef());
	}

	/**
	 * 提交会员基本库事务
	 * @return
	 */
	public boolean commitOrRoll2(TransactionStatus status, boolean r){
		if(r){
			twoTransactionManager.commit(status);
		}else{
			twoTransactionManager.rollback(status);
		}
		return r;
	}
}
