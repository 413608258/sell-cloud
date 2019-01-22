package com.lous.product.service;

import com.lous.product.dataobject.ProductInfo;
import com.lous.product.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName : IProductService
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2018-11-01
 **/
 
public interface IProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> finAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);    
}
