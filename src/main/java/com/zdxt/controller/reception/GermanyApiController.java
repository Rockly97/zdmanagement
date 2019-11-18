package com.zdxt.controller.reception;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.GermanyNews;
import com.zdxt.service.GermanyNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.awt.image.RGBImageFilter;
import java.util.List;
import java.util.Map;

/**
 * Created by Rockly on 2019/11/16 11:12.
 */
@RestController
@CrossOrigin
@RequestMapping("/germany")
@Api(tags = "GermanyApi", description = "获取德国新闻")
public class GermanyApiController {
    @Autowired
    private GermanyNewsService germanyNewsService;


    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",paramType = "query",value = "当前页码",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "limit",paramType = "query",value = "每页条数",dataType = "Integer",required = true)
    })
    public Result<GermanyNews> germanyList(@ApiIgnore @RequestParam Map<String, Object> params){

        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult resourcePage = germanyNewsService.getGermanyNewsList(pageUtil);
        if (resourcePage==null||resourcePage.getList().size()==0){
            return ResultGenerator.getFailResult("查询失败！");
        }
        return ResultGenerator.getSuccessResult(resourcePage);
    }

    @PostMapping("/listByid")
    @ApiImplicitParam(name = "germanyId",paramType = "query",value = "德国新闻id")
    public Result<GermanyNews> getGermanyById( @RequestParam String germanyId){
        GermanyNews germanyNewsItem = germanyNewsService.getGermanyNewsItem(germanyId);
        Result result = null;
        if(germanyNewsItem!=null){
            result = ResultGenerator.getSuccessResult(germanyNewsItem);
        } else {
            result = ResultGenerator.getFailResult("查找失败！");
        }
        return result;
    }

}
