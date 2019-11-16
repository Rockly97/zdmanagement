package com.zdxt.controller.reception;

import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.GermanyNews;
import com.zdxt.service.GermanyNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Rockly on 2019/11/16 11:12.
 */
@RestController
@CrossOrigin
public class GermanyApiController {
    @Autowired
    private GermanyNewsService germanyNewsService;


    @RequestMapping("/germanylist")
    public Result germanyList(){
        List<GermanyNews> germanyNewsList = germanyNewsService.getGermanyNewsList();
        if(germanyNewsList.size() > 0){
            return ResultGenerator.getSuccessResult(germanyNewsList);
        }
        return  ResultGenerator.getFailResult("数据查询错误！");
    }

}
