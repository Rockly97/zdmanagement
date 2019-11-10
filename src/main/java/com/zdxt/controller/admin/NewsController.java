package com.zdxt.controller.admin;

import com.zdxt.common.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Rockly on 2019/11/8 15:36.
 */
@Controller
@RequestMapping("/admin")
public class NewsController {

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/test")
    public String test(HttpServletRequest request){
        request.setAttribute("path","test");
       return "test";
    }
    @RequestMapping("/edit")
    public String edit(HttpServletRequest request){
        request.setAttribute("path","newsedit");
        return "newsedit";
    }

}
