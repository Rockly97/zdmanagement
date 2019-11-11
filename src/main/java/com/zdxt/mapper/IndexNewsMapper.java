package com.zdxt.mapper;


import com.zdxt.model.IndexNews;
import org.springframework.stereotype.Component;

@Component
public interface IndexNewsMapper {

    boolean insertNews(IndexNews indexNews);

}