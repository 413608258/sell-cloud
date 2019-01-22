package com.lous.order.repository;

import com.lous.order.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author Loushuai
 * @since 2018-11-01
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
