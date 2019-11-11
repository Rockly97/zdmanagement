package com.zdxt.controller.admin;

import com.zdxt.common.UploadController;
import com.zdxt.common.util.*;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;
import com.zdxt.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class ResourceController {
    @Autowired
    IdWorker idWorker;
    @Autowired
    ResourcesService resourcesService;

    @GetMapping({"/resources"})
    public String banner(HttpServletRequest request, HttpSession session){
        request.setAttribute("path","resources");
        return "resources";
    }
    @GetMapping("/resources/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        System.out.println(params.get("page"));
        System.out.println(params.get("limit"));
        System.out.println(params.get("keyword"));
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult resourcePage = resourcesService.getResourcePage(pageUtil);
        if (resourcePage==null||resourcePage.getList().size()==0){
            return ResultGenerator.getFailResult("查询失败！");
        }
        return ResultGenerator.getSuccessResult(resourcePage);
    }
    @GetMapping({"/resources/edit"})
    public String bannerEdit(HttpServletRequest request, RedirectAttributes attributes){

        request.setAttribute("path","resources");
        return "resourcesedit";
    }
}
