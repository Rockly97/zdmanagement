package com.zdxt.controller.admin;

import com.zdxt.common.baidu.ueditor.ActionEnter;
import com.zdxt.common.util.IdWorker;
import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.IndexNews;
import com.zdxt.service.IndexNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

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
        indexNews.setContent(content);
        indexNews.setCreateTime(new Date());
        indexNews.setFirstPicture(coverImage);
        indexNews.setDescription(description);
        indexNews.setFlag(flag);
        indexNews.setKind(kind);

        String save = indexNewsService.saveNews(indexNews);
        if("success".equals(save)){
            return ResultGenerator.getSuccessResult("添加成功");
        }else {
            return  ResultGenerator.getFailResult("添加失败");
        }

    }



}
