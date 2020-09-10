package com.hxzy.dto.hr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(value = "员工登录模型")
@Getter
@Setter
public class LoginDO {

    @ApiModelProperty(value = "登录名(电话或邮件)")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "(^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$)|(^1\\d{10}$)",message = "你不是有效的邮箱或手机号")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
