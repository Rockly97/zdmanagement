package com.zdxt.controller.reception;

import com.zdxt.common.util.*;
import com.zdxt.service.ResourcesService;
import com.zdxt.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping
@Api(tags = "SeachApi", description = "获取全局搜索")
public class SeachApiController{

    @Autowired
    SearchService searchService;


    @GetMapping(value = "/search/list")
    @ResponseBody
    @ApiOperation(value = "获取新闻",notes = "获取新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search",paramType = "query",value = "查询条件",dataType = "String",required = false)
    })
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params) {
        String search = (String) params.get("search");
        String s1=search.trim();
        System.out.println(s1);

        Map<String, SearchResult> search1 = searchService.Search(s1);

        if (search1==null||search1.size()==0){
            return ResultGenerator.getFailResult("查询失败！");
        }
        return ResultGenerator.getSuccessResult(search1);
    }
}

