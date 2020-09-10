package com.hxzy.common.service;

import com.hxzy.common.vo.Result;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 通用的业务逻辑层
 * @param <T>
 */
public interface BaseService<T> {

    /**
     * 新增
     * @param entity
     * @return
     */
    Result insert(T entity);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Result deleteById(Serializable id);

    /**
     * 自定义删除条件
     * @param params
     * @return
     */
    Result deleteByMap(Map<String, Object> params);


    /**
     * 根据主键批量删除
     * @param ids
     * @return
     */
    Result deleteBatchIds(Collection<? extends Serializable> ids);

    /**
     * 根据主键更新
     * @param entity
     * @return
     */
    Result updateById( T entity);


    /**
     * 根据id查询
     * @param id
     * @return
     */
    Result selectById(Serializable id);

    /**
     * 根据多个主键查询
     * @param ids
     * @return
     */
    Result selectBatchIds(Collection<? extends Serializable> ids);

    /**
     * 根据自定义条件查询
     * @param params
     * @return
     */
    Result selectByMap(Map<String, Object> params);


}
