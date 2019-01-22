package com.lous.order.dataobject;

import com.lous.order.enums.OrderStatusEnum;
import com.lous.order.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author Loushuai
 * @since 2018-11-01
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster implements Serializable {

    private static final long serialVersionUID = 4275009961463546038L;
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /**
     * 支付状态,默认0未支付
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public static final String ORDER_ID = "order_id";

    public static final String BUYER_NAME = "buyer_name";

    public static final String BUYER_PHONE = "buyer_phone";

    public static final String BUYER_ADDRESS = "buyer_address";

    public static final String BUYER_OPENID = "buyer_openid";

    public static final String ORDER_AMOUNT = "order_amount";

    public static final String ORDER_STATUS = "order_status";

    public static final String PAY_STATUS = "pay_status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
