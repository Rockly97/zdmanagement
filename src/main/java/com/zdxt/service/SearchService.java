package com.zdxt.service;

import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.common.util.PageResult;
import com.zdxt.common.util.SearchResult;

import java.util.List;
import java.util.Map;

public interface SearchService {
    Map<String,SearchResult> Search(String search);
}
