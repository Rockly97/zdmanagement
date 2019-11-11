package com.zdxt.service.impl;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.mapper.CooperativeResourcesMapper;
import com.zdxt.model.CooperativeResources;
import com.zdxt.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    CooperativeResourcesMapper cooperativeResourcesMapper;
    @Override
    public PageResult getResourcePage(PageQueryUtil pageUtil) {
        List<CooperativeResources> blogList = cooperativeResourcesMapper.findResourcesList(pageUtil);
        if(blogList.size()==0||blogList==null){
            return null;
        }
//        查询总数
        int total = cooperativeResourcesMapper.getTotalResources(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalResource() {
      return   cooperativeResourcesMapper.getTotalResources(null);
    }
}
