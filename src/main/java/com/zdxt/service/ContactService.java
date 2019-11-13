package com.zdxt.service;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.model.ContactWe;
import com.zdxt.model.CooperativeResources;

public interface ContactService {

    PageResult getContactPage(PageQueryUtil pageUtil);
    int getTotalContact();
    public ContactWe findContactWeByid(String id);
    public String save(ContactWe contactWe);
    Boolean deleteBatch(String[] ids);

}
