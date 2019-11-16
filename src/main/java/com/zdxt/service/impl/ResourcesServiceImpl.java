package com.zdxt.service.impl;

import com.zdxt.common.UploadController;
import com.zdxt.common.util.FileUtils;
import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.mapper.CooperativeResourcesMapper;
import com.zdxt.model.CooperativeResources;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    CooperativeResourcesMapper cooperativeResourcesMapper;
    @Override
    public PageResult getResourcePage(PageQueryUtil pageUtil) {
        List<CooperativeResources> blogList = cooperativeResourcesMapper.findResourcesList(pageUtil);
        if(blogList.size()==0||blogList==null){
            return null;
        }

//        查询总数
        int total = cooperativeResourcesMapper.getTotalResources(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalResource() {
      return   cooperativeResourcesMapper.getTotalResources(null);
    }

    @Override
    public PageResult getResourcePageApi(PageQueryUtil pageUtil) {
        List<CooperativeResources> blogList = cooperativeResourcesMapper.findResourcesListApi(pageUtil);
        if(blogList.size()==0||blogList==null){
            return null;
        }

//        查询总数
        int total = cooperativeResourcesMapper.getTotalResourcesApi(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalResourceApi() {
        return cooperativeResourcesMapper.getTotalResourcesApi(null);
    }

    @Override
    public CooperativeResources findCooperativeResourcesByid(String id) {
        CooperativeResources cooperativeResourcesById = cooperativeResourcesMapper.findCooperativeResourcesById(id);
        if(!cooperativeResourcesById.getLogo().equals("")&&!cooperativeResourcesById.getLogo().isEmpty()){
            return cooperativeResourcesById;
        }else {
            return null;
        }
    }

    @Override
    public String save(CooperativeResources cooperativeResources) {
//        获得图片名称
        String img = cooperativeResources.getLogo();
        String banner1 = img.substring(img.lastIndexOf("/")+1);
        System.out.println(banner1);
//        找到要复制的目标图片
        File file=new File(UploadController.TEMP+banner1);
//        复制的位置
        File dest=new File(UploadController.HEZUO+banner1);
        try {
            org.apache.commons.io.FileUtils.copyFile(file,dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String  imageurl="hezuo/"+banner1;
        cooperativeResources.setLogo(imageurl);
        System.out.println(cooperativeResources);
        boolean insert = cooperativeResourcesMapper.insert(cooperativeResources);
        if(insert){
            return "success";
        }else {
            return  "添加失败";
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
            CooperativeResources cooperativeResourcesByid = cooperativeResourcesMapper.findCooperativeResourcesById(id);
            File file=new File(UploadController.XIANGDUI+cooperativeResourcesByid.getLogo());
            if (!file.exists()) {
                System.out.println("删除文件失败:"  + "不存在！");
            } else {
                if (file.isFile()){
                    file.delete();
                }
            }
        }
        return cooperativeResourcesMapper.deleteResources(ids) > 0;
    }

    @Override
    public String updateCooperativeResources(CooperativeResources cooperativeResources) {
        //删除之前banner文件夹的图片(根据id查询图片路径)
        CooperativeResources banner = cooperativeResourcesMapper.findCooperativeResourcesById(cooperativeResources.getId());
        String oldimg = banner.getLogo();
//旧图片
        String oldimg_jiequ = oldimg.substring(oldimg.lastIndexOf("/") + 1);
//新图片
        String img = cooperativeResources.getLogo();
        String newimg_jiequ = img.substring(img.lastIndexOf("/") + 1);
        System.out.println("12312123" + oldimg_jiequ);
        System.out.println("21212" + newimg_jiequ);

        if (!oldimg.equals("") && !oldimg.isEmpty()) {
            if (!oldimg_jiequ.equals(newimg_jiequ)) {
//            根据地址删除原来图片
                File file = new File(UploadController.XIANGDUI + banner.getLogo());
                if (!file.exists()) {
                    System.out.println("删除文件失败:" + "不存在！");

                } else {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
//               不同就代表图片在temp里面，所以需要复制
                if (!img.equals("")) {
                    //        获得图片名称
                    String banner1 = img.substring(img.lastIndexOf("/") + 1);
                    System.out.println(banner1);
//        找到要复制的目标图片
                    File filere = new File(UploadController.TEMP + banner1);
//        复制的位置
                    File dest = new File(UploadController.HEZUO + banner1);
                    try {
                        org.apache.commons.io.FileUtils.copyFile(filere, dest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String imageurl = "hezuo/" + banner1;
                    cooperativeResources.setLogo(imageurl);
                }

            }else {
                cooperativeResources.setLogo(null);
            }
        }

//        不管成功还是失败，都先删除temp文件夹
        File fileDirectory = new File(UploadController.TEMP);
        //创建文件
        if (!fileDirectory.exists()) {
        } else {
            FileUtils.getDelete(fileDirectory);
            //最后删除目录文件夹
            fileDirectory.delete();

        }
        boolean b = cooperativeResourcesMapper.updateResources(cooperativeResources);

        if (b) {
            return "success";
        } else {
            return "修改失败";
        }
    }
}
