package com.zdxt.service.impl;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.mapper.ContactWeMapper;
import com.zdxt.model.ContactWe;
import com.zdxt.model.CooperativeResources;
import com.zdxt.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactWeMapper contactWeMapper;
    @Override
    public PageResult getContactPage(PageQueryUtil pageUtil) {
        List<ContactWe> contactWeList = contactWeMapper.findContactWeList(pageUtil);
        if(contactWeList.size()==0||contactWeList==null){
            return null;
        }

//        查询总数
        int total = contactWeMapper.getTotalinterface(pageUtil);
        PageResult pageResult = new PageResult(contactWeList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalContact() {
        return contactWeMapper.getTotalinterface(null);
    }

    @Override
    public ContactWe findContactWeByid(String id) {
        ContactWe contactWeContactWeById = contactWeMapper.findContactWeContactWeById(id);
        if(contactWeContactWeById!=null){
            return contactWeContactWeById;
        }else {
            return null;
        }
    }

    @Override
    public String save(ContactWe contactWe) {
        boolean insert = contactWeMapper.insert(contactWe);
        if(insert){
            return "success";
        }else {
            return  "添加失败";
        }
    }

    @Override
    public Boolean deleteBatch(String[] ids) {
        return contactWeMapper.deleteinterface(ids) > 0;
    }
}
