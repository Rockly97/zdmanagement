package com.zdxt.controller.admin;

import com.zdxt.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class BannerController {
    @Autowired
    IdWorker idWorker;
//    @RequestMapping(value ="/findAll",method = RequestMethod.GET)
//    public Result findAllCtClass(){
//        Result result=null;
//        List<CClass> allClass =cClassService.findAllClass();
//        if(allClass!=null){
//            result=new Result(ResultCode.SUCCESS,allClass);
//        }else {
//            result=new Result(ResultCode.FAIL);
//        }
//        return result;
//
//    }
}
