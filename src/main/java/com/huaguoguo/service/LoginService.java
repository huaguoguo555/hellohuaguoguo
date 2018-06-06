package com.huaguoguo.service;

import com.huaguoguo.entity.ResultModel;

import java.io.IOException;
import java.util.Map;

public interface LoginService {

    ResultModel login(Map<String,Object> input) throws IOException;
}
