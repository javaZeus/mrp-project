package com.hxzy.controller.hr;

import com.hxzy.common.vo.PageSearch;
import com.hxzy.common.vo.Result;
import com.hxzy.service.hr.ButtonsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/hr")
@Api(value="ButtonsController",description = "按钮表相关操作")
public class ButtonsController {

    @Autowired
    private ButtonsService buttonsService;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/buttons/{id}")
    public Result selectOne(@PathVariable(value = "id") int id){
          return this.buttonsService.selectById(id);
    }

    @ApiOperation(value = "按钮分页查询")
    @GetMapping(value = "/buttons/search")
    public Result search(PageSearch pageSearch){
         return this.buttonsService.search(pageSearch);
    }

}
