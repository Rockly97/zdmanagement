package com.zdxt.controller.reception;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.IndexNews;
import com.zdxt.service.IndexNewsService;
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

/**
 * Created by Rockly on 2019/11/16 11:23.
 */
@CrossOrigin
@RequestMapping("/news")
@Api(tags = "NewsApi",description = "获取新闻接口和图片置顶新闻")
@RestController
public class NewsApiController {

    @Autowired
    private IndexNewsService indexNewsService;

    @PostMapping("/list")
    @ApiOperation(value = "获取新闻",notes = "获取新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",paramType = "query",value = "当前页码",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "limit",paramType = "query",value = "每页条数",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "keyword",paramType = "query",value = "查询条件",dataType = "String",required = false)
    })
    public Result<IndexNews> newsList(@ApiIgnore @RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult resourcePage = indexNewsService.getNewsList(pageUtil);
        if (resourcePage==null||resourcePage.getList().size()==0){
            return ResultGenerator.getFailResult("查询失败！");
        }
        return ResultGenerator.getSuccessResult(resourcePage);
    }


    @GetMapping("/topic")
    @ApiOperation(value = "获取置顶新闻")
    @ResponseBody
    public Result<IndexNews> newsTopic(){
         List<IndexNews> indexNewsList = indexNewsService.findNewsTopic();
        if(indexNewsList.size() > 0){
            return ResultGenerator.getSuccessResult(indexNewsList);
        }
        return ResultGenerator.getFailResult("查询失败");
    }


    @PostMapping("/newsByid")
    @ApiOperation(value = "根据Id获取新闻")
    @ApiImplicitParam(name = "id",value = "获取置顶的新闻",paramType = "query")
    public Result<IndexNews> newsById(@RequestParam("id") String id){
        IndexNews newsItem = indexNewsService.getNewsItem(id);
        Result result = null;
        if(newsItem!=null){
            result = ResultGenerator.getSuccessResult(newsItem);
        } else {
            result = ResultGenerator.getFailResult("查找失败！");
        }
        return result;
    }



}


