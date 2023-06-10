package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * ClassName: UserController
 * Package: com.imooc.mall.controller
 *
 * @Author 马学兴
 * @Create 2023/5/31 19:48
 * @Version 1.0
 * Description:用户控制器
 */
@Controller
@CrossOrigin(origins = "*", allowCredentials = "true")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public User personalPage(){
        return userService.getUser();
    }


    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws ImoocMallException {
        if (StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于八位
        if (password.length()<8){
            return ApiRestResponse.error(ImoocMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName,password);
        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws ImoocMallException {
        if (StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //保存用户信息时，不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.IMOOC_MALL_USER,user);
        return ApiRestResponse.success(user);
    }

    //更新个性签名
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session,@RequestParam String signature) throws ImoocMallException {
        User currentUser =(User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser==null){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    //登出，清除session
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.IMOOC_MALL_USER);
        return ApiRestResponse.success();
    }

    //管理员登录接口
    @PostMapping("/adminlogin")
    @ResponseBody
    public ApiRestResponse adminlogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws ImoocMallException {
        if (StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //检验是否是管理员
        if (userService.checkAdminRole(user)) {
            //是管理员，执行操作
            //保存用户信息时，不保存密码
            user.setPassword(null);
            session.setAttribute(Constant.IMOOC_MALL_USER,user);
            return ApiRestResponse.success(user);
        }else {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }

    }


}
