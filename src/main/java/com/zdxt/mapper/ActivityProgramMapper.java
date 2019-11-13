package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.ActivityProgram;
import com.zdxt.model.CooperativeResources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityProgramMapper {
    List<ActivityProgram> findActivityProgramList(PageQueryUtil pageUtil);
    int getTotalActivity(PageQueryUtil pageUtil);
    public int deleteActivity(String[] ids);
    ActivityProgram findActivityProgramById(String id);
    boolean insert(ActivityProgram ActivityProgram);
    boolean updateActivityProgram(@Param("activityProgram")ActivityProgram activityProgram);
}