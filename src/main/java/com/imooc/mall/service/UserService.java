package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.pojo.User;

/**
 * ClassName: UserService
 * Package: com.imooc.mall.service
 *
 * @Author 马学兴
 * @Create 2023/5/31 19:50
 * @Version 1.0
 * Description:用户模块服务类
 */

public interface UserService {
   User getUser();
   void register(String userName,String password) throws ImoocMallException;

   User login(String userName, String password) throws ImoocMallException;

    void updateInformation(User user) throws ImoocMallException;

    boolean checkAdminRole(User user);
}
