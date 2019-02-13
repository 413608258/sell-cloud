package com.lous.user.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName : CookieUtil
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-08
 **/
 
public class CookieUtil {

    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request, String name){
        Cookie cookie = readCookieMap(request).get(name);
        return cookie;
    }

    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        /*Map<String, Cookie> cookieMap = Arrays.stream(cookies)
                .collect(Collectors.toMap(Cookie::getName, e -> e));*/

        HashMap<String, Cookie> cookieMap = new HashMap<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
