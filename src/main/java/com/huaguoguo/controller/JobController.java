package com.huaguoguo.controller;

import com.huaguoguo.entity.ForeJob;
import com.huaguoguo.entity.ResultModel;
import com.huaguoguo.mapper.ForeJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
  * @Author:huaguoguo
  * @Description:
  * @Date: 2018/8/31 16:19
  */
@RestController
public class JobController {

    @Autowired
    private ForeJobDao foreJobDao;

    @RequestMapping(value = "/job/add",method = RequestMethod.GET)
    public Object addJob(){
        ResultModel result = new ResultModel();
        for (int i = 0; i < 100; i++) {
            String id = i + "";
            ForeJob foreJob = new ForeJob(id, "pending");
            foreJobDao.insertForeJob(foreJob);
        }
        result.setMsg("ok");
        result.setStatus(200l);
        return result;
    }
}
