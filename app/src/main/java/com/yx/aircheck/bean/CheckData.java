package com.yx.aircheck.bean;

import org.litepal.crud.DataSupport;
<<<<<<< HEAD
public class CheckData extends DataSupport{
=======
public class CheckData {
>>>>>>> add dynamic chart
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String tem;//温度
    private String hum;//湿度
    private String choh;//甲醛
    private String pm25;//PM2.5
    private String pm10;//PM10
    private String range;//空气质量等级
    private String username;//用户名
    private String gpsInfo;//GPS信息
    private String deviceInfo;//设备信息
    private String time;//时间

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(String gpsInfo) {
        this.gpsInfo = gpsInfo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getChoh() {
        return choh;
    }

    public void setChoh(String choh) {
        this.choh = choh;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
    public String newToString(){
        return  "温度:" + tem +
                ",湿度:" + hum +
                ",甲醛:" + choh +
                ",PM2.5:" + pm25 +
                ",PM10:" + pm10 +
                ",等级:" + range;
    }
    @Override
    public String toString() {
        return  "tem=" + tem +
                ",hum=" + hum +
                ",choh=" + choh +
                ",pm25=" + pm25 +
                ",pm10=" + pm10 +
                ",range=" + range +
                ",username=" + username +
                ",gpsInfo=" + gpsInfo +
                ",deviceInfo=" + deviceInfo +
                ",time=" + time;
    }
}
