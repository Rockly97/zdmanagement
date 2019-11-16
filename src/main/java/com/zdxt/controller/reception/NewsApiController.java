package com.zdxt.controller.reception;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.IndexNews;
import com.zdxt.service.IndexNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Rockly on 2019/11/16 11:23.
 */
@CrossOrigin
@RequestMapping("/news")
@RestController
public class NewsApiController {

    @Autowired
    private IndexNewsService indexNewsService;

    @RequestMapping("/list")
    @ResponseBody
    public Result newsList(@RequestParam Map<String, Object> params){
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

    @RequestMapping("/topic")
    @ResponseBody
    public Result newsTopic(){
         List<IndexNews> indexNewsList = indexNewsService.findNewsTopic();
        if(indexNewsList.size() > 0){
            return ResultGenerator.getSuccessResult(indexNewsList);
        }
        return ResultGenerator.getFailResult("查询失败");
    }

}


