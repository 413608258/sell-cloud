package com.lous.product.service;

import com.lous.product.ProductApplicationTests;
import com.lous.product.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class ICategoryServiceTest extends ProductApplicationTests {

    @Autowired
    private ICategoryService iCategoryService;

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> list = iCategoryService.findByCategoryTypeIn(Arrays.asList(1, 2));
        Assert.assertTrue(list.size() > 0);
    }
}