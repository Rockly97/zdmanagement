package com.zdxt.controller.admin;

import com.zdxt.common.UploadController;
import com.zdxt.common.util.*;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    public String deleteImg(HttpServletRequest request, @RequestParam("img") String image){
        System.out.println(image);
        request.setAttribute("path","banner");
        if(image.isEmpty()||image.equals("")){
            return "banner";
        }else {
            String substring = image.substring(image.lastIndexOf("/")+1);
            if(substring.equals("img-upload.png")){
                return "banner";
            }
            File file=new File(UploadController.BANNER+substring);
            if (!file.exists()) {
                System.out.println("删除文件失败:"  + "不存在！");

            } else {
                if (file.isFile()){
                    file.delete();
                }
            }
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
    public String bannerEdit(HttpServletRequest request, HttpSession session){
        request.setAttribute("path","banner");
        return "banneredit";
    }
    @PostMapping("/banner/save")
    @ResponseBody
    public Result save(@RequestParam("descrip") String descrip,
                       @RequestParam("id") String id,
                       @RequestParam("img") String image
                     ) {
        if (StringUtils.isEmpty(descrip)) {
            return ResultGenerator.getFailResult("请输入文章描述");
        }
        if (descrip.trim().length() > 150) {
            return ResultGenerator.getFailResult("描述过长");
        }
        if (StringUtils.isEmpty(image)) {
            return ResultGenerator.getFailResult("轮播图图不能为空");
        }
        IndexBanner banner=new IndexBanner();
        String banner1 = image.substring(image.lastIndexOf("/")+1);
        System.out.println(banner1);
        File file=new File(UploadController.TEMP+banner1);
        File dest=new File(UploadController.BANNER+banner1);
        try {
            FileUtils.copyFile(file,dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        banner.setId(idWorker.nextId()+"");
        banner.setDescrip(descrip);
      String  imageurl="banner/"+banner1;
        banner.setImg(imageurl);
        String saveBlogResult = indexBannerService.save(banner);
        if ("success".equals(saveBlogResult)) {
            return ResultGenerator.getSuccessResult("添加成功");
        } else {
            return ResultGenerator.getFailResult(saveBlogResult);
        }
    }
//    @PostMapping("/banner/update")
//    @ResponseBody
//    public Result update(@RequestParam("id") String id,
//                         @RequestParam("descrip") String descrip,
//                         @RequestParam("img") String img
//                    ) {
//        IndexBanner baner = new IndexBanner();
//        if (descrip.trim().length() > 150) {
//            return ResultGenerator.getFailResult("描述过长");
//        }
//        if(!img.equals("")){
//            String banner1 = img.substring(img.lastIndexOf("banner"));
//            baner.setImg(banner1);
//        }
//        if(!id.equals("")){
//            baner.setId(id);
//        }
//
//
//        String updateBlogResult = blogService.updateBlog(blog);
//        if ("success".equals(updateBlogResult)) {
//            return ResultGenerator.genSuccessResult("修改成功");
//        } else {
//            return ResultGenerator.genFailResult(updateBlogResult);
//        }
//    }
}
