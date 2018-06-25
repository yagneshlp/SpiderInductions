package com.yagneshlp.spiderinductions.pojo.pojo_tracklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {

    @SerializedName("status_code")
    @Expose
    private Long statusCode;
    @SerializedName("execute_time")
    @Expose
    private Double executeTime;

    public Long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Long statusCode) {
        this.statusCode = statusCode;
    }

    public Double getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Double executeTime) {
        this.executeTime = executeTime;
    }

}