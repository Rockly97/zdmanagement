package com.zdxt.service;

import com.zdxt.model.IndexBanner;

import java.util.List;

public interface IndexBannerService {
    public List<IndexBanner> findAll();

    public String updateBanner (IndexBanner banner);
    public IndexBanner findBannerByid (String id);
      public String save (IndexBanner banner);
    Boolean deleteBatch(String[] ids);
}
