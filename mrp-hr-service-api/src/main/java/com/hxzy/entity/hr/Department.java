package com.hxzy.entity.hr;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hxzy.common.bean.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 部门实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "部门实体")
public class Department  extends AbstractEntity{


    @ApiModelProperty(name = "name",value = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @ApiModelProperty(name = "pid",value = "父级部门编号,0为根节点")
    @TableField(value = "pid")
    private Integer pid;

}
