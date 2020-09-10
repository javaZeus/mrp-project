package com.hxzy.dto.hr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 邮件连接激活账户
 */
@ApiModel(description = "员工邮账邮件激活对象")
@Getter
@Setter
public class EmailVerifyDO {

    @ApiModelProperty(value = "redis键",required = true)
    @NotBlank(message = "key不允许为空")
    private String sn;

    @ApiModelProperty(value = "要激活的邮箱",required = true)
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String mn;


}
