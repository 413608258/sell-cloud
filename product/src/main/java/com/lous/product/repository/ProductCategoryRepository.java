package com.lous.product.repository;

import com.lous.product.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p>
 * 类目表 Mapper 接口
 * </p>
 *
 * @author Loushuai
 * @since 2018-11-01
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
