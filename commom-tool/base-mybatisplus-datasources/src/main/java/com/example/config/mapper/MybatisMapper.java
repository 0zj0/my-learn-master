package com.example.config.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 屏蔽mybatis的mapper的部分接口，防止开发人员误操作，mapper都采用MybatisMapper，不采用BaseMapper
 * @author zdh
 * @date 2019/5/24
 */
public interface MybatisMapper<T> extends BaseMapper<T> {
    @Deprecated
    @Override
    default int deleteById(Serializable id){
        return -1;
    }

    @Deprecated
    @Override
    default int deleteByMap(@Param("cm") Map<String, Object> columnMap){
        return -1;
    }

    @Deprecated
    @Override
    default int delete(@Param("ew") Wrapper<T> wrapper){
        return -1;
    }

    @Deprecated
    @Override
    default int deleteBatchIds(@Param("coll") Collection<? extends Serializable> idList){
        return -1;
    }

    @Deprecated
    @Override
    default int updateById(T entity){
        return -1;
    }

    @Deprecated
    @Override
    default int update(T entity, Wrapper<T> updateWrapper){
        return -1;
    }
}
