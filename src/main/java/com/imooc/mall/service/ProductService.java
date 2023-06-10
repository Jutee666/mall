package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.model.vo.CategoryVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ClassName: CategoryService
 * Package: com.imooc.mall.service
 *
 * @Author 马学兴
 * @Create 2023/6/1 22:55
 * @Version 1.0
 * Description:商品Service
 */
public interface ProductService {


    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNumber, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);
}
