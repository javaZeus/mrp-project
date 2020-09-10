package com.hxzy.entity.hr;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 
 * 公共数据字典类型分类
 */
public class Dicttype implements Serializable {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 字典类型名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String name;

    private static final long serialVersionUID = 1L;

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
}