package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.CooperativeResources;
import com.zdxt.model.IndexBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperativeResourcesMapper {
    List<CooperativeResources> findResourcesList(PageQueryUtil pageUtil);
    int getTotalResources(PageQueryUtil pageUtil);
    public int deleteResources(String[] ids);
    CooperativeResources findCooperativeResourcesById(String id);
    boolean insert(CooperativeResources cooperativeResources);
    boolean updateResources(@Param("cooperativeResources")CooperativeResources cooperativeResources);
}