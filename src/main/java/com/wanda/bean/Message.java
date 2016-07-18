package com.wanda.bean;

/**
 * Created by 钱斌 on 2016/7/12.
 */

public class Message {
    private int time;

    private String floor;

    private String mac;

    private String y;

    private String plaza;

    private String x;

    public void setTime(int time){
        this.time = time;
    }
    public int getTime(){
        return this.time;
    }
    public void setFloor(String floor){
        this.floor = floor;
    }
    public String getFloor(){
        return this.floor;
    }
    public void setMac(String mac){
        this.mac = mac;
    }
    public String getMac(){
        return this.mac;
    }
    public void setY(String y){
        this.y = y;
    }
    public String getY(){
        return this.y;
    }
    public void setPlaza(String plaza){
        this.plaza = plaza;
    }
    public String getPlaza(){
        return this.plaza;
    }
    public void setX(String x){
        this.x = x;
    }
    public String getX(){
        return this.x;
    }


    @Override
    public String toString() {
        return this.getMac() + "\t" + this.getPlaza() + "\t" + this.getFloor() + "\t" + this.getTime()
                + "\t" + this.getX() + "\t" + this.getY();
    }
}
