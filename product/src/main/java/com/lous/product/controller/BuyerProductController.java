package com.lous.product.controller;

import com.alibaba.fastjson.JSON;
import com.lous.product.VO.ProductInfoVO;
import com.lous.product.VO.ProductVO;
import com.lous.product.VO.ResultVO;
import com.lous.product.dataobject.ProductCategory;
import com.lous.product.dataobject.ProductInfo;
import com.lous.product.dto.CartDTO;
import com.lous.product.service.ICategoryService;
import com.lous.product.service.IProductService;
import com.lous.product.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName : BuyerProductController
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-01
 **/
 
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list")
    //TODO: Redis缓存相关注解 spel表达式可动态传key, condition：满足条件缓存， unless: 满足条件就不缓存，
    //@Cacheable(cacheNames = "product", key = "123", condition = "", unless = "#result.getCode() != 0")
    public ResultVO list(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        List<ProductInfoVO> productInfoVOList = JSON.parseArray(JSON.toJSONString(productInfoList), ProductInfoVO.class);
        Map<Integer, List<ProductInfoVO>> listMap = productInfoVOList.stream()
                .collect(Collectors.groupingBy(ProductInfoVO::getCategoryType));

        //2.查询类目（一次查询）
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        System.out.println(productCategoryList.get(0).getCategoryType());
        System.out.println(listMap.get(2));
        //3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        productCategoryList.stream().forEach(category ->{
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(category.getCategoryType());
            productVO.setCategoryName(category.getCategoryName());
            productVO.setProductInfoVOList(listMap.get(category.getCategoryType()));
            productVOList.add(productVO);
        });

        return ResultVOUtil.success(productVOList);
    }
    @GetMapping("/findOne/{productId}")
    public ResultVO findOne(@PathVariable("productId") String productId){
        ProductInfo productInfo = productService.findOne(productId);
        return ResultVOUtil.success(productInfo);
    }

    /**
     * 扣库存
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList){
        productService.decreaseStock(cartDTOList);
    }
}
