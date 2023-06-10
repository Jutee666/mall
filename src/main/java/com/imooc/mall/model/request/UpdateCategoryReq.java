package com.imooc.mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ClassName: AddCategoryReq
 * Package: com.imooc.mall.model.request
 *
 * @Author 马学兴
 * @Create 2023/6/1 21:46
 * @Version 1.0
 * Description:更新目录的请求类
 */
public class UpdateCategoryReq {

    @NotNull(message = "id不能为null")
    private Integer id;
    @Size(min = 2,max = 5)
    private String name;//目录名
    @Max(3)
    private Integer type;//目录层级
    private  Integer parentId;//父目录的ID
    private Integer orderNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "UpdateCategoryReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }
}
