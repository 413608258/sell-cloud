package com.lous.product.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 类目表
 * </p>
 *
 * @author Loushuai
 * @since 2018-11-01
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    /**
     * 类目名称
     */
    private String categoryName;
    /**
     * 类目编号
     */
    private Integer categoryType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public static final String CATEGORY_ID = "category_id";

    public static final String CATEGORY_NAME = "category_name";

    public static final String CATEGORY_TYPE = "category_type";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
