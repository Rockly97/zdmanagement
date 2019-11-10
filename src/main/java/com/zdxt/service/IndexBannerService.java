package com.zdxt.service;

import com.zdxt.model.IndexBanner;

import java.util.List;

public interface IndexBannerService {
    public List<IndexBanner> findAll();
    public String save (IndexBanner banner);
}
