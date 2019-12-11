package com.zdxt.controller.admin;

import com.zdxt.common.UploadController;
import com.zdxt.common.util.*;
import com.zdxt.model.ActivityProgram;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.ActivityService;
import com.zdxt.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class ActivityController {
    @Autowired
    IdWorker idWorker;
    @Autowired
    ActivityService activityService;
    @GetMapping({"/activity"})
    public String banner(HttpServletRequest request, HttpSession session){
        request.setAttribute("path","activity");
            return "activity";
    }
    @GetMapping("/activity/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        System.out.println(params.get("page"));
        System.out.println(params.get("limit"));
        System.out.println(params.get("keyword"));
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult resourcePage = activityService.getActivityPage(pageUtil);
        if (resourcePage==null||resourcePage.getList().size()==0){
            return ResultGenerator.getFailResult("查询失败！");
        }
        return ResultGenerator.getSuccessResult(resourcePage);
    }
    @GetMapping({"/activity/edit"})
    public String bannerEdit(HttpServletRequest request){

        request.setAttribute("path","activity");
        return "acitvityedit";
    }

    @GetMapping("/activity/edit/{id}")
    public String edit(HttpServletRequest request, @PathVariable("id") String id) {
        request.setAttribute("path", "activity");
        ActivityProgram cooperativeActivityProgramByid = activityService.findCooperativeActivityProgramByid(id);
        if (cooperativeActivityProgramByid == null) {
            return "error/error_400";
        }
        request.setAttribute("activity", cooperativeActivityProgramByid);

        return "acitvityedit";
    }
//   "id": id, "title": title, "content": content,
//            "kind":kind , "addr": addr, "remark": remark,
//            "flag": flag
    @PostMapping("/activity/save")
    @ResponseBody
    public Result save(@RequestParam("id") String id,
                       @RequestParam("title") String title,
                       @RequestParam("content") String content,
                       @RequestParam("kind") String kind,
                       @RequestParam("addr") String addr,
                       @RequestParam("remark") String remark,
                       @RequestParam("flag") Integer flag

    ) {

        if (StringUtils.isEmpty(title)) {
            return ResultGenerator.getFailResult("请输入活动标题");
        }
        if (content.trim().length() > 450) {
            return ResultGenerator.getFailResult("内容过长");
        }
        if (remark.trim().length() > 450) {
            return ResultGenerator.getFailResult("备注过长");
        }
        if (title.trim().length() > 150) {
            return ResultGenerator.getFailResult("标题过长");
        }
        if (addr.trim().length() > 150) {
            return ResultGenerator.getFailResult("地址过长");
        }
            ActivityProgram activityProgram=new ActivityProgram();
        activityProgram.setId(idWorker.nextId()+"");
        activityProgram.setAddr(addr);
        activityProgram.setContent(content);
        activityProgram.setCreateTime(new Date());
        activityProgram.setUpdateTime(new Date());
        activityProgram.setFlag(flag);
        activityProgram.setKind(kind);
        activityProgram.setRemark(remark);
        activityProgram.setTitle(title);
        String save = activityService.save(activityProgram);
        if ("success".equals(save)) {
            return ResultGenerator.getSuccessResult("添加成功");
        } else {
            return ResultGenerator.getFailResult(save);
        }
    }
    @PostMapping("/activity/update")
    @ResponseBody
    public Result update(@RequestParam("id") String id,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam("kind") String kind,
                         @RequestParam("addr") String addr,
                         @RequestParam("remark") String remark,
                         @RequestParam("flag") Integer flag
    ) {
        if (StringUtils.isEmpty(id)) {
            return ResultGenerator.getFailResult("参数错误");
        }
        if (StringUtils.isEmpty(title)) {
            return ResultGenerator.getFailResult("请输入活动标题");
        }
        if (content.trim().length() > 30) {
            return ResultGenerator.getFailResult("时间范围不正确");
        }
        if (remark.trim().length() > 450) {
            return ResultGenerator.getFailResult("备注过长");
        }
        if (title.trim().length() > 150) {
            return ResultGenerator.getFailResult("标题过长");
        }
        if (addr.trim().length() > 150) {
            return ResultGenerator.getFailResult("地址过长");
        }
        ActivityProgram activityProgram=new ActivityProgram();
        activityProgram.setId(id);
        activityProgram.setAddr(addr);
        activityProgram.setContent(content);
        activityProgram.setCreateTime(new Date());
        activityProgram.setUpdateTime(new Date());
        activityProgram.setFlag(flag);
        activityProgram.setKind(kind);
        activityProgram.setRemark(remark);
        activityProgram.setTitle(title);
        String s = activityService.updateActivityProgram(activityProgram);
        if ("success".equals(s)) {
            return ResultGenerator.getSuccessResult("修改成功");

        } else {
            return ResultGenerator.getFailResult(s);
        }
    }

    @PostMapping("/activity/delete")
    @ResponseBody
    public Result delete(@RequestBody String[] ids) {
        System.out.println(ids.toString());

        if (activityService.deleteBatch(ids)) {
            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult("删除失败");
        }
    }
}

