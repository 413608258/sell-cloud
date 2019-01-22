package com.lous.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.lous.order.VO.ResultVO;
import com.lous.order.client.ProductClient;
import com.lous.order.dataobject.OrderDetail;
import com.lous.order.dataobject.OrderMaster;
import com.lous.order.dataobject.ProductInfo;
import com.lous.order.dto.CartDTO;
import com.lous.order.dto.OrderDTO;
import com.lous.order.enums.OrderStatusEnum;
import com.lous.order.enums.PayStatusEnum;
import com.lous.order.enums.ResultEnum;
import com.lous.order.execption.SellException;
import com.lous.order.repository.OrderDetailRepository;
import com.lous.order.repository.OrderMasterRepository;
import com.lous.order.service.IOrderService;
import com.lous.order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @ClassName : OrderServiceImpl
 * @Description :
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        return null;
    }

//    @Autowired
//    private IProductService productService;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    //@Autowired
    //private IPushMessageService pushMessageService;
    //@Autowired
    //private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        AtomicReference<BigDecimal> orderAmount = new AtomicReference<>(new BigDecimal(BigInteger.ZERO));
        //1.查询商品（数量，价钱）
        orderDTO.getOrderDetailList().forEach(orderDetail ->{
            //ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            ResultVO response = productClient.findOne(orderDetail.getProductId());
            ProductInfo productInfo = (ProductInfo) response.getData();
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单总价
            orderAmount.set(productInfo.getProductPrice()
                    .multiply(BigDecimal.valueOf(orderDetail.getProductQuantity()))
                    .add(orderAmount.get())
            );
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        });
        //3.写入订单数据库(orderMaster和orderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount.get());
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        orderMasterRepository.save(orderMaster);
        //4.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()))
                .collect(Collectors.toList());
        //productService.decreaseStock(cartDTOList);
        productClient.decreaseStock(cartDTOList);

        //发送websocket 消息
        //webSocket.sendMessage("有新的订单，订单号：" + orderId);
        return orderDTO;
    }
/*
    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> optionalOrderMaster = orderMasterRepository.findById(orderId);
        OrderMaster orderMaster = optionalOrderMaster.orElseThrow(() -> new SellException(ResultEnum.ORDER_NOT_EXIST));

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXSIT);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = JSON.parseArray(JSON.toJSONString(orderMasterPage.getContent()), OrderDTO.class);
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderDTOList.size());

        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[取消订单]订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = JSON.parseObject(JSON.toJSONString(orderDTO), OrderMaster.class);
        //orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[取消订单]更新订单状态失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[取消订单]订单中无商品详情，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[完结订单]订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = JSON.parseObject(JSON.toJSONString(orderDTO), OrderMaster.class);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[完结订单]订单状态更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信模板消息
        pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[订单支付完成]订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[订单支付完成]订单状态不正确，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = JSON.parseObject(JSON.toJSONString(orderDTO), OrderMaster.class);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[订单支付完成]订单支付状态更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = JSON.parseArray(JSON.toJSONString(orderMasterPage.getContent()), OrderDTO.class);
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());

        return orderDTOPage;
    }*/
}
