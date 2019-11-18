package com.zdxt.controller.admin;

import com.zdxt.common.dto.Count;
import com.zdxt.common.util.*;
import com.zdxt.model.ZdUser;
import com.zdxt.service.AdminUserService;
import com.zdxt.service.CountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Rockly on 2019/11/14 16:44.
 */
@RequestMapping("/admin")
@Controller
public class LoginController {

    @Autowired
    IdWorker idWorker;
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private CountService countService;
    /**
     * 首页
     * @param request
     * @return
     */
    @GetMapping({"","/","/index","/index.html"})
    public String index(HttpServletRequest request,HttpSession session){
        request.setAttribute("path","index");
        //标签展示
        Count count = countService.findCount();
        request.setAttribute("count",count);
        return "index";
    }


    @RequestMapping("/user")
    public String user(HttpServletRequest request){
        request.setAttribute("path","user");
        return "user";
    }

    @RequestMapping("/user/useredit")
    public String useredit(){
        return "useredit";
    }



    @RequestMapping("/user/profile")
    public String profile(HttpServletRequest request){
        request.setAttribute("path","profile");
        return "updatepaw";
    }

    @PostMapping("/user/add")
    @ResponseBody
    public String userAdd(@RequestParam("email") String email,
                        @RequestParam("nickName") String nickName,
                        @RequestParam("phone") String phone,
                        @RequestParam("loginUserName") String loginUserName,
                        @RequestParam("password")String password) {

            ZdUser zdUser =  new ZdUser();
            zdUser.setId(idWorker.nextId()+"");
            zdUser.setUsername(loginUserName);
            zdUser.setName(nickName);
            zdUser.setPhone(phone);
            zdUser.setEmail(email);
            zdUser.setPassword(MD5Util.MD5Encode(password,"utf-8"));
            String msg = adminUserService.saveUser(zdUser);
            if("nameture".equals(msg)){
                return "用户已存在";
            }
            if("failure".equals(msg)){
                return "添加失败";
            }
            return "success";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session,
                        HttpServletRequest request) {
        if (StringUtils.isEmpty(verifyCode)) {
            request.setAttribute("errorMsg", "验证码不能为空");
            return "login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            request.setAttribute("errorMsg", "用户名或密码不能为空");
            return "login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            request.setAttribute("errorMsg", "验证码错误");
            return "login";
        }
        ZdUser adminUser = adminUserService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getName());
            session.setAttribute("loginUserId", adminUser.getId());
            session.setAttribute("zdUser",adminUser);
            if(adminUser.getUsername().equals("admin")){
                session.setAttribute("loginUserName" , adminUser.getUsername());
            }
            //session过期时间设置为7200秒 即两小时
            //session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index";
        } else {
            request.setAttribute("errorMsg", "登陆失败");
            return "login";
        }
    }

    @RequestMapping("/user/updata")
    @ResponseBody
    public String updataUser(@RequestParam("loginUserName") String loginUserName,
                             @RequestParam("userEmail") String userEmail,
                             @RequestParam("nickName") String nickName,
                             @RequestParam("userPhone") String userPhone,
                             HttpSession session){
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "参数不能为空";
        }
        ZdUser zdUser = new ZdUser();
        zdUser.setUsername(loginUserName);
        zdUser.setEmail(userEmail);
        zdUser.setPhone(userPhone);
        zdUser.setName(nickName);
        zdUser.setId((String) session.getAttribute("loginUserId"));
        if (adminUserService.updateUser(zdUser)) {
            return "success";
        } else {
            return "修改失败";
        }
    }


    @PostMapping("/user/password")
    @ResponseBody
    public String updatePaw(HttpSession session,
                            @RequestParam("newPassword") String newPassword,
                            @RequestParam("originalPassword")String originalPassword){
        if(StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)){
            return "参数不能为空";
        }
        String loginUserId = (String) session.getAttribute("loginUserId");
        ZdUser zdUser = new ZdUser();
        zdUser.setId(loginUserId);
        zdUser.setPassword(newPassword);
        boolean falg = adminUserService.updateUser(zdUser);
        if(falg){
            session.invalidate();
            return "success";
        }else {
            return "failure";
        }
    }




    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }


    @RequestMapping("/user/delete")
    @ResponseBody
    public Result deletUser(@RequestBody String[] ids){
        if(ids.length < 1){
            return ResultGenerator.getFailResult("参数异常");
        }
        boolean falg  = adminUserService.delectUser(ids);

        if(falg){
            return ResultGenerator.getSuccessResult("删除成功");
        }else {
            return ResultGenerator.getFailResult("删除失败");
        }
    }


    @RequestMapping("/user/list")
    @ResponseBody
    public Result userList(@RequestParam Map<String,Object> params){
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        PageQueryUtil queryUtil = new PageQueryUtil(params);
        PageResult  pageResult = adminUserService.fingUserList(queryUtil);
        return ResultGenerator.getSuccessResult(pageResult);
    }
}
