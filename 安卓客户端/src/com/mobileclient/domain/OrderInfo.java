package com.mobileclient.domain;

import java.io.Serializable;

public class OrderInfo implements Serializable {
    /*记录编号*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*汽车站信息*/
    private int planeObj;
    public int getPlaneObj() {
        return planeObj;
    }
    public void setPlaneObj(int planeObj) {
        this.planeObj = planeObj;
    }

    /*汽车站*/
    private String planeNumber;
    public String getPlaneNumber() {
        return planeNumber;
    }
    public void setPlaneNumber(String planeNumber) {
        this.planeNumber = planeNumber;
    }

    /*始发汽车站*/
    private int startStation;
    public int getStartStation() {
        return startStation;
    }
    public void setStartStation(int startStation) {
        this.startStation = startStation;
    }

    /*终到汽车站*/
    private int endStation;
    public int getEndStation() {
        return endStation;
    }
    public void setEndStation(int endStation) {
        this.endStation = endStation;
    }

    /*汽车站日期*/
    private java.sql.Timestamp startDate;
    public java.sql.Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }

    /*席别*/
    private int seatType;
    public int getSeatType() {
        return seatType;
    }
    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    /*座位信息*/
    private String seatInfo;
    public String getSeatInfo() {
        return seatInfo;
    }
    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    /*总票价*/
    private float totalPrice;
    public float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /*发车时间*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*终到时间*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}