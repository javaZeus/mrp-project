package com.hxzy.entity.hr;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hxzy.common.bean.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 按钮表实体
 * @author 
 * 
 */
@Data
@ApiModel( value = "buttons", description ="按钮表实体" )
public class Buttons extends AbstractEntity{

    /**
     * 按钮名称
     */
    @ApiModelProperty(value = "按钮名称")
    private String name;

    /**
     * 按钮对应的javascript方法名称
     */
    @ApiModelProperty(value = "按钮对应的javascript方法名称")
    @TableField(value = "btnScript")
    private String btnScript;

    /**
     * 按钮图标字体库
     */
    @ApiModelProperty(value = "按钮图标字体库")
    @TableField(value = "btnIcon")
    private String btnIcon;


}