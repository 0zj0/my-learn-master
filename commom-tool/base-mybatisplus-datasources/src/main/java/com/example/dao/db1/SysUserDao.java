package com.example.dao.db1;

import com.example.config.mapper.MybatisMapper;
import com.example.entity.db1.po.SysUserPO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangjie
 * @since 2019-08-12
 */
public interface SysUserDao extends MybatisMapper<SysUserPO> {

    List<SysUserPO> listAll();
}
