package com.hxzy.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回给vue前端对象规范
 */
@ApiModel(description = "返回给前端的结果")
public class Result {

    @ApiModelProperty(value = "返回的状态码",example = "0")
    private int code;
    @ApiModelProperty(value = "返回状态消息")
    private String message;

    @ApiModelProperty(value = "返回的数据")
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    private Result(){}


    public static Result createResult_Data(ResultCode resultCode,Object data){
        Result result=new Result();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        result.setData(data);
        return result;
    }

    public static Result createResult(ResultCode resultCode){
        Result result=new Result();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }

    public static Result createResult(ResultCode resultCode,String message){
        Result result=new Result();
        result.setCode(resultCode.getCode());
        result.setMessage(message);
        return result;
    }

}
