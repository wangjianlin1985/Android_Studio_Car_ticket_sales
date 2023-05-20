package com.mobileserver.domain;

public class PlaneInfo {
    /*记录编号*/
    private int seatId;
    public int getSeatId() {
        return seatId;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
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

    /*车次日期*/
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

    /*票价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*总座位数*/
    private int seatNumber;
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /*剩余座位数*/
    private int leftSeatNumber;
    public int getLeftSeatNumber() {
        return leftSeatNumber;
    }
    public void setLeftSeatNumber(int leftSeatNumber) {
        this.leftSeatNumber = leftSeatNumber;
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

    /*历时*/
    private String totalTime;
    public String getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

}