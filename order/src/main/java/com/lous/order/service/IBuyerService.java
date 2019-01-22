package com.lous.order.service;

import com.lous.order.dto.OrderDTO;

/**
 * @ClassName : IBuyerService
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
 
public interface IBuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);

}
