package com.lous.order.enums;

import lombok.Getter;

/**
 * @ClassName : ProductStatusEnum
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2018-11-01
 **/
 @Getter
public enum ProductStatusEnum implements ICodeEnum<Integer>{
    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
