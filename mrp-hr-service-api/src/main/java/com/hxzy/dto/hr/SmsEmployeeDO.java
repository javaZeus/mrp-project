package com.hxzy.dto.hr;

import com.hxzy.entity.hr.Employee;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "员工短信注册对象")
public class SmsEmployeeDO extends Employee {

    //短信码
    @ApiModelProperty(value = "短信码")
    @NotBlank(message = "短信不能为空")
    private String code;


}
