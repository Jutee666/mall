package com.imooc.mall.common;

import com.google.common.collect.Sets;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Set;

/**
 * ClassName: Constant
 * Package: com.imooc.mall.common
 *
 * @Author 马学兴
 * @Create 2023/6/1 19:07
 * @Version 1.0
 * Description:常量值
 */
@Component
public class Constant {
    public static final String SALT="12TIT2IB`][";
    public static final String IMOOC_MALL_USER="IMOOC_MALL_USER";
    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void  setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR=fileUploadDir;
    }

    public interface ProductListOrder{
        Set<String> PRICE_ASC_DESC= Sets.newHashSet("price desc","price asc");
    }

    public interface SaleStatus{
        int NOT_SALE=0;//商品下架状态
        int SALE=1;
    }
    public interface Cart{
        int UN_CHECKED=0;//购物车未选中状态
        int CHECKED=1;
    }

    public enum OrderStatusEnum{
        CANCELED(0,"用户已取消"),
        NOT_PAID(10,"未付款"),
        PAID(20,"已付款"),
        DELIVERED(30,"已发货"),
        FINISHED(40,"交易完成");

        private String value;
        private int code;

        OrderStatusEnum(int code,String value) {
            this.value = value;
            this.code = code;
        }
        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode()==code){
                    return orderStatusEnum;
                }
            }
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ENUM);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
