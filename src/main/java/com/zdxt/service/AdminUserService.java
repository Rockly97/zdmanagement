package com.zdxt.service;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.model.ZdUser;

/**
 * Created by Rockly on 2019/11/14 17:03.
 */

public interface AdminUserService {

    ZdUser login(String userName, String password);

    boolean updateUser(ZdUser zdUser);

    String saveUser(ZdUser zdUser);

    boolean delectUser(String[] userid);

    PageResult fingUserList(PageQueryUtil queryUtil);
}
