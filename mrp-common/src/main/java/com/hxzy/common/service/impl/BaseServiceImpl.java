package com.hxzy.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxzy.common.service.BaseService;
import com.hxzy.common.util.AssertUtil;
import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 公共的业务逻辑实现
 * @param <T>
 */
@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseMapper baseMapper;

    public void setBaseMapper(BaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public Result insert(T entity) {
        AssertUtil.assertIsNull(baseMapper);
        int count=this.baseMapper.insert(entity);
        return Result.createResult( count>0? ResultCode.SAVE_DATABASE_OK: ResultCode.SAVE_DATABASE_ERROR);
    }

    @Override
    public Result deleteById(Serializable id) {
        AssertUtil.assertIsNull(baseMapper);
        int count=this.baseMapper.deleteById(id);
        return Result.createResult( count>0? ResultCode.SAVE_DATABASE_OK: ResultCode.SAVE_DATABASE_ERROR);
    }

    @Override
    public Result deleteByMap(Map<String, Object> params) {
        AssertUtil.assertIsNull(baseMapper);
        int count=this.baseMapper.deleteByMap(params);
        return Result.createResult( count>0? ResultCode.SAVE_DATABASE_OK: ResultCode.SAVE_DATABASE_ERROR);
    }

    @Override
    public Result deleteBatchIds(Collection<? extends Serializable> ids) {
        AssertUtil.assertIsNull(baseMapper);
        int count=this.baseMapper.deleteBatchIds(ids);
        return Result.createResult( count>0? ResultCode.SAVE_DATABASE_OK: ResultCode.SAVE_DATABASE_ERROR);
    }

    @Override
    public Result updateById(T entity) {
        AssertUtil.assertIsNull(baseMapper);
        int count=this.baseMapper.updateById(entity);
        return Result.createResult( count>0? ResultCode.SAVE_DATABASE_OK: ResultCode.SAVE_DATABASE_ERROR);
    }

    @Override
    public Result selectById(Serializable id) {
        AssertUtil.assertIsNull(baseMapper);
        T obj=(T)this.baseMapper.selectById(id);
         Result result=null;
         if(obj==null){
             result=Result.createResult(ResultCode.NOT_FOUND_DATA_BY_ID);
         }else{
             result=Result.createResult_Data(ResultCode.OK, obj);
         }
         return result;
    }

    @Override
    public Result selectBatchIds(Collection<? extends Serializable> ids) {
        AssertUtil.assertIsNull(baseMapper);
        List arr=this.baseMapper.selectBatchIds(ids);
        return Result.createResult_Data(ResultCode.OK, arr);
    }

    @Override
    public Result selectByMap(Map<String, Object> params) {
        AssertUtil.assertIsNull(baseMapper);
        List arr=this.baseMapper.selectByMap(params);
        return Result.createResult_Data(ResultCode.OK, arr);
    }
}
