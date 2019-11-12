package com.zdxt.service.impl;

import com.zdxt.common.UploadController;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.mapper.IndexBannerMapper;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Service
@Transactional
public class IndexBannerServiceImpl implements IndexBannerService {
   @Autowired
    IndexBannerMapper indexBannerMapper;
    @Override
    public List<IndexBanner> findAll() {
        List<IndexBanner> allBanner = indexBannerMapper.findAllBanner();
        return allBanner;
    }

    @Override
    public String save(IndexBanner banner) {
        String img = banner.getImg();
        String banner1 = img.substring(img.lastIndexOf("/")+1);
        System.out.println(banner1);
//        找到要复制的目标图片
        File file=new File(UploadController.TEMP+banner1);
//        复制的位置
        File dest=new File(UploadController.BANNER+banner1);
        try {
            org.apache.commons.io.FileUtils.copyFile(file,dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String  imageurl="banner/"+banner1;
        banner.setImg(imageurl);
        boolean insert = indexBannerMapper.insert(banner);
        if(insert){
            return "success";
        }else {
            return  "添加失败";
        }

    }

    @Override
    public String updateBanner(IndexBanner banner) {
        //删除之前banner文件夹的图片(根据id查询图片路径)
        IndexBanner oldbanner = indexBannerMapper.findBannerById(banner.getId());
        String oldimg= oldbanner.getImg();
//旧图片
        String oldimg_jiequ = oldimg.substring(oldimg.lastIndexOf("/")+1);
//新图片
        String newimg=banner.getImg();
        String newimg_jiequ = newimg.substring(newimg.lastIndexOf("/")+1);
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
                if(!newimg.equals("")){
                    //        获得图片名称
                    String banner1 = newimg.substring(newimg.lastIndexOf("/")+1);
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
                    banner.setImg(imageurl);
                }

            }else {
                banner.setImg(null);
            }
        }

        boolean b = indexBannerMapper.updateBanner(banner);
        if(b){
            return "success";
        }else {
            return  "修改失败";
        }

    }

    @Override
    public IndexBanner findBannerByid(String id) {
        IndexBanner banner = indexBannerMapper.findBannerById(id);
        if(!banner.getImg().equals("")&&!banner.getImg().isEmpty()){
            return banner;
        }else {
            return null;
        }

    }

    @Override
    public Boolean deleteBatch(String[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除图片
        for (String id : ids ) {
            System.out.println(id);
            //删除之前banner文件夹的图片(根据id查询图片路径)
            IndexBanner banner = indexBannerMapper.findBannerById(id);
            File file=new File(UploadController.XIANGDUI+banner.getImg());
            if (!file.exists()) {
                System.out.println("删除文件失败:"  + "不存在！");
            } else {
                if (file.isFile()){
                    file.delete();
                }
            }
        }
        return indexBannerMapper.deleteBanner(ids) > 0;

    }
}
