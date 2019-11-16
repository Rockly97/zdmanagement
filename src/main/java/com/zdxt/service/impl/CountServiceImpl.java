package com.zdxt.service.impl;

import com.zdxt.common.dto.Count;
import com.zdxt.mapper.*;
import com.zdxt.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountServiceImpl implements CountService{
    @Autowired
    ActivityProgramMapper activityProgramMapper;
    @Autowired
    ContactWeMapper contactWeMapper;
    @Autowired
    private GermanyNewsMapper germanyNewsMapper;
    @Autowired
    IndexBannerMapper indexBannerMapper;
    @Autowired
    private IndexNewsMapper indexNewsMapper;
    @Autowired
    CooperativeResourcesMapper cooperativeResourcesMapper;
    @Override
    public Count findCount() {
        Count count=new Count();
        count.setActivity(activityProgramMapper.getCount());
        count.setBanner(indexBannerMapper.getCount());
        count.setContact(contactWeMapper.getCount());
        count.setGermanyNews(germanyNewsMapper.getCount());
        count.setIndexNews(indexNewsMapper.getCount());
        count.setResources(cooperativeResourcesMapper.getCount());
        return count;
    }
}
