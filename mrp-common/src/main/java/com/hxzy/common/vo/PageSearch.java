package com.hxzy.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "分页查询参数")
@Getter
@Setter
public class PageSearch {

    @ApiModelProperty(value = "当前是第几页，默认为1",example = "1",required = true)
    private Integer page=1;

    @ApiModelProperty(value = "每页几笔数据,默认为5",example = "5",required = true)
    private Integer size=5;
}
