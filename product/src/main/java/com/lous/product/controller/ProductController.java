package com.lous.product.controller;

import com.lous.product.service.ICategoryService;
import com.lous.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : ProductController
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-14
 **/
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    /*@GetMapping("/list")
    public ResultVO<ProductVO> list(){

    }
*/
}
