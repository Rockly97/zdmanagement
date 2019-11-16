package com.zdxt.service.impl;

import com.zdxt.common.util.MD5Util;
import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.mapper.ZdUserMapper;
import com.zdxt.model.ZdUser;
import com.zdxt.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Rockly on 2019/11/14 17:04.
 */

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private ZdUserMapper zdUserMapper;

    @Override
    public ZdUser login(String userName, String password) {
        String passwordMd5 = MD5Util.MD5Encode(password, "UTF-8");
        return zdUserMapper.login(userName,passwordMd5);
    }

    @Override
    public boolean updateUser(ZdUser zdUser) {
        if(!StringUtils.isEmpty(zdUser.getPassword())){
            zdUser.setPassword(MD5Util.MD5Encode(zdUser.getPassword(), "UTF-8"));
        }
        boolean flag = zdUserMapper.updateUser(zdUser);
        return flag;
    }

    @Override
    public String saveUser(ZdUser zdUser) {
        ZdUser user = zdUserMapper.findUserName(zdUser.getUsername());
        if(user == null){
            boolean flag = zdUserMapper.saveUser(zdUser);
            if(flag){
                return "success";
            }else {
                return "failure";
            }
        }
        return "nameture";
    }

    @Override
    public boolean delectUser(String[] userid) {
        boolean falg = zdUserMapper.deleteUser(userid);
        return falg;
    }

    @Override
    public PageResult fingUserList(PageQueryUtil queryUtil) {
        Integer count = zdUserMapper.findCountUser(queryUtil);
        List<ZdUser> zdUserList = zdUserMapper.fingUserList(queryUtil);
        PageResult pageResult = new PageResult(zdUserList,count,queryUtil.getPage(),queryUtil.getLimit());
        return pageResult;
    }
}
