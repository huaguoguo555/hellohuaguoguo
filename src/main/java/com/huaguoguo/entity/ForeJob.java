package com.huaguoguo.entity;

public class ForeJob {

    public String id;
    public String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ForeJob{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public ForeJob(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public ForeJob() {
    }
}
