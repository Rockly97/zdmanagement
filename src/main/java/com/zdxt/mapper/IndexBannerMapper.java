package com.zdxt.mapper;


import com.zdxt.model.IndexBanner;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.Banner;

import java.util.List;

public interface IndexBannerMapper {
     List<IndexBanner> findAllBanner();
    boolean insert(IndexBanner indexBanner);
    boolean updateBanner(@Param("indexBanner")IndexBanner indexBanner);
    IndexBanner findBannerById(String id);
    public int deleteBanner(String[] ids);
}