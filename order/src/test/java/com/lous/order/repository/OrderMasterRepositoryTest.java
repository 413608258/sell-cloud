package com.lous.order.repository;

import com.lous.order.OrderApplicationTests;
import com.lous.order.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1314321");
        orderMaster.setBuyerPhone("18868066666");
        orderMaster.setBuyerName("Lous");
        orderMaster.setBuyerAddress("杭州市西湖区");
        orderMaster.setBuyerOpenid("123456");
        orderMaster.setOrderAmount(new BigDecimal(6.6));
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertTrue(result != null);
    }

    @Test
    public void findByBuyerOpenid() {
    }
}