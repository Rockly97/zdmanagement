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
<<<<<<< HEAD
=======
        newbanner.setDescrip(descrip);
        //删除之前banner文件夹的图片(根据id查询图片路径)
        IndexBanner banner = indexBannerService.findBannerByid(id);
       String oldimg= banner.getImg();
        //旧图片
        String oldimg_jiequ = oldimg.substring(oldimg.lastIndexOf("/")+1);
        //新图片
        String newimg_jiequ = img.substring(img.lastIndexOf("/")+1);
        System.out.println("12312123"+oldimg_jiequ);
        System.out.println("21212"+newimg_jiequ);

        if(!oldimg.equals("")&&!oldimg.isEmpty()){
            if(!oldimg_jiequ.equals(newimg_jiequ)){
//            根据地址删除原来图片
                File file=new File(UploadController.XIANGDUI+banner.getImg());
                if (!file.exists()) {
                    System.out.println("删除文件失败:"  + "不存在！");

                } else {
                    if (file.isFile()){
                        file.delete();
                    }
                }
//               不同就代表图片在temp里面，所以需要复制
                if(!img.equals("")){
                    //        获得图片名称
                    String banner1 = img.substring(img.lastIndexOf("/")+1);
                    System.out.println(banner1);
//        找到要复制的目标图片
                    File filere=new File(UploadController.TEMP+banner1);
//        复制的位置
                    File dest=new File(UploadController.BANNER+banner1);
                    try {
                        org.apache.commons.io.FileUtils.copyFile(filere,dest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String  imageurl="banner/"+banner1;
                    newbanner.setImg(imageurl);
                }

            }
        }

>>>>>>> 5455ba94aa0e3872ef0cb80d7db33f38910f3204

        if(!id.equals("")){
            newbanner.setId(id);
        }
        newbanner.setImg(img);
        newbanner.setDescrip(descrip);

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
