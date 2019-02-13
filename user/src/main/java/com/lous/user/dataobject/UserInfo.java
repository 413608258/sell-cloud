package com.lous.user.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName : UserInfo
 * @Description : UserInfo
 *
 * @author : Loushuai
 * @since : 2018-11-06
 **/
@Data
@Entity
public class UserInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Integer role;

}
