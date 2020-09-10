package com.hxzy.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * SOLR分页查询类
 */
@ApiModel(description = "Solr分页查询类")
@Getter
@Setter
public class SolrPageSearch extends PageSearch {

    /**
     * 分页offset
     * @return
     */
    @ApiModelProperty(value = "solr分页start计算",example = "0")
    public int getStart(){
        return  (super.getPage()-1) * super.getSize();
    }

    /**
     * 分页取几笔
     * @return
     */
    @ApiModelProperty(value = "solr分页取几笔",example = "10")
    public int getRows(){
        return super.getSize();
    }
}
