package com.zdxt.service;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.model.IndexNews;

import java.io.IOException;

/**
 * Created by Rockly on 2019/11/10 23:06.
 */
public interface IndexNewsService {

    String saveNews(IndexNews indexNews) throws IOException;

    String updataNews(IndexNews indexNews) throws IOException;

    PageResult getNewsPage(PageQueryUtil queryUtil);

    boolean deleteBatch(String[] ids);

    IndexNews getNewsItem(String newsId);
}
