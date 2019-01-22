package com.lous.order.controller;

import com.lous.order.VO.ResultVO;
import com.lous.order.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName : ClientController
 * @Description : TODO: 使用 RestTemplate 调用服务的三种方式
 *
 * @author : Loushuai
 * @since : 2019-01-14
 **/
 @RestController
public class ClientController {

     @Autowired
     private LoadBalancerClient loadBalancerClient;
     @Autowired
     private ProductClient productClient;
//    @Autowired
//    private RestTemplate restTemplate;

     @GetMapping("/getProductMsg")
     public String getProductMsg(){
         //RestTemplate
         //1.第一种方式
//         RestTemplate restTemplate = new RestTemplate();
//         String response = restTemplate.getForObject("http://localhost:8011/buyer/product/findOne/123456", String.class);

         //2.第二种方式
         ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
         String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort() + "/buyer/product/findOne/123456");

         RestTemplate restTemplate = new RestTemplate();
         String response = restTemplate.getForObject(url, String.class);

         //3.第三种方式
         //String response = restTemplate.getForObject("http://PRODUCT/buyer/product/findOne/123456", String.class);

         return response;
     }

     @GetMapping("/getProduct")
     public ResultVO getProduct(){
         ResultVO resultVO = productClient.findOne("123456");
         return resultVO;
     }
}
