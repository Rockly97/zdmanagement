package com.zdxt.controller.reception;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.CooperativeResources;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;
import com.zdxt.service.ResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@Api(tags = "ResouceApi", description = "获取合作资源的资料")
@RequestMapping
public class ResouceApiController {
    @Autowired
    ResourcesService resourcesService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",paramType = "query",value = "当前页码",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "limit",paramType = "query",value = "每页条数",dataType = "Integer",required = true),
    })
    @ApiOperation(value = "获取合作资源")
    @PostMapping(value = "/resources/list")
    @ResponseBody
    public Result<CooperativeResources> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        System.out.println(params.get("page"));
        System.out.println(params.get("limit"));
        System.out.println(params.get("keyword"));
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult resourcePage = resourcesService.getResourcePageApi(pageUtil);
        if (resourcePage==null||resourcePage.getList().size()==0){
            return ResultGenerator.getFailResult("查询失败！");
        }
        return ResultGenerator.getSuccessResult(resourcePage);
    }
}
