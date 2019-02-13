package com.lous.user.service;


import com.lous.user.dataobject.UserInfo;

/**
 * @ClassName : UserService
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-06
 **/
 
public interface UserService {

    UserInfo findUserInfoByOpenid(String openid);
}
