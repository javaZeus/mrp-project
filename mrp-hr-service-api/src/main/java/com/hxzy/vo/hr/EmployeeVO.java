package com.hxzy.vo.hr;

import com.hxzy.entity.hr.Employee;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "员工表参数")
@SolrDocument(solrCoreName="hxzy")
public class EmployeeVO  extends Employee{



    @ApiModelProperty(name = "sexName",value = "员工性别")
    @Field(value = "emp_sexName")
    private String sexName;

    @ApiModelProperty(name = "departmentName",value = "所属部门名称")
    @Field(value = "emp_departmentName")
    private String departmentName;

    @ApiModelProperty(name = "positionName",value = "职位名称")
    @Field(value = "emp_positionName")
    private String positionName;

}
