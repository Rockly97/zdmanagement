package com.zdxt.controller.admin;

import com.zdxt.common.util.*;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BannerController {
    @Autowired
    IdWorker idWorker;
    @Autowired
    IndexBannerService indexBannerService;

    @GetMapping({"/banner"})
    public String banner(HttpServletRequest request, HttpSession session){
        request.setAttribute("path","banner");
        return "banner";
    }
    @GetMapping("/banner/list")
    @ResponseBody
    public Result list(){
        List<IndexBanner> all = indexBannerService.findAll();
        System.out.println(all);
        if(all!=null){
            return ResultGenerator.getSuccessResult(all);
        }else {
          return ResultGenerator.getFailResult("查询数据失败");
        }

    }
    @GetMapping({"/banner/edit"})
    public String bannerEdit(HttpServletRequest request, HttpSession session){
        request.setAttribute("path","banner");
        return "banneredit";
    }
}
