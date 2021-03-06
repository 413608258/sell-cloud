package com.lous.product.utils;

import com.lous.product.enums.ICodeEnum;

/**
 * @ClassName : EnumUtil
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2018-11-06
 **/
 
public class EnumUtil {

    public static <T extends ICodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return  each;
            }
        }
        return null;
    }
}
