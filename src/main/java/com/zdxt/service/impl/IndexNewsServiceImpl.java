package com.zdxt.service.impl;

import com.zdxt.mapper.IndexNewsMapper;
import com.zdxt.model.IndexNews;
import com.zdxt.service.IndexNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rockly on 2019/11/10 23:06.
 */

@Service
public class IndexNewsServiceImpl implements IndexNewsService {

    @Autowired
    private IndexNewsMapper indexNewsMapper;

    @Override
    public String saveNews(IndexNews indexNews) {
        boolean falg = indexNewsMapper.insertNews(indexNews);
        if(falg){
            return "success";
        }else {
            return "failure";
        }

    }



}
