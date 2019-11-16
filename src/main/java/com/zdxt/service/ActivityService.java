package com.zdxt.service;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.model.ActivityProgram;
import com.zdxt.model.CooperativeResources;

public interface ActivityService {

    PageResult getActivityPage(PageQueryUtil pageUtil);
    int getTotalActivity();
    public ActivityProgram findCooperativeActivityProgramByid(String id);
    public String save(ActivityProgram activityProgram);
    Boolean deleteBatch(String[] ids);
    public String updateActivityProgram(ActivityProgram activityProgram);
    PageResult getActivityPageApi(PageQueryUtil pageUtil);
    int getTotalActivityApi();
}
