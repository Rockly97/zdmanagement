package com.zdxt.service;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.model.GermanyNews;

import java.util.List;

/**
 * Created by Rockly on 2019/11/12 19:59.
 */
public interface GermanyNewsService {
    GermanyNews getGermanyNewsItem(String germanyId);

    String saveGermanyNews(GermanyNews germanyNews);

    String updataGermanyNews(GermanyNews germanyNews);

    PageResult getGermanyNewsPage(PageQueryUtil queryUtil);

    boolean deleteBatch(String[] ids);

    PageResult getGermanyNewsList(PageQueryUtil pageUtil);
}
