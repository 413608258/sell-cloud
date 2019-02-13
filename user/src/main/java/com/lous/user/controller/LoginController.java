package com.lous.user.controller;

import com.lous.user.VO.ResultVO;
import com.lous.user.constant.CookieConstant;
import com.lous.user.constant.RedisConstant;
import com.lous.user.dataobject.UserInfo;
import com.lous.user.enums.ResultEnum;
import com.lous.user.enums.RoleEnum;
import com.lous.user.service.UserService;
import com.lous.user.utils.CookieUtil;
import com.lous.user.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : Controller
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-1-28
 **/
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 买家登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid,
                          HttpServletResponse response){
        //1. openid 和数据库的数据进行匹配
        UserInfo userInfo = userService.findUserInfoByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getCode(), ResultEnum.LOGIN_FAIL.getMessage());
        }
        //2. 判断角色
        if (!RoleEnum.BUYER.getCode().equals(userInfo.getRole())) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR.getCode(), ResultEnum.ROLE_ERROR.getMessage());
        }
        //3. cookie 里设置 openid = abc
        CookieUtil.set(response, CookieConstant.OPENID, openid, CookieConstant.EXPIRE);

        return ResultVOUtil.success();
    }
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletRequest request,
                           HttpServletResponse response){
        //判断是否已登录
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            String redisToken = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            if (!StringUtils.isEmpty(redisToken)) {
                return ResultVOUtil.success();
            }
        }

        //1. openid 和数据库的数据进行匹配
        UserInfo userInfo = userService.findUserInfoByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getCode(), ResultEnum.LOGIN_FAIL.getMessage());
        }
        //2. 判断角色
        if (!RoleEnum.SELLER.getCode().equals(userInfo.getRole())) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR.getCode(), ResultEnum.ROLE_ERROR.getMessage());
        }
        //3. redis设置 key = UUID, value = openid
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(
                String.format(RedisConstant.TOKEN_PREFIX, token),
                openid,
                RedisConstant.EXPIRE,
                TimeUnit.SECONDS
        );
        //4. cookie 里设置 openid = UUID
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return ResultVOUtil.success();
    }

}
