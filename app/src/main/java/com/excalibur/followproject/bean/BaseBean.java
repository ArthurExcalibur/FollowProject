package com.excalibur.followproject.bean;

/**
 * Created by lieniu on 2017/11/27.
 */

public class BaseBean {

    private boolean status;
    private String error_info;
    private long shijian;
    private String data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getError_info() {
        return error_info;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }

    public long getShijian() {
        return shijian;
    }

    public void setShijian(long shijian) {
        this.shijian = shijian;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status=" + status +
                ", error_info='" + error_info + '\'' +
                ", shijian=" + shijian +
                ", data='" + data + '\'' +
                '}';
    }
}
