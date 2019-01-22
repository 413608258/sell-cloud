package com.lous.product.utils;

import com.lous.product.VO.ResultVO;

/**
 * @ClassName : ResultVOUtil
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2018-11-01
 **/
 
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);

        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code, String msg){
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
