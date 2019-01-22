package com.lous.order.repository;

import com.lous.order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * <p>
 * 订单详情 Mapper 接口
 * </p>
 *
 * @author Loushuai
 * @since 2018-11-01
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
