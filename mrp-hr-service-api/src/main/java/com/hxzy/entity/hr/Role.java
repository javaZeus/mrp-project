package com.hxzy.entity.hr;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 
 * 
 */
public class Role implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色认证标识
     */
    @NotBlank(message = "认证标识不能为空")
    private String authentication;

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

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}