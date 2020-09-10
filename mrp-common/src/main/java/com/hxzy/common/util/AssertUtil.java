package com.hxzy.common.util;

import com.hxzy.common.exception.BaseServiceNullException;
import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import lombok.extern.log4j.Log4j2;

/**
 * 断言判断
 */
@Log4j2
public class AssertUtil {

    /**
     * 断言是否为空
     * @param obj
     * @return
     */
    public static Result assertIsNull(Object obj){
       if(obj==null){
          log.error("对象为空");
          throw new BaseServiceNullException();
       }
       return Result.createResult(ResultCode.OK);
    }

}
