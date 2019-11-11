package com.zdxt.common.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 分页工具类
 *
 * Created by Rockly on 2019/7/29 18:00.
 */

@Data
public class PageResult implements Serializable{
    //总记录数
    private int totalCount;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private int currPage;
    //列表数据
    private List<?> list;

    public PageResult(List<?> list,int totalCount,int pageSize,int currPage){
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (totalCount % pageSize) == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
    }


}
