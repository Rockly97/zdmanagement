package com.zdxt.mapper;


import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.model.ContactWe;
import com.zdxt.model.CooperativeResources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContactWeMapper {
    List<ContactWe> findContactWeList(PageQueryUtil pageUtil);
    int getTotalinterface(PageQueryUtil pageUtil);
    public int deleteinterface(String[] ids);
    ContactWe findContactWeContactWeById(String id);
    boolean insert(ContactWe contactWe);

}