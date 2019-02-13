package com.lous.user.enums;

import lombok.Getter;

/**
 * @ClassName : RoleEnum
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2019-01-28
 **/
@Getter
public enum RoleEnum {
    BUYER(1, "买家"),
    SELLER(2, "卖家"),
    ;

    private Integer code;

    private String message;

    RoleEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
