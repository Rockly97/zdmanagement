package com.zdxt.controller.admin;

import com.zdxt.common.baidu.ueditor.ActionEnter;
import com.zdxt.common.util.*;
import com.zdxt.model.IndexNews;
import com.zdxt.service.IndexNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by Rockly on 2019/11/8 15:36.
 */
@Controller
@RequestMapping("/admin")
public class NewsController {

    @Autowired
    IdWorker idWorker;

    @Autowired
    private IndexNewsService indexNewsService;

    @RequestMapping("/news/edit")
    public String edit(HttpServletRequest request){
        request.setAttribute("path","newsedit");
        return "newsedit";
    }




    @RequestMapping("/news/edit/{newsId}")
    public String editNewsId(HttpServletRequest request,@PathVariable("newsId") String newsId){
        IndexNews indexNews = indexNewsService.getNewsItem(newsId);
        request.setAttribute("indexNews",indexNews);
        return "newsedit";
    }

    /**
     * 保存博客
     * @param title
     * @param description
     * @param kind
     * @param author
     * @param content
     * @param coverImage
     * @param flag
     * @return
     */
    @PostMapping("/news/save")
    @ResponseBody
    public Result save(@RequestParam("title") String title,
                       @RequestParam("description") String description,
                       @RequestParam("kind") String kind,
                       @RequestParam("author") String author,
                       @RequestParam("content") String content,
                       @RequestParam("coverImage") String coverImage,
                       @RequestParam("flag")Integer  flag){

        if(StringUtils.isEmpty(title)){
            return ResultGenerator.getFailResult("请输入文章标题");
        }
        if(title.trim().length() > 150){
            return ResultGenerator.getFailResult("标题过长");
        }
        if(StringUtils.isEmpty(description)){
            return ResultGenerator.getFailResult("请输入文章描述");
        }
        if(description.trim().length() > 450){
            return ResultGenerator.getFailResult("文章描述过长");
        }
        if(StringUtils.isEmpty(author)){
            return ResultGenerator.getFailResult("请输入文章作者");
        }
        if(author.trim().length() > 100){
            return ResultGenerator.getFailResult("文章作者名字过长");
        }
        if(StringUtils.isEmpty(kind)){
            return ResultGenerator.getFailResult("请选择文章分类");
        }
        if(content.trim().length() > 100000){
            return ResultGenerator.getFailResult("文章内容过长");
        }
        if(StringUtils.isEmpty(coverImage)){
            return ResultGenerator.getFailResult("封面图不能为空");
        }

        //拿到实例类  将实例类存储到数据库中  放回结果字符串 success就代表成功 否则失败
        IndexNews indexNews = new IndexNews();
        indexNews.setId(idWorker.nextId()+"");
        indexNews.setAuthor(author);
        indexNews.setTitle(title);
        indexNews.setContent(content);
        indexNews.setCreateTime(new Date());
        indexNews.setFirstPicture(coverImage);
        indexNews.setDescription(description);
        indexNews.setFlag(flag);
        indexNews.setKind(kind);

        String save = null;
        try {
            save = indexNewsService.saveNews(indexNews);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if("success".equals(save)){
            return ResultGenerator.getSuccessResult("添加成功");
        }else {
            return  ResultGenerator.getFailResult("添加失败");
        }

    }


    /**
     * 更新/修改博客内容
     * @param id
     * @param title
     * @param description
     * @param kind
     * @param author
     * @param content
     * @param coverImage
     * @param flag
     * @return
     */
    @PostMapping("/news/update")
    @ResponseBody
    public Result update(@RequestParam("id") String id,
                         @RequestParam("title") String title,
                         @RequestParam("description") String description,
                         @RequestParam("kind") String kind,
                         @RequestParam("author") String author,
                         @RequestParam("content") String content,
                         @RequestParam("coverImage") String coverImage,
                         @RequestParam("flag")Integer  flag){
        if(StringUtils.isEmpty(title)){
            return ResultGenerator.getFailResult("请输入文章标题");
        }
        if(title.trim().length() > 150){
            return ResultGenerator.getFailResult("标题过长");
        }
        if(StringUtils.isEmpty(description)){
            return ResultGenerator.getFailResult("请输入文章描述");
        }
        if(description.trim().length() > 450){
            return ResultGenerator.getFailResult("文章描述过长");
        }
        if(StringUtils.isEmpty(author)){
            return ResultGenerator.getFailResult("请输入文章作者");
        }
        if(author.trim().length() > 100){
            return ResultGenerator.getFailResult("文章作者名字过长");
        }
        if(StringUtils.isEmpty(kind)){
            return ResultGenerator.getFailResult("请选择文章分类");
        }
        if(content.trim().length() > 100000){
            return ResultGenerator.getFailResult("文章内容过长");
        }
        if(StringUtils.isEmpty(coverImage)){
            return ResultGenerator.getFailResult("封面图不能为空");
        }
        //拿到实例类  将实例类存储到数据库中  放回结果字符串 success就代表成功 否则失败
        IndexNews indexNews = new IndexNews();
        indexNews.setId(id);
        indexNews.setAuthor(author);
        indexNews.setContent(content);
        indexNews.setUpdateTime(new Date());
        indexNews.setFirstPicture(coverImage);
        indexNews.setDescription(description);
        indexNews.setFlag(flag);
        indexNews.setKind(kind);


        String save = null;
        try {
            save = indexNewsService.updataNews(indexNews);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if("success".equals(save)){
            return ResultGenerator.getSuccessResult("更新成功");
        }else {
            return  ResultGenerator.getFailResult("更新失败");
        }
    }



    /**
     * 列表信息展示 + 搜索信息展示  + 分页操作
     * @param params
     * @return
     */
    @GetMapping("/news/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> params){
        //判断参数里是否为空
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        //查询到的Map集合放入 响应结果集中
        PageQueryUtil queryUtil = new PageQueryUtil(params);
        PageResult pageResult = indexNewsService.getNewsPage(queryUtil);
        return ResultGenerator.getSuccessResult(pageResult);
    }


    /**
     * 删除新闻
     * @param ids
     * @return
     */
    public Result delete(@RequestBody Integer[] ids){
        if(ids.length < 1){
            return ResultGenerator.getFailResult("参数异常");
        }
        if(indexNewsService.deleteBatch(ids)){
            return ResultGenerator.getSuccessResult("删除成功");
        }
        return ResultGenerator.getFailResult("删除失败");
    }






}
