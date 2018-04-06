package com.huaguoguo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        logger.debug("hello");
        logger.info("hello");


        return "hello huaguoguo";
    }
}
