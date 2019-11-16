package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.IndexNews;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IndexNewsMapper {
    int getCount();
    boolean insertNews(IndexNews indexNews);

    boolean updataNews(IndexNews indexNews);

    List<IndexNews> findNewsList(PageQueryUtil queryUtil);
    List<IndexNews> findSearch(String search);

    int getTotalNews(PageQueryUtil queryUtil);

    boolean deleteBatchNews(String[] ids);

    IndexNews findNewsImg(String id);

    IndexNews findByIdNews(String newsId);

    Integer fingCountBigImage(int level);

    List<String> getdelectImageAdd(String[] ids);

    List<IndexNews> findNewsListItme(PageQueryUtil pageUtil);

    int getTotalNewsItem(PageQueryUtil pageUtil);

    List<IndexNews> findNewsTopic();
}