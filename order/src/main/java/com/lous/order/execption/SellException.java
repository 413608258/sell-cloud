package com.lous.order.execption;

import com.lous.order.enums.ResultEnum;
import lombok.Getter;

/**
 * @ClassName : SellException
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
@Getter
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
