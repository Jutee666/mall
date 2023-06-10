package com.imooc.mall.util;

import com.imooc.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName: MD5Utils
 * Package: com.imooc.mall.util
 *
 * @Author 马学兴
 * @Create 2023/6/1 18:59
 * @Version 1.0
 * Description:MD5加密工具
 */
public class MD5Utils {
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((strValue+ Constant.SALT).getBytes()));
    }

    //用这个方法测试生成MD5的值
    public static void main(String[] args) {
        String md5Str = null;
        try {
            md5Str = getMD5Str("1234");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println(md5Str);
    }
}
