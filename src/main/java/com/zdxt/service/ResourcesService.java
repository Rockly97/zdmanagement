package com.zdxt.service;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;

public interface ResourcesService {

    PageResult getResourcePage(PageQueryUtil pageUtil);
    int getTotalResource();
}
