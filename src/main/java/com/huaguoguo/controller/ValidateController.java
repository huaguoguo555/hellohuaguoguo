package com.huaguoguo.controller;

import com.huaguoguo.util.JedisClient;
import com.huaguoguo.util.VerifyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/validate")
public class ValidateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateController.class);

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "/sys/image",method = RequestMethod.GET)
    public Object image(@RequestParam("mobile") String mobile,HttpSession session){
        HashMap<String, Object> map = new HashMap<>();
        jedisClient.set("mobile",mobile);
        LOGGER.info("手机号已存入redis");
        map.put("imageUrl","http://localhost:8080/validate/image.png");
        return map;
    }

    @RequestMapping(value = "/image.png",method = RequestMethod.GET)
    public void valicode(HttpServletResponse response) throws Exception{
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        LOGGER.info("mobile:"+jedisClient.get("mobile"));
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);



    }

    @RequestMapping(value = "/image/code",method = RequestMethod.GET)
    public Object valicode1(HttpServletResponse response, HttpSession session) throws Exception{
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode",objs[0]);

        //将图片输出给浏览器
        HashMap<String, Object> map = new HashMap<>();
        map.put("image",objs[1]);
        return map;
    }

}
