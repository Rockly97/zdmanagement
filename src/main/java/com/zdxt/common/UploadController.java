package com.zdxt.common;


import com.zdxt.common.util.MyBlogUtils;
import com.zdxt.common.util.Result;
import com.zdxt.common.util.ResultGenerator;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


@Controller
@RequestMapping("/admin")
public class UploadController {
    public  static final String TEMP="C:\\upload\\img\\temp\\";
    public  static final String BANNER="C:\\upload\\img\\banner\\";
    public  static final String  NEWS="C:\\upload\\img\\news\\";
    public  static final String HEZUO="C:\\upload\\img\\hezuo\\";
    public  static final String XIANGDUI="C:\\upload\\img\\";
    @PostMapping({"/upload/file"})
    @ResponseBody
    public Result upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File(TEMP);
        //创建文件
        File destFile = new File(TEMP+newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result result = ResultGenerator.getSuccessResult();
        result.setData("http://106.52.3.235:8888/temp/"+newFileName);
        return result;
    }

}
