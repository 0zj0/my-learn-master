package com.example.service.db1.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.db1.MqOperateRecordDao;
import com.example.entity.db1.po.MqOperateRecordPO;
import com.example.service.db1.IMqOperateRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * MQ操作记录表 服务实现类
 * </p>
 *
 * @author zhangjie
 * @since 2020-06-17
 */
@Service
public class MqOperateRecordServiceImpl extends ServiceImpl<MqOperateRecordDao, MqOperateRecordPO> implements IMqOperateRecordService {

}
