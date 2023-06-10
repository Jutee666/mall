package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.model.vo.OrderVO;

import java.util.List;

/**
 * ClassName: UserService
 * Package: com.imooc.mall.service
 *
 * @Author 马学兴
 * @Create 2023/5/31 19:50
 * @Version 1.0
 * Description:订单模块服务类
 */

public interface OrderService {
    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    String qrcode(String orderNo);

    void pay(String orderNo);

    void deliver(String orderNo);

    void finish(String orderNo);
}
