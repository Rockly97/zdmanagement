package com.zdxt.service.impl;

import com.zdxt.mapper.IndexBannerMapper;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class IndexBannerServiceImpl implements IndexBannerService {
   @Autowired
    IndexBannerMapper indexBannerMapper;
    @Override
    public List<IndexBanner> findAll() {
        List<IndexBanner> allBanner = indexBannerMapper.findAllBanner();
        return allBanner;
    }
}
