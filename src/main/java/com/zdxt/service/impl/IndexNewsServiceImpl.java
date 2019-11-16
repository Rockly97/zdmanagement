package com.zdxt.service.impl;

import com.zdxt.common.UploadController;
import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.mapper.IndexNewsMapper;
import com.zdxt.model.IndexNews;
import com.zdxt.service.IndexNewsService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Rockly on 2019/11/10 23:06.
 */

@Service
public class IndexNewsServiceImpl implements IndexNewsService {

    @Autowired
    private IndexNewsMapper indexNewsMapper;

    @Override
    public String saveNews(IndexNews indexNews) throws IOException {

        String imgNews =  indexNews.getFirstPicture();
        imgNews = imgNews.substring(imgNews.lastIndexOf("/")+1);

        File soure = new File(UploadController.TEMP+imgNews);

        File dest = new File(UploadController.NEWS+imgNews);

        FileUtils.copyFile(soure,dest);
        String imageUrl = "news/"+imgNews;

        indexNews.setFirstPicture(imageUrl);
        int level = indexNews.getLevel();
        if(level == 1){
            Integer bigcount  = indexNewsMapper.fingCountBigImage(level);
            if(bigcount==1){
                return "bigmax";
            }
        }else if(level == 2){
            Integer smlacount = indexNewsMapper.fingCountBigImage(level);
            if(smlacount == 4){
                return "smlamax";
            }
        }
        boolean falg = indexNewsMapper.insertNews(indexNews);
        if(falg){
            com.zdxt.common.util.FileUtils.deletTempFile(UploadController.TEMP);
            return "success";
        }else {
            return "failure";
        }

    }

    @Override
    public String updataNews(IndexNews indexNews) throws IOException {
        IndexNews oldNews = indexNewsMapper.findNewsImg(indexNews.getId());
        String oldimg = oldNews.getFirstPicture();
        //旧图片
        String oldimg_jiequ = oldimg.substring(oldimg.lastIndexOf("/")+1);
        //新图片
        String newImage = indexNews.getFirstPicture();
        String newimg_jiequ = newImage.substring(newImage.lastIndexOf("/")+1);

        if(!oldimg.equals("")&&!oldimg.isEmpty()){
            if(!oldimg_jiequ.equals(newimg_jiequ)){
                //根据地址删除原来图片
                File file=new File(UploadController.XIANGDUI+oldNews.getFirstPicture());
                if (!file.exists()) {
                    throw new IOException("删除文件失败！");
                } else {
                    if (file.isFile()){
                        file.delete();
                    }
                }
                //不同就代表图片在temp里面，所以需要复制
                System.out.println(newimg_jiequ);
                //找到要复制的目标图片
                File filere=new File(UploadController.TEMP+newimg_jiequ);
                //复制的位置
                File dest=new File(UploadController.NEWS+newimg_jiequ);
                try {
                    FileUtils.copyFile(filere,dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String  imageurl="news/"+newimg_jiequ;
                indexNews.setFirstPicture(imageurl);
            }else {
                indexNews.setFirstPicture(null);
            }
        }
        if(!indexNews.getLevel().equals(oldNews.getLevel())){
            int level = indexNews.getLevel();
            if(level == 1){
                Integer bigcount  = indexNewsMapper.fingCountBigImage(level);
                if(bigcount==1){
                    return "bigmax";
                }
            }else if(level == 2){
                Integer smlacount = indexNewsMapper.fingCountBigImage(level);
                if(smlacount == 4){
                    return "smlamax";
                }
            }
        }

        boolean falg = indexNewsMapper.updataNews(indexNews);
        if(falg){
            com.zdxt.common.util.FileUtils.deletTempFile(UploadController.TEMP);
            return "success";
        }else {
            return "failure";
        }
    }

    @Override
    public PageResult getNewsPage(PageQueryUtil queryUtil) {
        List<IndexNews> indexNewsList =  indexNewsMapper.findNewsList(queryUtil);
        int total = indexNewsMapper.getTotalNews(queryUtil);
        PageResult pageResult = new PageResult(indexNewsList,total,queryUtil.getPage(),queryUtil.getLimit());
        return pageResult;
    }

    @Override
    public boolean deleteBatch(String[] ids) {
        List<String> imgaddlist = indexNewsMapper.getdelectImageAdd(ids);
            for (String imgadd: imgaddlist) {
                File file = new File(UploadController.XIANGDUI + imgadd);
                if (!file.exists()) {
                    System.out.println("删除文件失败:"  + "不存在！");
                } else {
                    if (file.isFile()){
                        file.delete();
                    }
                }
            }
            boolean flag = indexNewsMapper.deleteBatchNews(ids);
        return flag;
    }

    @Override
    public IndexNews getNewsItem(String newsId) {
        return indexNewsMapper.findByIdNews(newsId);
    }

    @Override
    public PageResult getNewsList(PageQueryUtil pageUtil) {
        List<IndexNews> indexNewsList =  indexNewsMapper.findNewsListItme(pageUtil);
        int total = indexNewsMapper.getTotalNewsItem(pageUtil);
        PageResult pageResult = new PageResult(indexNewsList,total,pageUtil.getPage(),pageUtil.getLimit());
        return pageResult;
    }

    @Override
    public List<IndexNews> findNewsTopic() {
        List<IndexNews> indexNewsList = indexNewsMapper.findNewsTopic();
        return indexNewsList;
    }


}
