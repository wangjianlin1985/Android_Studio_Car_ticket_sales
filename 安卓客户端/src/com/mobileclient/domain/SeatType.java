package com.mobileclient.domain;

import java.io.Serializable;

public class SeatType implements Serializable {
    /*记录编号*/
    private int seatTypeId;
    public int getSeatTypeId() {
        return seatTypeId;
    }
    public void setSeatTypeId(int seatTypeId) {
        this.seatTypeId = seatTypeId;
    }

    /*席别名称*/
    private String seatTypeName;
    public String getSeatTypeName() {
        return seatTypeName;
    }
    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

}