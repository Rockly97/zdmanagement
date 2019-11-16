package com.zdxt.mapper;


import com.zdxt.model.IndexBanner;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.Banner;

import java.util.List;

public interface IndexBannerMapper {
     List<IndexBanner> findAllBanner();
    IndexBanner findBannerById(String id);
    boolean insert(IndexBanner indexBanner);
    boolean updateBanner(@Param("indexBanner")IndexBanner indexBanner);
    public int deleteBanner(String[] ids);
    int getCount();
}