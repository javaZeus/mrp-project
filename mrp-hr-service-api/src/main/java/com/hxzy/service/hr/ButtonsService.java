package com.hxzy.service.hr;

import com.hxzy.common.service.BaseService;
import com.hxzy.common.vo.PageSearch;
import com.hxzy.common.vo.Result;
import com.hxzy.entity.hr.Buttons;

/**
 * 按钮表业务逻辑
 */
public interface ButtonsService extends BaseService<Buttons> {
    Result search(PageSearch pageSearch);
}
