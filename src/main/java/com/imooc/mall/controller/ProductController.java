package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ProductController
 * Package: com.imooc.mall.controller
 *
 * @Author 马学兴
 * @Create 2023/6/4 13:49
 * @Version 1.0
 * Description:前台商品Controller
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("商品详情")
    @GetMapping("/product/detail")
    public ApiRestResponse detail(@RequestParam Integer id){
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }
    @ApiOperation("商品列表")
    @GetMapping("/product/list")
    public ApiRestResponse list(ProductListReq productListReq){
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }

}
