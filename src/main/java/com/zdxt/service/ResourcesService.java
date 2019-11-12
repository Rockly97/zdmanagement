package com.zdxt.service;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.model.CooperativeResources;
import com.zdxt.model.IndexBanner;

public interface ResourcesService {

    PageResult getResourcePage(PageQueryUtil pageUtil);
    int getTotalResource();
    public CooperativeResources findCooperativeResourcesByid (String id);
    public String save (CooperativeResources cooperativeResources);
    Boolean deleteBatch(String[] ids);
    public String updateCooperativeResources (CooperativeResources cooperativeResources);
}
