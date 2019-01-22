package com.lous.order.enums;

import lombok.Getter;

/**
 * @ClassName : OrderStatusEnum
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
@Getter
public enum PayStatusEnum implements ICodeEnum<Integer> {
    WAIT(0, "未支付"),
    SUCCESS(1, "支付成功")
    ;

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
