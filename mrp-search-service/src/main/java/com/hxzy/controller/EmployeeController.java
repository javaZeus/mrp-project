package com.hxzy.controller;


import com.hxzy.common.vo.Result;
import com.hxzy.dto.EmployeeSearchDO;
import com.hxzy.service.EmployeeSolrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/search")
@Api(value = "EmployeeController",description = "员工搜索类")
public class EmployeeController {

    @Autowired
    private EmployeeSolrService employeeSolrService;



    @ApiOperation(value = "按名称分页搜索")
    @PostMapping(value = "/employee")
    public Result employeeSearch(EmployeeSearchDO employeeSearchDO) throws IOException, SolrServerException {

        //return null;
        return this.employeeSolrService.search(employeeSearchDO);
    }



}
