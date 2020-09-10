package com.hxzy.dto;

import com.hxzy.common.vo.SolrPageSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 员工搜索
 */
@Getter
@Setter
@ApiModel(description = "员工搜索类")
public class EmployeeSearchDO extends SolrPageSearch{

    @ApiModelProperty(value = "按名称模糊搜索")
    private String name;


}
