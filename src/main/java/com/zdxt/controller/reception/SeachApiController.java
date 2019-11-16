package com.zdxt.controller.reception;

import com.zdxt.common.util.*;
import com.zdxt.service.ResourcesService;
import com.zdxt.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping
public class SeachApiController{
    @Autowired
    SearchService searchService;
    @RequestMapping(value = "/search/list",method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
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

