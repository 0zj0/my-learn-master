package com.example.testBean;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 自定义类型过滤器
 * @author zhangjie
 * @date 2019/10/28 14:13
 */
public class MyFilterType implements TypeFilter {


    /**
     * 确定过滤器是否与给定的元数据的class匹配
     * @param metadataReader 目标类的元数据阅读器
     * @param metadataReaderFactory 获取元数据读取器的工厂  用于其他类(如超类和接口)
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类的注解源信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

        //获取当前类的class源信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();

        //获取当前类的资源信息
        Resource resource = metadataReader.getResource();

        System.out.println("MyFilterType---match---classMetadata:className:"+classMetadata.getClassName());

        boolean b = false;
        if(classMetadata.getClassName().contains("Dao")){
            //含有dao的类过滤返回true
            b = true;
        }
        System.out.println("MyFilterType---match:"+b);
        return b;
    }
}
