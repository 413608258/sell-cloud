package com.lous.order.controller;

import com.lous.order.VO.ResultVO;
import com.lous.order.converter.OrderForm2OrderDTOConverter;
import com.lous.order.dto.OrderDTO;
import com.lous.order.enums.ResultEnum;
import com.lous.order.execption.SellException;
import com.lous.order.form.OrderForm;
import com.lous.order.service.IBuyerService;
import com.lous.order.service.IOrderService;
import com.lous.order.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : BuyerOrderController
 * @Description : 
 *
 * @author : Loushuai
 * @since : 2018-11-02
 **/
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IBuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 参数不正确， orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, new PageRequest(page, size));

        return ResultVOUtil.success(orderDTOPage.getContent());
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                            @RequestParam("orderId") String orderId){
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
