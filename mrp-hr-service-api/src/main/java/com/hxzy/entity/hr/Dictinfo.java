package com.hxzy.entity.hr;

import java.io.Serializable;

/**
 * @author 
 * 公共数据字典类型明细
 */
public class Dictinfo implements Serializable {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 字典类型编号,连接dicttype表
     */
    private Integer typecode;

    /**
     * 字典明细名称
     */
    private String name;

    /**
     * 排序，值越大越靠前
     */
    private Integer seq;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypecode() {
        return typecode;
    }

    public void setTypecode(Integer typecode) {
        this.typecode = typecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}