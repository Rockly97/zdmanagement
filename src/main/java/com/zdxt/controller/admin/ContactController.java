package com.zdxt.controller.admin;

import com.zdxt.common.util.*;
import com.zdxt.model.ActivityProgram;
import com.zdxt.model.ContactWe;
import com.zdxt.service.ActivityService;
import com.zdxt.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping
public class ContactController {
    @Autowired
    IdWorker idWorker;
    @Autowired
    ContactService contactService;
    @GetMapping({"admin/contact"})
    public String banner(HttpServletRequest request, HttpSession session){
        request.setAttribute("path","contact");
        return "contact";
    }
    @GetMapping("/admin/contact/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        System.out.println(params.get("page"));
        System.out.println(params.get("limit"));
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult resourcePage = contactService.getContactPage(pageUtil);
        if (resourcePage==null||resourcePage.getList().size()==0){
            return ResultGenerator.getFailResult("查询失败！");
        }
        return ResultGenerator.getSuccessResult(resourcePage);
    }
    @PostMapping("/contact/save")
    @ResponseBody
    public Result save(@RequestParam("name") String name,
                       @RequestParam("coment") String coment,
                       @RequestParam("email") String email,
                       @RequestParam("phone") String phone
    ) {

        if (StringUtils.isEmpty(name)) {
            return ResultGenerator.getFailResult("请输入活动标题");
        }
        if (StringUtils.isEmpty(coment)) {
            return ResultGenerator.getFailResult("请输入活动标题");
        }
        if (StringUtils.isEmpty(email)) {
            return ResultGenerator.getFailResult("请输入活动标题");
        }
        if (StringUtils.isEmpty(phone)) {
            return ResultGenerator.getFailResult("请输入活动标题");
        }
        if (name.trim().length() > 30) {
            return ResultGenerator.getFailResult("姓名过长");
        }
        if (coment.trim().length() > 450) {
            return ResultGenerator.getFailResult("反馈内容过长");
        }
        if (!RegexUtils.checkEmail(email)) {
            return ResultGenerator.getFailResult("请输入正确的邮箱地址");
        }
        if (RegexUtils.checkPhone(phone)||RegexUtils.checkMobile(phone)) {
            ContactWe contactWe=new ContactWe();
            contactWe.setId(idWorker.nextId()+"");
            contactWe.setComent(coment);
            contactWe.setCreateTime(new Date());
            contactWe.setEmail(email);
            contactWe.setPhone(phone);
            contactWe.setName(name);
            String save = contactService.save(contactWe);
            if ("success".equals(save)) {
                return ResultGenerator.getSuccessResult("添加成功");
            } else {
                return ResultGenerator.getFailResult(save);
            }
        }else {
            return ResultGenerator.getFailResult("请输入正确的联系号码");
        }

    }
    @PostMapping("/admin/contact/delete")
    @ResponseBody
    public Result delete(@RequestBody String[] ids) {
        System.out.println(ids.toString());

        if (contactService.deleteBatch(ids)) {
            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult("删除失败");
        }
    }
}
