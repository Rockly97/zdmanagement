package com.zdxt.controller.admin;

import com.zdxt.common.UploadController;
import com.zdxt.common.util.*;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;




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

@CrossOrigin
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
    @GetMapping({"/banner/deleteImg"})
    public String deleteImg(HttpServletRequest request){
        File fileDirectory = new File(UploadController.TEMP);
        //创建文件
            if (!fileDirectory.exists()) {
                return "banner";
            }else {
                FileUtils.getDelete(fileDirectory);
                //最后删除目录文件夹
                fileDirectory.delete();
                return "banner";

            }

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
    public String bannerEdit(HttpServletRequest request, RedirectAttributes attributes){
        List<IndexBanner> all = indexBannerService.findAll();
        System.out.println(all.size());
        if(all.size()>=5){
            request.setAttribute("path","banner");
            attributes.addFlashAttribute("msg", "亲！这边最多只能上传5张轮播图");

            return "redirect:/admin/banner";
        }
        attributes.addFlashAttribute("msg", "");
        request.setAttribute("path","banner");
        return "banneredit";
    }
    @GetMapping("/banner/edit/{id}")
    public String edit(HttpServletRequest request, @PathVariable("id") String id) {
        request.setAttribute("path", "banner");
        IndexBanner bannerByid = indexBannerService.findBannerByid(id);
        if (bannerByid == null) {
            return "error/error_400";
        }
        request.setAttribute("banner", bannerByid);

        return "banneredit";
    }

    @PostMapping("/banner/save")
    @ResponseBody
    public Result save(@RequestParam("descrip") String descrip,
                       @RequestParam("id") String id,
                       @RequestParam("img") String image
                     ) {
        List<IndexBanner> all = indexBannerService.findAll();
        System.out.println(all.size());
        if(all.size()>=5){

            return ResultGenerator.getFailResult("轮播图最大上传数量为5");
        }
        if (StringUtils.isEmpty(descrip)) {
            return ResultGenerator.getFailResult("请输入文章描述");
        }
        if (descrip.trim().length() > 450) {
            return ResultGenerator.getFailResult("描述过长");
        }
        if (StringUtils.isEmpty(image)) {
            return ResultGenerator.getFailResult("轮播图图不能为空");
        }
        IndexBanner banner=new IndexBanner();
        banner.setImg(image);
        banner.setId(idWorker.nextId()+"");
        banner.setDescrip(descrip);
//
        String saveBlogResult = indexBannerService.save(banner);
        //不管成功失败，都删除temp文件
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
    @PostMapping("/banner/update")
    @ResponseBody
    public Result update(@RequestParam("id") String id,
                         @RequestParam("descrip") String descrip,
                         @RequestParam("img") String img
                    ) {
        if (descrip.trim().length() > 450) {
            return ResultGenerator.getFailResult("描述过长");
        }
        IndexBanner newbanner = new IndexBanner();
        newbanner.setDescrip(descrip);
        if(!id.equals("")){
            newbanner.setId(id);
        }
        newbanner.setImg(img);
        String updateBlogResult = indexBannerService.updateBanner(newbanner);
//        不管成功还是失败，都先删除temp文件夹
        File fileDirectory = new File(UploadController.TEMP);
        //创建文件
        if (!fileDirectory.exists()) {
        }else {
            FileUtils.getDelete(fileDirectory);
            //最后删除目录文件夹
            fileDirectory.delete();

        }
        if ("success".equals(updateBlogResult)) {
            return ResultGenerator.getSuccessResult("修改成功");

        } else {
            return ResultGenerator.getFailResult(updateBlogResult);
        }
    }

    @PostMapping("/banner/delete")
    @ResponseBody
    public Result delete(@RequestBody String[] ids) {
        System.out.println(ids.toString());

        if (indexBannerService.deleteBatch(ids)) {


            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult("删除失败");
        }
    }
}
