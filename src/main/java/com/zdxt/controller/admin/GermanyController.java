package com.zdxt.controller.admin;

import com.zdxt.common.util.*;
import com.zdxt.model.GermanyNews;
import com.zdxt.model.IndexNews;
import com.zdxt.service.GermanyNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Rockly on 2019/11/12 19:50.
 */
@RequestMapping("/admin")
@Controller
public class GermanyController {

    @Autowired
    IdWorker idWorker;

    @Autowired
    private GermanyNewsService germanyNewsService;


    @RequestMapping("/germanynews")
    public String news (HttpServletRequest request){
        request.setAttribute("path","germanynews");
        return "germanynews";
    }

    @RequestMapping("/germanynews/edit")
    public String edit(HttpServletRequest request){
        request.setAttribute("path","germanynewsedit");
        return "germanynewsedit";
    }




    @RequestMapping("/germanynews/edit/{germanyId}")
    public String editNewsId(HttpServletRequest request,@PathVariable("germanyId") String germanyId){
        GermanyNews germanyNews = germanyNewsService.getGermanyNewsItem(germanyId);
        request.setAttribute("germanyNews",germanyNews);
        return "germanynewsedit";
    }

    /**
     * 保存博客
     * @param title
     * @param author
     * @param content
     * @param flag
     * @return
     */
    @PostMapping("/germanynews/save")
    @ResponseBody
    public Result save(@RequestParam("title") String title,
                       @RequestParam("author") String author,
                       @RequestParam("content") String content,
                       @RequestParam("flag")Integer flag){

        if(StringUtils.isEmpty(title)){
            return ResultGenerator.getFailResult("请输入文章标题");
        }
        if(title.trim().length() > 150){
            return ResultGenerator.getFailResult("标题过长");
        }
        if(StringUtils.isEmpty(author)){
            return ResultGenerator.getFailResult("请输入文章作者");
        }
        if(author.trim().length() > 100){
            return ResultGenerator.getFailResult("文章作者名字过长");
        }
        if(content.trim().length() > 100000){
            return ResultGenerator.getFailResult("文章内容过长");
        }

        //拿到实例类  将实例类存储到数据库中  放回结果字符串 success就代表成功 否则失败
        GermanyNews germanyNews = new GermanyNews();
        germanyNews.setAuthor(author);
        germanyNews.setTitle(title);
        germanyNews.setContent(content);
        germanyNews.setCreateTime(new Date());
        germanyNews.setFlag(flag);
        germanyNews.setId(idWorker.nextId()+"");

        String save = germanyNewsService.saveGermanyNews(germanyNews);
        if("success".equals(save)){
            return ResultGenerator.getSuccessResult("添加成功");
        }else {
            return ResultGenerator.getFailResult("添加失败");
        }
    }


    /**
     * 更新/修改博客内容
     */
    @PostMapping("/germanynews/update")
    @ResponseBody
    public Result update(@RequestParam("id") String id,
                         @RequestParam("title") String title,
                         @RequestParam("author") String author,
                         @RequestParam("content") String content,
                         @RequestParam("flag")Integer flag){
        if(StringUtils.isEmpty(title)){
            return ResultGenerator.getFailResult("请输入文章标题");
        }
        if(title.trim().length() > 150){
            return ResultGenerator.getFailResult("标题过长");
        }
        if(StringUtils.isEmpty(author)){
            return ResultGenerator.getFailResult("请输入文章作者");
        }
        if(author.trim().length() > 100){
            return ResultGenerator.getFailResult("文章作者名字过长");
        }
        if(content.trim().length() > 100000){
            return ResultGenerator.getFailResult("文章内容过长");
        }

        //拿到实例类  将实例类存储到数据库中  放回结果字符串 success就代表成功 否则失败
        GermanyNews germanyNews = new GermanyNews();
        germanyNews.setAuthor(author);
        germanyNews.setTitle(title);
        germanyNews.setContent(content);
        germanyNews.setCreateTime(new Date());
        germanyNews.setFlag(flag);
        germanyNews.setId(id);


        String save = germanyNewsService.updataGermanyNews(germanyNews);

        if("success".equals(save)){
            return ResultGenerator.getSuccessResult("添加成功");
        }else {
            return ResultGenerator.getFailResult("添加失败");
        }
    }



    /**
     * 列表信息展示 + 搜索信息展示  + 分页操作
     * @param params
     * @return
     */
    @GetMapping("/germanynews/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> params){
        //判断参数里是否为空
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        //查询到的Map集合放入 响应结果集中
        PageQueryUtil queryUtil = new PageQueryUtil(params);
        PageResult pageResult = germanyNewsService.getGermanyNewsPage(queryUtil);
        return ResultGenerator.getSuccessResult(pageResult);
    }


    /**
     * 删除新闻
     * @param ids
     * @return
     */
    @RequestMapping("/germanynews/delete")
    @ResponseBody
    public Result delete(@RequestBody String[] ids){
        if(ids.length < 1){
            return ResultGenerator.getFailResult("参数异常");
        }
        if(germanyNewsService.deleteBatch(ids)){
            return ResultGenerator.getSuccessResult("删除成功");
        }
        return ResultGenerator.getFailResult("删除失败");
    }



}
