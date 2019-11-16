package com.zdxt.service.impl;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.mapper.GermanyNewsMapper;
import com.zdxt.model.GermanyNews;
import com.zdxt.service.GermanyNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rockly on 2019/11/12 20:00.
 */

@Service
public class GermanyNewsServiceImpl implements GermanyNewsService{
    @Autowired
    private GermanyNewsMapper germanyNewsMapper;

    @Override
    public GermanyNews getGermanyNewsItem(String germanyId) {
        GermanyNews germanyNews = germanyNewsMapper.findGermanyNewsItem(germanyId);
        return germanyNews;
    }

    @Override
    public String saveGermanyNews(GermanyNews germanyNews) {
        boolean falg= germanyNewsMapper.insertGermanyNews(germanyNews);
        if(falg){
            return "success";
        }else {
            return "failure";
        }
    }

    @Override
    public String updataGermanyNews(GermanyNews germanyNews) {
        boolean falg = germanyNewsMapper.updateGermanyNews(germanyNews);
        if(falg){
            return "success";
        }else {
            return "failure";
        }
    }

    @Override
    public PageResult getGermanyNewsPage(PageQueryUtil queryUtil) {
        List<GermanyNews> germanyNewsList =  germanyNewsMapper.findGermanyNewsList(queryUtil);
        int total = germanyNewsMapper.getTotalGermanyNews(queryUtil);
        PageResult pageResult = new PageResult(germanyNewsList,total,queryUtil.getPage(),queryUtil.getLimit());
        return pageResult;
    }

    @Override
    public boolean deleteBatch(String[] ids) {
        boolean flag = germanyNewsMapper.deleteBatchGermanyNews(ids);
        return flag;
    }

    @Override
    public PageResult getGermanyNewsList(PageQueryUtil pageUtil) {
        List<GermanyNews> germanyNewsList =  germanyNewsMapper.findGermanyAllList(pageUtil);
        int total = germanyNewsMapper.getTotalGermanyNews(pageUtil);
        PageResult pageResult = new PageResult(germanyNewsList,total,pageUtil.getPage(),pageUtil.getLimit());
        return pageResult;
    }
}
