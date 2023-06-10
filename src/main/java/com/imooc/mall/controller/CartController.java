package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: CartController
 * Package: com.imooc.mall.controller
 *
 * @Author 马学兴
 * @Create 2023/6/4 19:56
 * @Version 1.0
 * Description:购物车Controller
 *
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/list")
    @ApiOperation("购物车列表")
    public ApiRestResponse list(){
        //内部获取用户Id，防止横向越权
        List<CartVO> cartList = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(cartList);
    }
    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count){
        List<CartVO> cartVOList = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }
    @PostMapping("/update")
    @ApiOperation("更新购物车")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count){
        List<CartVO> cartVOList = cartService.update(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }
    @PostMapping("/delete")
    @ApiOperation("删除购物车")
    public ApiRestResponse delete(@RequestParam Integer productId){
        //不能传入userId，cartId,否则可以删除其他人的购物车
        List<CartVO> cartVOList = cartService.delete(UserFilter.currentUser.getId(), productId);
        return ApiRestResponse.success(cartVOList);
    }
    @PostMapping("/select")
    @ApiOperation("选中/不选择购物车的某商品")
    public ApiRestResponse select(@RequestParam Integer productId,@RequestParam Integer selected){

        List<CartVO> cartVOList = cartService.selectOrNot(UserFilter.currentUser.getId(), productId,selected);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/selectAll")
    @ApiOperation("全选中/全不选择购物车的某商品")
    public ApiRestResponse select(@RequestParam Integer selected){

        List<CartVO> cartVOList = cartService.selectAllOrNot(UserFilter.currentUser.getId(),selected);
        return ApiRestResponse.success(cartVOList);
    }
}
