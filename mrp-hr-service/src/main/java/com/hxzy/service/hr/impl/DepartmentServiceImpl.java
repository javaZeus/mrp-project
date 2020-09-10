package com.hxzy.service.hr.impl;

import com.hxzy.common.service.impl.BaseServiceImpl;
import com.hxzy.entity.hr.Department;
import com.hxzy.mapper.hr.DepartmentMapper;
import com.hxzy.service.hr.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService{

    private DepartmentMapper departmentMapper;

    @Autowired
    public void setDepartmentMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
        super.setBaseMapper(departmentMapper);
    }
}
