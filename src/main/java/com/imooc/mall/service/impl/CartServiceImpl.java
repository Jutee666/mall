package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Cart;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: CartServiceImpl
 * Package: com.imooc.mall.service.impl
 *
 * @Author 马学兴
 * @Create 2023/6/4 20:12
 * @Version 1.0
 * Description:购物车模块实现类
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId){
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (CartVO cartVO : cartVOS) {
            cartVO.setTotalPrice(cartVO.getPrice()*cartVO.getQuantity());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count){
        validProduct(productId,count);//判断商品是否存在
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart==null){
            //这商品之前不在购物车，需要新增一个记录
           cart = new Cart();
           cart.setProductId(productId);
           cart.setUserId(userId);
           cart.setQuantity(count);
           cart.setSelected(Constant.Cart.CHECKED);
           cartMapper.insertSelective(cart);
        }else {
            //这个商品已经在购物车里，则数量想加
            count=cart.getQuantity()+count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }
    private void validProduct(Integer productId, Integer count){
        Product product = productMapper.selectByPrimaryKey(productId);
        //判断商品是否存在，商品是否上架
        if (product==null||product.getStatus().equals(Constant.SaleStatus.NOT_SALE)){
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
        }
        //判断商品库存
        if (count>product.getStock()){
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count){
        validProduct(productId,count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart==null){
            //这商品之前不在购物车，无法更新
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }else {
            //这个商品已经在购物车里，则更新数量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }
    @Override
    public List<CartVO>  delete(Integer userId, Integer productId){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart==null){
            //这商品之前不在购物车，无法删除
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }else {
            //这个商品已经在购物车里，则可以删除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart==null){
            //这商品之前不在购物车，选中失败
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }else {
            //这个商品已经在购物车里，则可以选中/不选中
            cartMapper.selectOrNot(userId,productId,selected);
        }
        return this.list(userId);
    }
    @Override
    public List<CartVO> selectAllOrNot(Integer userId,Integer selected){
        //改变选中状态
        cartMapper.selectOrNot(userId,null,selected);
        return this.list(userId);
    }
}
