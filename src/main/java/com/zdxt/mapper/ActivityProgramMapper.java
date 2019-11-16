package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.ActivityProgram;
import com.zdxt.model.CooperativeResources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityProgramMapper {
    int getCount();
    List<ActivityProgram> findActivityProgramList(PageQueryUtil pageUtil);
    List<ActivityProgram> findActivityProgramListAPi(PageQueryUtil pageUtil);
    List<ActivityProgram> findSearch(String search);
    int getTotalActivity(PageQueryUtil pageUtil);
    int getTotalActivityApi(PageQueryUtil pageUtil);
    public int deleteActivity(String[] ids);
    ActivityProgram findActivityProgramById(String id);
    boolean insert(ActivityProgram ActivityProgram);
    boolean updateActivityProgram(@Param("activityProgram")ActivityProgram activityProgram);
}