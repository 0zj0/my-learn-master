package com.example.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 袁康云
 * @title MultipleTransactionSupport
 * @projectName vip_core
 * @date 2019/6/19 10:28
 */
public class MultipleTransactionSupport {
    /** 业务核心库事务 */
    @Autowired
    private DataSourceTransactionManager oneTransactionManager;

    /** 会员基本库事务 */
    @Autowired
    private DataSourceTransactionManager twoTransactionManager;

    private TransactionDefinition getTxDef() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(this.getClass().getName() + System.currentTimeMillis());
        def.setReadOnly(false);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return def;
    }

    /**
     * 核心库事务
     * @return
     */
    public TransactionGroup txGroup() {
        return new TransactionGroup(oneTransactionManager, oneTransactionManager.getTransaction(getTxDef()));
    }

    /**
     * vip库事务
     * @return
     */
    public TransactionGroup txGroupVip() {
        return new TransactionGroup(twoTransactionManager, twoTransactionManager.getTransaction(getTxDef()));
    }

    /**
     * 开启多个事务
     * @param transactionGroups
     * @return
     */
    public MultipleTransactionManager beginTransaction(TransactionGroup... transactionGroups) {
        MultipleTransactionManager multipleTransactionManager = new MultipleTransactionManager();
        for (TransactionGroup transactionGroup : transactionGroups) {
            multipleTransactionManager.appendTransaction(transactionGroup);
        }
        return multipleTransactionManager;
    }

    /**
     * 提交或回滚多个事务
     * @param transactionManager
     * @param commitFlag
     */
    public void handleTransactions(MultipleTransactionManager transactionManager, boolean commitFlag) {
        List<TransactionGroup> transactionGroups = transactionManager.transactionGroups;
        Collections.reverse(transactionGroups);
        for (TransactionGroup transactionGroup : transactionGroups) {
            if (commitFlag) {
                transactionGroup.transactionManager.commit(transactionGroup.transactionStatus);
            } else {
                transactionGroup.transactionManager.rollback(transactionGroup.transactionStatus);
            }
        }
    }

    /**
     * 事务组：一个事务管理和一个事务状态
     */
    public static class TransactionGroup {
        public TransactionGroup(DataSourceTransactionManager transactionManager, TransactionStatus transactionStatus) {
            this.transactionManager = transactionManager;
            this.transactionStatus = transactionStatus;
        }

        DataSourceTransactionManager transactionManager;
        TransactionStatus transactionStatus;
    }

    /**
     * 多事务管理器类：初始化、追加事务组
     */
    public static class MultipleTransactionManager {
        private List<TransactionGroup> transactionGroups = new ArrayList<>(3);

        private MultipleTransactionManager() {
        }

        void appendTransaction(TransactionGroup transactionGroup) {
            transactionGroups.add(transactionGroup);
        }
    }
}
