package com.zdxt.service.impl;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.SearchResult;
import com.zdxt.mapper.ActivityProgramMapper;
import com.zdxt.mapper.CooperativeResourcesMapper;
import com.zdxt.mapper.GermanyNewsMapper;
import com.zdxt.mapper.IndexNewsMapper;
import com.zdxt.model.ActivityProgram;
import com.zdxt.model.CooperativeResources;
import com.zdxt.model.GermanyNews;
import com.zdxt.model.IndexNews;
import com.zdxt.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SearchServiceImpl implements SearchService{
    @Autowired
    IndexNewsMapper indexNewsMapper;
    @Autowired
    ActivityProgramMapper activityProgramMapper;
    @Autowired
    CooperativeResourcesMapper cooperativeResourcesMapper;
    @Autowired
    GermanyNewsMapper germanyNewsMapper;
    @Override
    public Map<String, SearchResult> Search(String search) {
        Map<String, SearchResult> map=new HashMap<>();
        List<IndexNews> list1 = indexNewsMapper.findSearch(search);
        SearchResult searchResult1=new SearchResult();
        searchResult1.setsTitle("新闻动态");
        searchResult1.setList(list1);

        List<ActivityProgram> search1 = activityProgramMapper.findSearch(search);
        SearchResult searchResult2=new SearchResult();
        searchResult2.setsTitle("活动计划");
        searchResult2.setList(search1);

        List<CooperativeResources> search2 = cooperativeResourcesMapper.findSearch(search);
        SearchResult searchResult3=new SearchResult();
        searchResult3.setsTitle("合作资源");
        searchResult3.setList(search2);

        List<GermanyNews> search4 = germanyNewsMapper.findSearch(search);
        SearchResult searchResult4=new SearchResult();
        searchResult4.setsTitle("趣味德国");
        searchResult4.setList(search4);
        map.put("indexNews",searchResult1);
        map.put("activity",searchResult2);
        map.put("resources",searchResult3);
        map.put("germanyNews",searchResult4);
        if(map.size()==0||map==null){
            return null;
        }
        return map;
    }
}
