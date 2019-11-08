package com.zdxt.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Rockly on 2019/11/7 18:33.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {


    @GetMapping({"/login"})
    public String login(HttpServletRequest request,HttpSession session){
        session.setAttribute("loginUser","1111");
        session.setAttribute("loginUserId","111");
        return "/login";
    }

    //获取登录用户名密码验证
    @PostMapping({"/login"})
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpServletRequest request,
                        HttpSession session){
        //校验数据  如果不对返回首页
        if(StringUtils.isEmpty(verifyCode)){
            request.setAttribute("errorMsg","验证码不能为空");
            return "admin/login";
        }
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            request.setAttribute("errorMsg","用户名或密码不能为空");
            return "admin/login";
        }

        //验证码做判断
        String kaptchaCode = (String) session.getAttribute("verifyCode");
        if(StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)){
            request.setAttribute("errorMsg","验证码错误");
            return "admin/login";
        }

        //查找用户是否存在 逻辑
//        boolean flag = adminService.login(userName,password);
//        if (!flag) {
//            request.setAttribute("errorMsg","登录失败");
//        } else {
//            //保存用户信息
//            AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();
//            session.setAttribute("loginUser",adminUser.getNickName());
//            session.setAttribute("loginUserId",adminUser.getAdminUserId());
//            return "redirect:/admin/index";
//        }
        return "admin/login";
    }



    /**
     * 首页
     * @param request
     * @return
     */
    @GetMapping({"","/","/index","/index.html"})
    public String index(HttpServletRequest request,HttpSession session){
        request.setAttribute("path","index");
        session.setAttribute("loginUser","1111");
        session.setAttribute("loginUserId","111");
        //标签展示
        request.setAttribute("categoryCount",1);
        request.setAttribute("blogCount",1);
        request.setAttribute("linkCount",1);
        request.setAttribute("tagCount",1);
        request.setAttribute("commentCount",1);
        return "index";
    }


    /**
     * 修改密码
     * @param session
     * @param newPassword
     * @param originalPassword
     * @return
     */
    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpSession session,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("originalPassword")String originalPassword){
        if(StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)){
            return "参数不能为空";
        }
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");
        // 修改成功 转跳到登录界面
//        if(adminService.updatePassword(loginUserId,originalPassword,newPassword)){
//            session.removeAttribute("loginUserId");
//            session.removeAttribute("loginUser");
//            session.removeAttribute("errorMsg");
//            return "success";
//        }
        return "修改失败";
    }


    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        return "admin/login";
    }

}
