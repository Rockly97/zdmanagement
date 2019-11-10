package com.zdxt.mapper;


import com.zdxt.model.IndexBanner;

import java.util.List;

public interface IndexBannerMapper {
    public List<IndexBanner> findAllBanner();
    boolean insert(IndexBanner indexBanner);
}