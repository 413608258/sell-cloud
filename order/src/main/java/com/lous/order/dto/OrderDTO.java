package com.lous.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lous.order.Serializer.Date2LongSerializer;
import com.lous.order.dataobject.OrderDetail;
import com.lous.order.enums.OrderStatusEnum;
import com.lous.order.enums.PayStatusEnum;
import com.lous.order.utils.EnumUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName : OrderDTO
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;
    /**
     * 买家姓名
     */
    private String buyerName;
    /**
     * 买家电话
     */
    private String buyerPhone;
    /**
     * 买家地址
     */
    private String buyerAddress;
    /**
     * 买家微信openid
     */
    private String buyerOpenid;
    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单装填,默认0新下单
     */
    private Integer orderStatus;
    /**
     * 支付状态,默认0未支付
     */
    private Integer payStatus;
    /**
     * 创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
