package com.zdxt.service;

import com.zdxt.model.IndexBanner;

import java.util.List;

public interface IndexBannerService {
    public List<IndexBanner> findAll();
    public String save (IndexBanner banner);
    public String updateBanner (IndexBanner banner);
    public IndexBanner findBannerByid (String id);
    Boolean deleteBatch(String[] ids);
}
