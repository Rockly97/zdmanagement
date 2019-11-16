package com.zdxt.common.util;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private String sTitle;
    //列表数据
    private List<?> list;

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
