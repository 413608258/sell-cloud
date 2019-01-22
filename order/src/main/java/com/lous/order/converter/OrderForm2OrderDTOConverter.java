package com.lous.order.converter;

import com.alibaba.fastjson.JSON;
import com.lous.order.dataobject.OrderDetail;
import com.lous.order.dto.OrderDTO;
import com.lous.order.enums.ResultEnum;
import com.lous.order.execption.SellException;
import com.lous.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : OrderForm2OrderDTOConverter
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = JSON.parseArray(orderForm.getItems(), OrderDetail.class);
        } catch (Exception e) {
            log.error("[对象转换] 错误，itemsString={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
