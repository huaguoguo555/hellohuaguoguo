package com.huaguoguo.controller;

import com.huaguoguo.util.JedisClient;
import com.huaguoguo.util.VerifyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/validate")
public class ValidateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateController.class);

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "/sys/image",method = RequestMethod.GET)
    public Object image(@RequestParam("mobile") String mobile,@CookieValue("JSESSIONID") String sessionId){
        HashMap<String, Object> map = new HashMap<>();
        if(StringUtils.isEmpty(mobile)){
            //返回前端手机号不能为空
        }
        //校验手机号是否合法
        if (!matchMobile(mobile)){
            //返回前端手机号格式不合法
        }


        //将手机号存入redis
        jedisClient.set("image_code_mobile",mobile);
        LOGGER.info("手机号已存入redis");
        //把验证码图片的url返回给前端
        map.put("imageUrl","/validate/image.png");
        return map;
    }

    @RequestMapping(value = "/image.png",method = RequestMethod.GET)
    public void valicode(HttpServletResponse response) throws Exception{
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入redis
        String mobile = jedisClient.get("image_code_mobile");
        LOGGER.info("mobile:"+mobile);
        jedisClient.set("image_code_"+mobile, ((String) objs[0]));
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);



    }



    public static void main(String[] args) {

        String mobile = "15321545624";

        boolean isMatch = matchMobile(mobile);
        System.out.println(isMatch);
    }

    /**
     * 根据手机号的匹配规则匹配是否未手机号
     * @param mobile
     * @return
     */
    public static boolean matchMobile(String mobile){
        String pattern = "C";
        return Pattern.matches(pattern, mobile);
    }


}
