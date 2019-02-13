package com.lous.user.service.impl;

import com.lous.user.dataobject.UserInfo;
import com.lous.user.repository.UserInfoRepository;
import com.lous.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : UserServiceImpl
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-06
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository repository;
    @Override
    public UserInfo findUserInfoByOpenid(String openid) {
        UserInfo sellerInfo = repository.findByOpenid(openid);
        return sellerInfo;
    }
}
