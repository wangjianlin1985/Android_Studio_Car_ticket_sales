package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderInfo {
    /*记录编号*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*车次信息*/
    private PlaneInfo planeObj;
    public PlaneInfo getPlaneObj() {
        return planeObj;
    }
    public void setPlaneObj(PlaneInfo planeObj) {
        this.planeObj = planeObj;
    }

    /*车次*/
    private String planeNumber;
    public String getPlaneNumber() {
        return planeNumber;
    }
    public void setPlaneNumber(String planeNumber) {
        this.planeNumber = planeNumber;
    }

    /*始发汽车站*/
    private StationInfo startStation;
    public StationInfo getStartStation() {
        return startStation;
    }
    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }

    /*终到汽车站*/
    private StationInfo endStation;
    public StationInfo getEndStation() {
        return endStation;
    }
    public void setEndStation(StationInfo endStation) {
        this.endStation = endStation;
    }

    /*车次日期*/
    private Timestamp startDate;
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /*席别*/
    private SeatType seatType;
    public SeatType getSeatType() {
        return seatType;
    }
    public void setSeatType(SeatType seatType) {
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