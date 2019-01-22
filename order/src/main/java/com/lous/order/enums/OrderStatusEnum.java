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
public enum OrderStatusEnum implements ICodeEnum<Integer>{
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消")
    ;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
