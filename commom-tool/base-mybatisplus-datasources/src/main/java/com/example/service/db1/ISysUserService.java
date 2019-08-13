package com.example.service.db1;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.db1.po.SysUserPO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangjie
 * @since 2019-08-12
 */
public interface ISysUserService extends IService<SysUserPO> {

    List<SysUserPO> listAll();
}
