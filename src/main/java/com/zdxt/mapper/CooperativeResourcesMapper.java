package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.CooperativeResources;
import com.zdxt.model.IndexBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperativeResourcesMapper {
    int getCount();
    List<CooperativeResources> findResourcesList(PageQueryUtil pageUtil);
    List<CooperativeResources> findResourcesListApi(PageQueryUtil pageUtil);
    List<CooperativeResources> findSearch(String search);
    int getTotalResources(PageQueryUtil pageUtil);
    int getTotalResourcesApi(PageQueryUtil pageUtil);
    public int deleteResources(String[] ids);
    CooperativeResources findCooperativeResourcesById(String id);
    boolean insert(CooperativeResources cooperativeResources);
    boolean updateResources(@Param("cooperativeResources")CooperativeResources cooperativeResources);
}