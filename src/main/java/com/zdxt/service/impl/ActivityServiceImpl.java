package com.zdxt.service.impl;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.mapper.ActivityProgramMapper;
import com.zdxt.model.ActivityProgram;
import com.zdxt.model.CooperativeResources;
import com.zdxt.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActivityServiceImpl implements ActivityService{
    @Autowired
    ActivityProgramMapper activityProgramMapper;
    @Override
    public PageResult getActivityPage(PageQueryUtil pageUtil) {
        List<ActivityProgram> blogList = activityProgramMapper.findActivityProgramList(pageUtil);
        if(blogList.size()==0||blogList==null){
            return null;
        }

//        查询总数
        int total = activityProgramMapper.getTotalActivity(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalActivity() {
        return   activityProgramMapper.getTotalActivity(null);
    }

    @Override
    public ActivityProgram findCooperativeActivityProgramByid(String id) {
        return activityProgramMapper.findActivityProgramById(id);
    }

    @Override
    public String save(ActivityProgram activityProgram) {
        boolean insert = activityProgramMapper.insert(activityProgram);
        if(insert){
            return "success";
        }else {
            return  "添加失败";
        }


    }

    @Override
    public Boolean deleteBatch(String[] ids) {
        return activityProgramMapper.deleteActivity(ids) > 0;
    }

    @Override
    public String updateActivityProgram(ActivityProgram activityProgram) {
        boolean b = activityProgramMapper.updateActivityProgram(activityProgram);
        if(b){
            return "success";
        }else {
            return  "修改失败";
        }
    }

    @Override
    public PageResult getActivityPageApi(PageQueryUtil pageUtil) {
        List<ActivityProgram> blogList = activityProgramMapper.findActivityProgramListAPi(pageUtil);
        if(blogList.size()==0||blogList==null){
            return null;
        }

//        查询总数
        int total = activityProgramMapper.getTotalActivityApi(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalActivityApi() {
        return activityProgramMapper.getTotalActivityApi(null);
    }
}
