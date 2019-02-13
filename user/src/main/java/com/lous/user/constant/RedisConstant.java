package com.lous.user.constant;

/**
 * @ClassName : RedisConstant
 * @Description : Redis 常量
 *
 * @author : Loushuai
 * @since : 2018-11-08
 **/
 
public interface RedisConstant {

    String TOKEN_PREFIX = "token:%s";

    Integer EXPIRE = 7200; //2小时

}
