package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.domain.SeatType;
import com.chengxusheji.domain.PlaneInfo;

@Service @Transactional
public class PlaneInfoDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddPlaneInfo(PlaneInfo planeInfo) throws Exception {
    	Session s = factory.getCurrentSession();
        s.merge(planeInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PlaneInfo> QueryPlaneInfoInfo(String planeNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PlaneInfo planeInfo where 1=1";
    	if(!planeNumber.equals("")) hql = hql + " and planeInfo.planeNumber like '%" + planeNumber + "%'";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and planeInfo.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and planeInfo.endStation.stationId=" + endStation.getStationId();
    	if(!startDate.equals("")) hql = hql + " and planeInfo.startDate like '%" + startDate + "%'";
    	if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and planeInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List planeInfoList = q.list();
    	return (ArrayList<PlaneInfo>) planeInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PlaneInfo> QueryPlaneInfoInfo(String planeNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PlaneInfo planeInfo where 1=1";
    	if(!planeNumber.equals("")) hql = hql + " and planeInfo.planeNumber like '%" + planeNumber + "%'";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and planeInfo.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and planeInfo.endStation.stationId=" + endStation.getStationId();
    	if(!startDate.equals("")) hql = hql + " and planeInfo.startDate like '%" + startDate + "%'";
    	if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and planeInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
    	Query q = s.createQuery(hql);
    	List planeInfoList = q.list();
    	return (ArrayList<PlaneInfo>) planeInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PlaneInfo> QueryAllPlaneInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From PlaneInfo";
        Query q = s.createQuery(hql);
        List planeInfoList = q.list();
        return (ArrayList<PlaneInfo>) planeInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String planeNumber,StationInfo startStation,StationInfo endStation,String startDate,SeatType seatType) {
        Session s = factory.getCurrentSession();
        String hql = "From PlaneInfo planeInfo where 1=1";
        if(!planeNumber.equals("")) hql = hql + " and planeInfo.planeNumber like '%" + planeNumber + "%'";
        if(null != startStation && startStation.getStationId()!=0) hql += " and planeInfo.startStation.stationId=" + startStation.getStationId();
        if(null != endStation && endStation.getStationId()!=0) hql += " and planeInfo.endStation.stationId=" + endStation.getStationId();
        if(!startDate.equals("")) hql = hql + " and planeInfo.startDate like '%" + startDate + "%'";
        if(null != seatType && seatType.getSeatTypeId()!=0) hql += " and planeInfo.seatType.seatTypeId=" + seatType.getSeatTypeId();
        Query q = s.createQuery(hql);
        List planeInfoList = q.list();
        recordNumber = planeInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PlaneInfo GetPlaneInfoBySeatId(int seatId) {
        Session s = factory.getCurrentSession();
        PlaneInfo planeInfo = (PlaneInfo)s.get(PlaneInfo.class, seatId);
        return planeInfo;
    }

    /*更新PlaneInfo信息*/
    public void UpdatePlaneInfo(PlaneInfo planeInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(planeInfo);
    }

    /*删除PlaneInfo信息*/
    public void DeletePlaneInfo (int seatId) throws Exception {
        Session s = factory.getCurrentSession();
        Object planeInfo = s.load(PlaneInfo.class, seatId);
        s.delete(planeInfo);
    }

}
