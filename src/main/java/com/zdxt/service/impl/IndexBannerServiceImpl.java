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

    @Override
    public String save(IndexBanner banner) {
        boolean insert = indexBannerMapper.insert(banner);
        if(insert){
            return "success";
        }else {
            return  "添加失败";
        }

    }

    @Override
    public String updateBanner(IndexBanner banner) {
        boolean b = indexBannerMapper.updateBanner(banner);
        if(b){
            return "success";
        }else {
            return  "修改失败";
        }

    }

    @Override
    public IndexBanner findBannerByid(String id) {
        IndexBanner banner = indexBannerMapper.findBannerById(id);
        if(!banner.getImg().equals("")&&!banner.getImg().isEmpty()){
            return banner;
        }else {
            return null;
        }

    }

    @Override
    public Boolean deleteBatch(String[] ids) {
        return indexBannerMapper.deleteBanner(ids) > 0;

    }
}
