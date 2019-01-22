package com.lous.order.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author Loushuai
 * @since 2018-11-01
 */
@Entity
@Data
@DynamicUpdate
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String detailId;
    private String orderId;
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品价格
     */
    private BigDecimal productPrice;
    /**
     * 商品数量
     */
    private Integer productQuantity;
    /**
     * 商品小图
     */
    private String productIcon;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public static final String DETAIL_ID = "detail_id";

    public static final String ORDER_ID = "order_id";

    public static final String PRODUCT_ID = "product_id";

    public static final String PRODUCT_NAME = "product_name";

    public static final String PRODUCT_PRICE = "product_price";

    public static final String PRODUCT_QUANTITY = "product_quantity";

    public static final String PRODUCT_ICON = "product_icon";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
