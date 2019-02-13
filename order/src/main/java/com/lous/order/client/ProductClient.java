package com.lous.order.client;

import com.lous.order.VO.ResultVO;
import com.lous.order.dto.CartDTO;
import com.lous.order.utils.ResultVOUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName : ProductClient
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-15
 **/

@FeignClient(name = "product", fallback = ProductClient.ProductClientHystrix.class)
public interface ProductClient {

    @GetMapping("/buyer/product/findOne/{productId}")
    ResultVO findOne(@PathVariable("productId") String productId);

    @PostMapping("/buyer/product/decreaseStock")
    void decreaseStock(@RequestBody List<CartDTO> cartDTOList);

    @Component
    class ProductClientHystrix implements ProductClient{

        @Override
        public ResultVO findOne(String productId) {
            throw new RuntimeException();
            //return new ResultVO();
        }

        @Override
        public void decreaseStock(List<CartDTO> cartDTOList) {
            throw new RuntimeException();
        }
    }

}
