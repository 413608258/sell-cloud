package com.lous.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName : ProductInfoVO
 * @Description : 商品详情
 *
 * @author : Loushuai
 * @since : 2018-11-01
 **/
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = -5054049614006692237L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;

    @JsonProperty("type")
    private Integer categoryType;
}
