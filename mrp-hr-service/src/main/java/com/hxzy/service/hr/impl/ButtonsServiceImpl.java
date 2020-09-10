package com.hxzy.service.hr.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxzy.common.service.impl.BaseServiceImpl;
import com.hxzy.common.vo.PageSearch;
import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import com.hxzy.entity.hr.Buttons;
import com.hxzy.mapper.hr.ButtonsMapper;
import com.hxzy.service.hr.ButtonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * buttons业务逻辑
 */
@Transactional
@Service
public class ButtonsServiceImpl  extends BaseServiceImpl<Buttons> implements ButtonsService{

    private ButtonsMapper buttonsMapper;

    @Autowired
    public void setButtonsMapper(ButtonsMapper buttonsMapper) {
        this.buttonsMapper = buttonsMapper;
        super.setBaseMapper(buttonsMapper);
    }


    @Override
    public Result search(PageSearch pageSearch) {

        IPage<Buttons> page=new Page<>(pageSearch.getPage(),pageSearch.getSize());
        //如果有条件
        //QueryWrapper<Buttons> wrapper=new QueryWrapper<>();

        IPage<Buttons> ipage=this.buttonsMapper.selectPage(page,null);

        return Result.createResult_Data(ResultCode.OK, ipage);
    }
}
