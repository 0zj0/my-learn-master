package com.zj.dao;


import com.zj.entity.po.ProductInfo;

/**
 * Created by smlz on 2019/11/17.
 */
public interface ProductInfoMapper {

    ProductInfo selectProductInfoById(String productNo);
}
