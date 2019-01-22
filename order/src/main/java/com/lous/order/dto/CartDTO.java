package com.lous.order.dto;

import lombok.Data;

/**
 * @ClassName : CartDTO
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
 
@Data
public class CartDTO {
    /**
     * 商品id
     */
    private String productId;
    /**
     * 购买数量
     */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
