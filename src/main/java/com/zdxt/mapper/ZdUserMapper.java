package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.ZdUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ZdUserMapper {

    ZdUser login(@Param("userName") String userName,@Param("password") String password);

    boolean updateUser(ZdUser zdUser);

    boolean saveUser(ZdUser zdUser);

    ZdUser findUserName(String username);

    boolean deleteUser(String[] userid);

    Integer findCountUser(PageQueryUtil queryUtil);

    List<ZdUser> fingUserList(PageQueryUtil queryUtil);
}