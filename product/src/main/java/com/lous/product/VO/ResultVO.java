package com.lous.product.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : ResultVO
 * @Description : http请求返回的最外层对象
 *
 * @author : Loushuai
 * @since : 2018-11-01
 **/
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 4106952180601791273L;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 具体内容
     */
    private T data;

}
