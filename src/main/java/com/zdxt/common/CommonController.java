package com.zdxt.common;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * 验证码的请求
 * Created by Rockly on 2019/7/26 17:49.
 */

@Controller
public class CommonController {

    @Autowired
    private DefaultKaptcha captchaProducer;

    @RequestMapping("/common/kaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            String verifyCode = captchaProducer.createText();
            //把验证码封装到session
            request.getSession().setAttribute("verifyCode",verifyCode);
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge,"jpg",imgOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        captchaOutputStream = imgOutputStream.toByteArray();
        response.setHeader("Cache-Control","");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",10);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();

    }

    @RequestMapping("/error/error404")
    public String error(){
        return "/error/error_404";
    }


    @RequestMapping("/error/unauthorized")
    public String unauthorized(){
        return "/error/error_unauthorized";
    }
}
