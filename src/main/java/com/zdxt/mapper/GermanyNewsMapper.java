package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.GermanyNews;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GermanyNewsMapper {

    GermanyNews findGermanyNewsItem(String germanyId);


    boolean insertGermanyNews(GermanyNews germanyNews);

    boolean updateGermanyNews(GermanyNews germanyNews);

    List<GermanyNews> findGermanyNewsList(PageQueryUtil queryUtil);
    List<GermanyNews> findSearch(String search);

    int getTotalGermanyNews(PageQueryUtil queryUtil);

    boolean deleteBatchGermanyNews(String[] ids);

    List<GermanyNews> findGermanyAllList(PageQueryUtil pageUtil);
}