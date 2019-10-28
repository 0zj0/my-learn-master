package com.example.testImport;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 通过ImportSelector 的实现类 导入组件（组件的id为全类名路径）
 * @author zhangjie
 * @date 2019/10/28 15:22
 */
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.example.testBean.Person"};
    }
}
