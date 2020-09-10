package com.hxzy.service.hr.impl;

import com.hxzy.common.service.impl.BaseServiceImpl;
import com.hxzy.entity.hr.Dictinfo;
import com.hxzy.mapper.hr.DictinfoMapper;
import com.hxzy.service.hr.DictinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DictinfoServiceImpl extends BaseServiceImpl<Dictinfo> implements DictinfoService{


    private DictinfoMapper  dictinfoMapper;

    @Autowired
    public void setDictinfoMapper(DictinfoMapper dictinfoMapper) {
        this.dictinfoMapper = dictinfoMapper;
        super.setBaseMapper(dictinfoMapper);
    }
}
