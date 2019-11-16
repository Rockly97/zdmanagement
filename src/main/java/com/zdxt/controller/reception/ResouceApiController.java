package com.zdxt.controller.reception;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;
import com.zdxt.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping
public class ResouceApiController {
    @Autowired
    ResourcesService resourcesService;
    @RequestMapping(value = "/resources/list",method = RequestMethod.POST)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
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