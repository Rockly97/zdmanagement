package com.zdxt.controller.admin;

import com.zdxt.common.UploadController;
import com.zdxt.common.util.*;
import com.zdxt.model.CooperativeResources;
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
import java.util.Date;
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
        File fileDirectory = new File(UploadController.TEMP);
        //创建文件
        if (!fileDirectory.exists()) {
            return "resources";
        }else {
            FileUtils.getDelete(fileDirectory);
            //最后删除目录文件夹
            fileDirectory.delete();
            return "resources";

        }
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
//  "id": id, "title": title, "description": description,
//            "kind": kind, "url": lujin, "coverImage": coverImage,
//            "flag": flag
    @PostMapping("/resources/save")
    @ResponseBody
    public Result save(@RequestParam("id") String id,
                       @RequestParam("title") String title,
                       @RequestParam("description") String description,
                       @RequestParam("kind") String kind,
                       @RequestParam("url") String url,
                       @RequestParam("img") String img,
                       @RequestParam("flag") Integer flag) {

        if (StringUtils.isEmpty(title)) {
            return ResultGenerator.getFailResult("请输入合作资源标题");
        }
        if (title.trim().length() > 100) {
            return ResultGenerator.getFailResult("描述过长");
        }
        if (description.trim().length() > 450) {
            return ResultGenerator.getFailResult("描述过长");
        }
        if (url.trim().length() > 100) {
            return ResultGenerator.getFailResult("网址过长");
        }
        if (StringUtils.isEmpty(img)) {
            return ResultGenerator.getFailResult("合作资源首图不能为空");
        }

        CooperativeResources resources=new CooperativeResources();
        resources.setId(idWorker.nextId()+"");
        resources.setDescription(description);
        resources.setTitle(title);
        resources.setCreateTime(new Date());
        resources.setFlag(flag);
        resources.setUrl(url);
        resources.setKind(kind);
        resources.setLogo(img);
        resources.setUpdateTime(new Date());

//
        String saveBlogResult = resourcesService.save(resources);
//        不管成功失败，都删除temp文件
        File fileDirectory = new File(UploadController.TEMP);
        if (!fileDirectory.exists()) {
        }else {
            FileUtils.getDelete(fileDirectory);
            //最后删除目录文件夹
            fileDirectory.delete();

        }
        if ("success".equals(saveBlogResult)) {

            return ResultGenerator.getSuccessResult("添加成功");
        } else {
            return ResultGenerator.getFailResult(saveBlogResult);
        }
    }
    @GetMapping("/resources/edit/{id}")
    public String edit(HttpServletRequest request, @PathVariable("id") String id) {
        request.setAttribute("path", "resources");
        CooperativeResources cooperativeResourcesByid = resourcesService.findCooperativeResourcesByid(id);
        if (cooperativeResourcesByid == null) {
            return "error/error_400";
        }
        request.setAttribute("resources", cooperativeResourcesByid);

        return "resourcesedit";
    }
    @PostMapping("/resources/delete")
    @ResponseBody
    public Result delete(@RequestBody String[] ids) {
        System.out.println(ids.toString());

        if (resourcesService.deleteBatch(ids)) {

            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult("删除失败");
        }
    }

    @PostMapping("/resources/update")
    @ResponseBody
    public Result update(@RequestParam("id") String id,
                         @RequestParam("title") String title,
                         @RequestParam("description") String description,
                         @RequestParam("kind") String kind,
                         @RequestParam("url") String url,
                         @RequestParam("img") String img,
                         @RequestParam("flag") Integer flag) {

        if (StringUtils.isEmpty(title)) {
            return ResultGenerator.getFailResult("请输入合作资源标题");
        }
        if (title.trim().length() > 100) {
            return ResultGenerator.getFailResult("描述过长");
        }
        if (description.trim().length() > 450) {
            return ResultGenerator.getFailResult("描述过长");
        }
        if (url.trim().length() > 100) {
            return ResultGenerator.getFailResult("网址过长");
        }
        if (StringUtils.isEmpty(img)) {
            return ResultGenerator.getFailResult("合作资源首图不能为空");
        }

        CooperativeResources resources=new CooperativeResources();
        resources.setId(id);
        resources.setDescription(description);
        resources.setTitle(title);
        resources.setCreateTime(new Date());
        resources.setFlag(flag);
        resources.setUrl(url);
        resources.setKind(kind);
        resources.setUpdateTime(new Date());
        resources.setLogo(img);
        String updateBlogResult = resourcesService.updateCooperativeResources(resources);

        if ("success".equals(updateBlogResult)) {
            return ResultGenerator.getSuccessResult("修改成功");

        } else {
            return ResultGenerator.getFailResult(updateBlogResult);
        }
    }
}
