package com.imooc.mall.exception;

/**
 * ClassName: ImoocMallException
 * Package: com.imooc.mall.exception
 *
 * @Author 马学兴
 * @Create 2023/6/1 13:59
 * @Version 1.0
 * Description:统一异常
 */
public class ImoocMallException extends RuntimeException{
    private final Integer code;
    private final String message;
    public ImoocMallException(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    public ImoocMallException(ImoocMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(),exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
