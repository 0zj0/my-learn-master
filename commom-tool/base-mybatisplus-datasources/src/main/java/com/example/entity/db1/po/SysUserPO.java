package com.example.entity.db1.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.entity.enums.SexEnum;
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
@TableName("sys_user")
public class SysUserPO extends Model<SysUserPO> {

private static final long serialVersionUID=1L;

    private Integer id;

    @TableField("userName")
    private String userName;

    //private Integer sex;
    private SexEnum sexEnum;

    private Integer age;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
