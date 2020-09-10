package com.hxzy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import com.hxzy.dto.EmployeeSearchDO;
import com.hxzy.vo.EmployeeVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 员工相关的搜索
 */
@Service
public class EmployeeSolrService {

    @Autowired
    private SolrClient  solrClient;


    public Result search(EmployeeSearchDO searchDO) throws IOException, SolrServerException {
         IPage<EmployeeVO>  page=searchNameBySolor(searchDO);

        return Result.createResult_Data(ResultCode.OK, page);
    }


    private IPage<EmployeeVO> searchNameBySolor(EmployeeSearchDO searchDO) throws IOException, SolrServerException {
        IPage<EmployeeVO>  page=new Page<EmployeeVO>( searchDO.getPage(),searchDO.getSize());
        //total  总记录数
        //当前页分页数据 records
        SolrQuery  solrQuery=new SolrQuery();
        //设定查询条件
        if(StringUtils.isNotBlank( searchDO.getName())){
            solrQuery.setQuery("emp_name:"+searchDO.getName());

        }else{
            solrQuery.setQuery("*:*");
        }
        //分页
        solrQuery.setStart(searchDO.getStart());
        solrQuery.setRows(searchDO.getRows());

        //查询
         QueryResponse queryResponse= this.solrClient.query(solrQuery);
         //得到结果集合
        SolrDocumentList documentList=queryResponse.getResults();
        //得到总记录数  numFound
        page.setTotal(documentList.getNumFound());


        //使用注解    @SolrDocument(solrCoreName="hxzy")  +字段  @Field(value = "emp_sex")  注意 主键必须是String类型
        List<EmployeeVO> resultList=queryResponse.getBeans(EmployeeVO.class);

        //返回给前端数据 手工取
//        List<EmployeeVO>  resultList=new ArrayList<EmployeeVO>();
//
//        //取数据
//        for(SolrDocument  document : documentList){
//          EmployeeVO  emp=new EmployeeVO();
//
//          // 反射
//          emp.setId( Integer.parseInt(document.getFieldValue("id").toString() ));
//          emp.setName(document.getFieldValue("emp_name").toString());
//          emp.setSex(Integer.parseInt(document.getFieldValue("emp_sex").toString()));
//          emp.setSexName(document.getFieldValue("emp_sexName").toString());
//          emp.setMobile(document.getFieldValue("emp_mobile").toString());
//          emp.setAvatar(document.getFieldValue("emp_avatar").toString());
//          emp.setDepartmentId(Integer.parseInt(document.getFieldValue("emp_departmentId").toString()));
//          emp.setDepartmentName(document.getFieldValue("emp_departmentName").toString());
//          emp.setPositionId(Integer.parseInt(document.getFieldValue("emp_positionId").toString()));
//          emp.setPositionName(document.getFieldValue("emp_positionName").toString());
//          emp.setWechatid(document.getFieldValue("emp_wechatid").toString());
//          emp.setStatus(Integer.parseInt(document.getFieldValue("emp_status").toString()));
//          resultList.add(emp);
//        }
        //把集合放到 Ipage分页对象中
        page.setRecords(resultList);

        return page;
    }

}
