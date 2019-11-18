package com.zdxt.controller.reception;

import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import com.zdxt.model.IndexBanner;
import com.zdxt.service.IndexBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(tags = "BannerApi", description = "获取Banner图")
@RestController
@RequestMapping
public class BannerApiController {

    @Autowired
    IndexBannerService indexBannerService;

    @GetMapping("/banner/list")
    @ApiOperation(value = "Banner图")
    public Result<IndexBanner> list(){
        List<IndexBanner> all = indexBannerService.findAll();
        System.out.println(all);
        if(all!=null){
            return ResultGenerator.getSuccessResult(all);
        }else {
            return ResultGenerator.getFailResult("查询数据失败");
        }

    }
}
