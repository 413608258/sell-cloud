package com.lous.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.lous.product.dataobject.ProductInfo;
import com.lous.product.dto.CartDTO;
import com.lous.product.enums.ProductStatusEnum;
import com.lous.product.enums.ResultEnum;
import com.lous.product.execption.SellException;
import com.lous.product.repository.ProductInfoRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lous.product.service.IProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName : ProductServiceImpl
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-01
 **/
@Service
//TODO: Redis 缓存相关注解
//@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductInfoRepository repository;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    //TODO: Redis 缓存相关注解
    @Cacheable(cacheNames = "product", key = "#productId")
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> productInfoOptional = repository.findById(productId);
        if (!productInfoOptional.isPresent()) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            //throw new ResponseBankException();
        }
        return repository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> finAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    //TODO: Redis 缓存相关注解
    @CachePut(cacheNames = "product", key = "#productInfo.productId")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        cartDTOList.forEach(cartDTO -> {
            ProductInfo productInfo = this.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            this.save(productInfo);
        });
    }

    /**
     * TODO: 数据库减库存过后, 向 MQ 发送消息同步库存
     * @param cartDTOList
     */
    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        //减库存操作
        List<ProductInfo> productInfoList = this.decreaseStockProcess(cartDTOList);
        /**
         * TODO: 向MQ发送消息时,不应该用 ProductInfo 对象,应该单独定义一个对外的包装类,例如: ProductInfoOutput
         *      这里偷懒了...
         */
        amqpTemplate.convertAndSend("productInfo", JSON.toJSONString(productInfoList));
    }

    /**
     * 减库存操作
     * @param cartDTOList
     * @return
     */
    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<CartDTO> cartDTOList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        cartDTOList.forEach(cartDTO -> {
            ProductInfo productInfo = this.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if ((result < 0)) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);

            this.save(productInfo);

            productInfoList.add(productInfo);
        });

        return productInfoList;
    }

    @Override
    public ProductInfo onSale(String productId) {
        Optional<ProductInfo> opt = repository.findById(productId);
        opt.orElseThrow(() -> new SellException(ResultEnum.PRODUCT_NOT_EXIST));
        ProductInfo productInfo = opt.get();
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        Optional<ProductInfo> opt = repository.findById(productId);
        opt.orElseThrow(() -> new SellException(ResultEnum.PRODUCT_NOT_EXIST));
        ProductInfo productInfo = opt.get();
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
