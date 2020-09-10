package com.hxzy.dto.hr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(description = "员工短信发送对象")
@Getter
@Setter
public class SmsDO {

    @ApiModelProperty(value = "手机号码",required = true)
    @NotBlank(message = "手机号码不能为空",groups = {SendSmsState.class, MobileCodeValid.class})
    @Pattern(regexp = "^1\\d{10}$",message = "电话号码格式必须是11位数字")
    private String mobile;


    @ApiModelProperty(value = "验证码,在短信ajax认证时候有效")
    @NotBlank(message = "验证码不能为空",groups = { MobileCodeValid.class})
    @Pattern(regexp = "^\\d{4}$",message = "验证码只能是4位数字")
    private String code;


    /**
     * 验证手机和验证码是否正确
     */
    public interface MobileCodeValid{}

    /**
     * 发送短信的标识
     */
    public interface SendSmsState{}
}
