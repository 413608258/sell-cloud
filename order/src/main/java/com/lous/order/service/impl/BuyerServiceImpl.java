package com.lous.order.service.impl;

import com.lous.order.dto.OrderDTO;
import com.lous.order.enums.ResultEnum;
import com.lous.order.execption.SellException;
import com.lous.order.service.IBuyerService;
import com.lous.order.service.IOrderService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : BuyerServiceImpl
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
 
@Service
@Data
@Slf4j
public class BuyerServiceImpl implements IBuyerService {

    @Autowired
    private IOrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("[取消订单] 查不到该订单，orderId={}", orderId);
             throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("[查询订单] 订单openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
