package com.example.service.db1.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.db1.SysUserDao;
import com.example.entity.db1.po.SysUserPO;
import com.example.service.db1.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangjie
 * @since 2019-08-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserPO> implements ISysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public List<SysUserPO> listAll() {
       return sysUserDao.listAll();
    }
}
