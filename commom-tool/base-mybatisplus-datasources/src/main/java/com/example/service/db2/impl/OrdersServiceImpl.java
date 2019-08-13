package com.example.service.db2.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.db2.OrdersDao;
import com.example.entity.db2.po.OrdersPO;
import com.example.service.db2.IOrdersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangjie
 * @since 2019-08-12
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, OrdersPO> implements IOrdersService {

}
