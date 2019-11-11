package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.CooperativeResources;

import java.util.List;

public interface CooperativeResourcesMapper {
    List<CooperativeResources> findResourcesList(PageQueryUtil pageUtil);
    int getTotalResources(PageQueryUtil pageUtil);
}