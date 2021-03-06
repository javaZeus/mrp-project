package com.hxzy.entity.hr;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxzy.common.bean.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author 
 * 
 */
@ApiModel(description = "内部员工实体")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employee extends AbstractEntity {


    /**
     * 员工手机号码
     */
    @ApiModelProperty(name = "mobile",value = "手机号码")
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1\\d{10}$",message = "电话号码格式必须是11位数字")
    @Field(value = "emp_mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email",value = "邮箱")
    @NotBlank(message = "邮件不能为空")
    @Email(message = "邮件格式不正确")
    @Field(value = "emp_email")
    private String email;

    /**
     * 账号密码 (不要生成json串给前端)
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 员工姓名
     */
    @ApiModelProperty(name = "name",value = "员工姓名")
    @NotBlank(message = "员工姓名不能为空")
    @Field(value = "emp_name")
    private String name;

    /**
     * 员工性别 dictinfo
     */
    @ApiModelProperty(name = "sex",value = "员工性别(3代表男，4代表女)")
    @Field(value = "emp_sex")
    private Integer sex;


    /**
     * 员工生日
     */
    @ApiModelProperty(name = "birthday",value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Field(value = "emp_birthday")
    private Date birthday;

    /**
     * 所属部门id
     */
    @ApiModelProperty(name = "departmentId",value = "所属部门id")
    @TableField(value = "departmentId")
    @Field(value = "emp_departmentId")
    private Integer departmentId;

    /**
     * 职位编号  dictinfo
     */
    @ApiModelProperty(name = "positionId",value = "职位编号")
    @TableField(value = "positionId")
    @Field(value = "emp_positionId")
    private Integer positionId;



    /**
     * 员工头像
     */
    @ApiModelProperty(name = "avatar",value = "员工头像")
    @Field(value = "emp_avatar")
    private String avatar;

    /**
     * 微信号
     */
    @ApiModelProperty(name = "wechatid",value = "微信号")
    @Field(value = "emp_wechatid")
    private String wechatid;


    @ApiModelProperty(name = "status",value = "账户状态(0未激活，1代表正确，2代表锁定)")
    @Field(value = "emp_status")
    private Integer status;

    private static final long serialVersionUID = 1L;

}