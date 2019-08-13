package com.example.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author zhangjie
 * @date 2019/8/13 15:26
 */
//@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SexEnum implements IEnum<Integer> {

    MALE(1,"男"),
    FEMALE(2,"女"),
    UN_KNOWN(3,"未知"),
    ;

    @EnumValue
    private int sex;
    private String desc;

    SexEnum(final int sex,final String desc) {
        this.sex = sex;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.sex;
    }

    public String getDesc(){
        return this.desc;
    }
}
