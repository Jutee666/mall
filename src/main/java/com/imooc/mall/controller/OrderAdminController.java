package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: OrderAdminController
 * Package: com.imooc.mall.controller
 *
 * @Author 马学兴
 * @Create 2023/6/6 13:40
 * @Version 1.0
 * Description:订单后台管理
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class OrderAdminController {
    @Autowired
    OrderService orderService;

    @GetMapping("/admin/order/list")
    @ApiOperation("管理员订单列表")
    public ApiRestResponse listForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/admin/order/delivered")
    @ApiOperation("管理员发货")
    //订单状态: 0用户已取消，10未付款（初始状态），20已付款，30已发货，40交易完成
    public ApiRestResponse delivered(@RequestParam String orderNo) {
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }

    @PostMapping("/order/finish")
    @ApiOperation("完结订单")//管理员和用户都可用
    //订单状态: 0用户已取消，10未付款（初始状态），20已付款，30已发货，40交易完成
    public ApiRestResponse finish(@RequestParam String orderNo) {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
}
