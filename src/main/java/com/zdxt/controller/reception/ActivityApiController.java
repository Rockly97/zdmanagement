package com.zdxt.controller.reception;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.ActivityProgram;
import com.zdxt.service.ActivityService;
import com.zdxt.service.ResourcesService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@CrossOrigin
@RestController
@Api(tags = "ActivityApi", description = "获取新活动计划")
@RequestMapping
public class ActivityApiController {

    @Autowired
    ActivityService activityService;


    @ApiOperation(value = "获取活动计划")
    @PostMapping(value = "/activity/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",paramType = "query",value = "当前页码",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "limit",paramType = "query",value = "每页条数",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "keyword",paramType = "query",value = "查询条件",dataType = "String",required = false)
    })
    public Result<ActivityProgram> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        System.out.println(params.get("page"));
        System.out.println(params.get("limit"));
        System.out.println(params.get("keyword"));
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult resourcePage = activityService.getActivityPageApi(pageUtil);
        if (resourcePage == null || resourcePage.getList().size() == 0) {
            return ResultGenerator.getFailResult("查询失败！");
        }
        return ResultGenerator.getSuccessResult(resourcePage);
    }
}
