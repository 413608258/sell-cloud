package com.lous.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : ProductVO
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2018-11-01
 **/
@Data
public class ProductVO implements Serializable {
    
    private static final long serialVersionUID = -6510914643015375577L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
