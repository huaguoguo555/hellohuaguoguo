package com.huaguoguo.mapper;

import com.huaguoguo.entity.ForeJob;

public interface ForeJobDao {

    ForeJob selectForeJob(ForeJob job);

    void updateForeJob();

    void insertForeJob(ForeJob job);
}
