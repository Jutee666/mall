package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.UpdateProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * ClassName: ProductAdminController
 * Package: com.imooc.mall.controller
 * @Author 马学兴
 * @Create 2023/6/3 14:45
 * @Version 1.0
 * Description:后台商品管理controller
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ProductAdminController {
    @Autowired
    ProductService productService;
    @ApiOperation("后台添加商品")
    @PostMapping("admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.add(addProductReq);
        return ApiRestResponse.success();
    }
    @ApiOperation("后台上传图片")
    @PostMapping("admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest,@RequestParam("file") MultipartFile file){
        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf("."));//截取后缀
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName= uuid.toString()+suffixName;
        //创建文件
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);//文件夹
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);//目标文件
        if (!fileDirectory.exists()){
            if (!fileDirectory.mkdirs()){
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return  ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL()+""))+"/images/" + newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }
    }
    private URI getHost(URI uri){
        URI effectiveURI;
        try {//scheme（协议类型，如 http、https）、userInfo（用户信息），host（主机名）、port（端口号）、path（路径）、query（查询字符串）和 fragment（片段标识符）
            effectiveURI=new URI(uri.getScheme(),uri.getUserInfo(),uri.getHost(),uri.getPort(),null,null,null);
        } catch (URISyntaxException e) {
            effectiveURI=null;
        }
        return effectiveURI;
    }
    @ApiOperation("后台更新商品")
    @PostMapping("/admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq,product);
        productService.update(product);
        return ApiRestResponse.success();
    }
    @ApiOperation("后台删除商品")
    @PostMapping("/admin/product/delete")
    public ApiRestResponse deleteProduct(@RequestParam Integer id){
        productService.delete(id);
        return ApiRestResponse.success();
    }
    @ApiOperation("后台批量上下架接口")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus( @RequestParam Integer[] ids,@RequestParam Integer sellStatus){
        productService.batchUpdateSellStatus(ids,sellStatus);
        return ApiRestResponse.success();
    }
    @ApiOperation("后台商品接口")
    @PostMapping("/admin/product/list")
    public ApiRestResponse list( @RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        PageInfo pageInfo = productService.listForAdmin(pageNumber, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

}
