package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.vo.CartVO;

import java.util.List;

/**
 * ClassName: UserService
 * Package: com.imooc.mall.service
 *
 * @Author 马学兴
 * @Create 2023/5/31 19:50
 * @Version 1.0
 * Description:购物车模块服务类
 */

public interface CartService {

    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count);

    List<CartVO> update(Integer userId, Integer productId, Integer count);

    List<CartVO> delete(Integer userId, Integer productId);

    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected);

    List<CartVO> selectAllOrNot(Integer userId, Integer selected);
}
