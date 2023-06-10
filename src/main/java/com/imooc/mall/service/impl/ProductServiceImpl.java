package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.query.ProductListQuery;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ProductServiceImpl
 * Package: com.imooc.mall.service.impl
 *
 * @Author 马学兴
 * @Create 2023/6/3 14:55
 * @Version 1.0
 * Description:商品Service实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;

    @Override
    public void add(AddProductReq addProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq,product);
        Product productOld = productMapper.selectByName(addProductReq.getName());
        if (productOld!=null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count==0){
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Product updateProduct){
        Product productOld = productMapper.selectByName(updateProduct.getName());
        //同名且不同id，不能继续修改
        if (productOld!=null&&!productOld.getId().equals(updateProduct.getId())){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count==0){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey(id);
        if (productOld == null) {//查不到记录，无法删除，删除失败
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus){
        productMapper.batchUpdateSellStatus(ids, sellStatus);
    }
    @Override
    public PageInfo listForAdmin(Integer pageNumber, Integer pageSize){
        PageHelper.startPage(pageNumber,pageSize);
        List<Product> products = productMapper.selectlistForAdmin();
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }
    @Override
    public Product detail(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }


    @Override
    public PageInfo list(ProductListReq productListReq){
        //构建Query对象
        ProductListQuery productListQuery = new ProductListQuery();
        //搜索处理
        if (!StringUtils.isEmpty(productListReq.getKeyword())){
            String keyword = new StringBuilder().append("%").append(productListReq.getKeyword()).append("%").toString();
            productListQuery.setKeyword(keyword);
        }
        //目录处理：如果查某个目录下的商品，不仅是需要查该目录下的，还要把所有子目录的所有商品都查出来，所以要拿到一个目录id的List
        if (productListReq.getCategoryId()!=null){
            List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(productListReq.getCategoryId());
            //平铺展开
            ArrayList<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(productListReq.getCategoryId());//
            getCategoryIds(categoryVOList,categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }

        //排序处理
        String orderBy = productListReq.getOrderBy();
        if (Constant.ProductListOrder.PRICE_ASC_DESC.contains(orderBy)){//排序使用枚举

            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize(),orderBy);
        }else {
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize());
        }
        List<Product> productList = productMapper.selectList(productListQuery);
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }
    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds){
        for (CategoryVO categoryVO : categoryVOList) {
            if (categoryVO!=null){
                categoryIds.add(categoryVO.getId());
                getCategoryIds(categoryVO.getChildCategory(),categoryIds);
            }
        }
    }
}
