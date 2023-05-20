package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.PlaneInfo;
import com.mobileserver.util.DB;

public class PlaneInfoDAO {

	public List<PlaneInfo> QueryPlaneInfo(String planeNumber,int startStation,int endStation,Timestamp startDate,int seatType) {
		List<PlaneInfo> planeInfoList = new ArrayList<PlaneInfo>();
		DB db = new DB();
		String sql = "select * from PlaneInfo where 1=1";
		if (!planeNumber.equals(""))
			sql += " and planeNumber like '%" + planeNumber + "%'";
		if (startStation != 0)
			sql += " and startStation=" + startStation;
		if (endStation != 0)
			sql += " and endStation=" + endStation;
		if(startDate!=null)
			sql += " and startDate='" + startDate + "'";
		if (seatType != 0)
			sql += " and seatType=" + seatType;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				PlaneInfo planeInfo = new PlaneInfo();
				planeInfo.setSeatId(rs.getInt("seatId"));
				planeInfo.setPlaneNumber(rs.getString("planeNumber"));
				planeInfo.setStartStation(rs.getInt("startStation"));
				planeInfo.setEndStation(rs.getInt("endStation"));
				planeInfo.setStartDate(rs.getTimestamp("startDate"));
				planeInfo.setSeatType(rs.getInt("seatType"));
				planeInfo.setPrice(rs.getFloat("price"));
				planeInfo.setSeatNumber(rs.getInt("seatNumber"));
				planeInfo.setLeftSeatNumber(rs.getInt("leftSeatNumber"));
				planeInfo.setStartTime(rs.getString("startTime"));
				planeInfo.setEndTime(rs.getString("endTime"));
				planeInfo.setTotalTime(rs.getString("totalTime"));
				planeInfoList.add(planeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return planeInfoList;
	}
	/* ���복����Ϣ���󣬽��г�����Ϣ�����ҵ�� */
	public String AddPlaneInfo(PlaneInfo planeInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����³�����Ϣ */
			String sqlString = "insert into PlaneInfo(planeNumber,startStation,endStation,startDate,seatType,price,seatNumber,leftSeatNumber,startTime,endTime,totalTime) values (";
			sqlString += "'" + planeInfo.getPlaneNumber() + "',";
			sqlString += planeInfo.getStartStation() + ",";
			sqlString += planeInfo.getEndStation() + ",";
			sqlString += "'" + planeInfo.getStartDate() + "',";
			sqlString += planeInfo.getSeatType() + ",";
			sqlString += planeInfo.getPrice() + ",";
			sqlString += planeInfo.getSeatNumber() + ",";
			sqlString += planeInfo.getLeftSeatNumber() + ",";
			sqlString += "'" + planeInfo.getStartTime() + "',";
			sqlString += "'" + planeInfo.getEndTime() + "',";
			sqlString += "'" + planeInfo.getTotalTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������Ϣ */
	public String DeletePlaneInfo(int seatId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from PlaneInfo where seatId=" + seatId;
			db.executeUpdate(sqlString);
			result = "������Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��������Ϣ */
	public PlaneInfo GetPlaneInfo(int seatId) {
		PlaneInfo planeInfo = null;
		DB db = new DB();
		String sql = "select * from PlaneInfo where seatId=" + seatId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				planeInfo = new PlaneInfo();
				planeInfo.setSeatId(rs.getInt("seatId"));
				planeInfo.setPlaneNumber(rs.getString("planeNumber"));
				planeInfo.setStartStation(rs.getInt("startStation"));
				planeInfo.setEndStation(rs.getInt("endStation"));
				planeInfo.setStartDate(rs.getTimestamp("startDate"));
				planeInfo.setSeatType(rs.getInt("seatType"));
				planeInfo.setPrice(rs.getFloat("price"));
				planeInfo.setSeatNumber(rs.getInt("seatNumber"));
				planeInfo.setLeftSeatNumber(rs.getInt("leftSeatNumber"));
				planeInfo.setStartTime(rs.getString("startTime"));
				planeInfo.setEndTime(rs.getString("endTime"));
				planeInfo.setTotalTime(rs.getString("totalTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return planeInfo;
	}
	/* ���³�����Ϣ */
	public String UpdatePlaneInfo(PlaneInfo planeInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update PlaneInfo set ";
			sql += "planeNumber='" + planeInfo.getPlaneNumber() + "',";
			sql += "startStation=" + planeInfo.getStartStation() + ",";
			sql += "endStation=" + planeInfo.getEndStation() + ",";
			sql += "startDate='" + planeInfo.getStartDate() + "',";
			sql += "seatType=" + planeInfo.getSeatType() + ",";
			sql += "price=" + planeInfo.getPrice() + ",";
			sql += "seatNumber=" + planeInfo.getSeatNumber() + ",";
			sql += "leftSeatNumber=" + planeInfo.getLeftSeatNumber() + ",";
			sql += "startTime='" + planeInfo.getStartTime() + "',";
			sql += "endTime='" + planeInfo.getEndTime() + "',";
			sql += "totalTime='" + planeInfo.getTotalTime() + "'";
			sql += " where seatId=" + planeInfo.getSeatId();
			db.executeUpdate(sql);
			result = "������Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
