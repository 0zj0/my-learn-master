package com.zj.dao;

import com.zj.entity.po.OrderInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by smlz on 2019/11/17.
 */
public interface OrderInfoMapper {

    OrderInfo selectOrderInfoByIdAndUserName(@Param("orderNo") String orderNo, @Param("userName") String userName);

    int insertOrder(OrderInfo orderInfo);

    OrderInfo selectOrderInfoById(@Param("orderNo") String orderNo);
}
