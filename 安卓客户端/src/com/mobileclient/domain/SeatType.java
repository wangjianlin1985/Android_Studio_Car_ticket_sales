package com.mobileclient.domain;

import java.io.Serializable;

public class SeatType implements Serializable {
    /*��¼���*/
    private int seatTypeId;
    public int getSeatTypeId() {
        return seatTypeId;
    }
    public void setSeatTypeId(int seatTypeId) {
        this.seatTypeId = seatTypeId;
    }

    /*ϯ������*/
    private String seatTypeName;
    public String getSeatTypeName() {
        return seatTypeName;
    }
    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

}