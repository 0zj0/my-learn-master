package com.example.entity.db2.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangjie
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
public class OrdersPO extends Model<OrdersPO> {

private static final long serialVersionUID=1L;

    @TableId("orderId")
    private Integer orderId;

    @TableField("userId")
    private Integer userId;

    @TableField("orderNo")
    private String orderNo;

    /**
     * 1: 未下单； 2 已下单，未支付；3 已支付，未成功；4 已支付，成功
     */
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

}
