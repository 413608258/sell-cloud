package com.lous.user.repository;

import com.lous.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName : UserInfoRepository
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-06
 **/
 
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByOpenid(String openid);
}
