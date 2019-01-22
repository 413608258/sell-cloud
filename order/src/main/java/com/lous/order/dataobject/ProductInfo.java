package com.lous.order.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lous.order.Serializer.Date2LongSerializer;
import com.lous.order.enums.ProductStatusEnum;
import com.lous.order.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author Loushuai
 * @since 2018-11-01
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 8812909907710129941L;
    @Id
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 单价
     */
    private BigDecimal productPrice;
    /**
     * 库存
     */
    private Integer productStock;
    /**
     * 描述
     */
    private String productDescription;
    /**
     * 小图
     */
    private String productIcon;
    /**
     * 商品状态
     */
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    /**
     * 类目编号
     */
    private Integer categoryType;
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
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

    public static final String PRODUCT_ID = "product_id";

    public static final String PRODUCT_NAME = "product_name";

    public static final String PRODUCT_PRICE = "product_price";

    public static final String PRODUCT_STOCK = "product_stock";

    public static final String PRODUCT_DESCRIPTION = "product_description";

    public static final String PRODUCT_ICON = "product_icon";

    public static final String CATEGORY_TYPE = "category_type";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
