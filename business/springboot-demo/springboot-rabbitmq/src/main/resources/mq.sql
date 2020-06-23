CREATE TABLE `mq_operate_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发送方名称，填写项目名',
  `exchangeName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交换机名称',
  `routingKey` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由键',
  `data` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生产者和消费者约定的参数（格式不限） ',
  `consumeQueues` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '消费成功的队列名，适合一个交换机绑定多个队列的场景：queue_a,queue_b',
  `errorMsg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '错误堆栈信息',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '处理状态；1.未处理2.成功 3 失败',
  `startDealTime` bigint(13) NOT NULL DEFAULT '0' COMMENT '延迟队列预计处理时间：如当前时间为10:10，半小时后执行则应该保存为10:40对应的时间戳',
  `ctime` bigint(13) NOT NULL DEFAULT '0' COMMENT '添加时间',
  `updTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `retryCnt` int(11) DEFAULT '0' COMMENT '重试次数',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  KEY `idx_startDealTime` (`startDealTime`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='MQ操作记录表';