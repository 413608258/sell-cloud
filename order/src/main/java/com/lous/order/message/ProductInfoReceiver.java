package com.lous.order.message;

import com.alibaba.fastjson.JSON;
import com.lous.order.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName : ProductInfoReceiver
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2019-01-24
 **/
@Component
@Slf4j
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock:%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        /**
         * message => ProductInfo
         * TODO: 其实不应该直接用 ProductInfo.class, 应该专门定义一个对外提供的对象, 例如: ProductInfoOutput
         */
        List<ProductInfo> productInfoList = JSON.parseArray(message, ProductInfo.class);
        //ProductInfo productInfo = JSON.parseObject(message, ProductInfo.class);
        log.info("从队列[{}] 接受到消息: {}", "productInfoList", productInfoList);
        productInfoList.stream()
                .forEach(productInfo -> {
                    //存到 Redis 中
                    stringRedisTemplate.opsForValue().set(
                            String.format(PRODUCT_STOCK_TEMPLATE, productInfo.getProductId()),
                            String.valueOf(productInfo.getProductStock())
                    );
                });
    }

}
