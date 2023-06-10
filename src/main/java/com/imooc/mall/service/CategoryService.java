package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
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
 * Description:分类目录Service
 */
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
