package com.chengxusheji.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.chengxusheji.dao.PlaneInfoDAO;
import com.chengxusheji.domain.PlaneInfo;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.dao.SeatTypeDAO;
import com.chengxusheji.domain.SeatType;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PlaneInfoAction extends ActionSupport {

    /*界面层需要查询的属性: 车次*/
    private String planeNumber;
    public void setPlaneNumber(String planeNumber) {
        this.planeNumber = planeNumber;
    }
    public String getPlaneNumber() {
        return this.planeNumber;
    }

    /*界面层需要查询的属性: 始发汽车站*/
    private StationInfo startStation;
    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }
    public StationInfo getStartStation() {
        return this.startStation;
    }

    /*界面层需要查询的属性: 终到汽车站*/
    private StationInfo endStation;
    public void setEndStation(StationInfo endStation) {
        this.endStation = endStation;
    }
    public StationInfo getEndStation() {
        return this.endStation;
    }

    /*界面层需要查询的属性: 车次日期*/
    private String startDate;
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return this.startDate;
    }

    /*界面层需要查询的属性: 席别*/
    private SeatType seatType;
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    public SeatType getSeatType() {
        return this.seatType;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int seatId;
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    public int getSeatId() {
        return seatId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource StationInfoDAO stationInfoDAO;
 
    @Resource SeatTypeDAO seatTypeDAO;
    @Resource PlaneInfoDAO planeInfoDAO;

    /*待操作的PlaneInfo对象*/
    private PlaneInfo planeInfo;
    public void setPlaneInfo(PlaneInfo planeInfo) {
        this.planeInfo = planeInfo;
    }
    public PlaneInfo getPlaneInfo() {
        return this.planeInfo;
    }

    /*跳转到添加PlaneInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的StationInfo信息*/
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
       
        /*查询所有的SeatType信息*/
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "add_view";
    }

    /*添加PlaneInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddPlaneInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            StationInfo startStation = stationInfoDAO.GetStationInfoByStationId(planeInfo.getStartStation().getStationId());
            planeInfo.setStartStation(startStation);
            }
            if(true) {
            StationInfo endStation = stationInfoDAO.GetStationInfoByStationId(planeInfo.getEndStation().getStationId());
            planeInfo.setEndStation(endStation);
            }
            if(true) {
            SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(planeInfo.getSeatType().getSeatTypeId());
            planeInfo.setSeatType(seatType);
            }
            planeInfoDAO.AddPlaneInfo(planeInfo);
            ctx.put("message",  java.net.URLEncoder.encode("PlaneInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaneInfo添加失败!"));
            return "error";
        }
    }

    /*查询PlaneInfo信息*/
    public String QueryPlaneInfo() {
        if(currentPage == 0) currentPage = 1;
        if(planeNumber == null) planeNumber = "";
        if(startDate == null) startDate = "";
        List<PlaneInfo> planeInfoList = planeInfoDAO.QueryPlaneInfoInfo(planeNumber, startStation, endStation, startDate, seatType, currentPage);
        /*计算总的页数和总的记录数*/
        planeInfoDAO.CalculateTotalPageAndRecordNumber(planeNumber, startStation, endStation, startDate, seatType);
        /*获取到总的页码数目*/
        totalPage = planeInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = planeInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("planeInfoList",  planeInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("planeNumber", planeNumber);
        ctx.put("startStation", startStation);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        ctx.put("endStation", endStation);
        
        ctx.put("startDate", startDate);
        ctx.put("seatType", seatType);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryPlaneInfoOutputToExcel() { 
        if(planeNumber == null) planeNumber = "";
        if(startDate == null) startDate = "";
        List<PlaneInfo> planeInfoList = planeInfoDAO.QueryPlaneInfoInfo(planeNumber,startStation,endStation,startDate,seatType);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PlaneInfo信息记录"; 
        String[] headers = { "车次","始发汽车站","终到汽车站","车次日期","席别","票价","总座位数","剩余座位数","发车时间","终到时间","历时"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<planeInfoList.size();i++) {
        	PlaneInfo planeInfo = planeInfoList.get(i); 
        	dataset.add(new String[]{planeInfo.getPlaneNumber(),planeInfo.getStartStation().getStationName(),
planeInfo.getEndStation().getStationName(),
new SimpleDateFormat("yyyy-MM-dd").format(planeInfo.getStartDate()),planeInfo.getSeatType().getSeatTypeName(),
planeInfo.getPrice() + "",planeInfo.getSeatNumber() + "",planeInfo.getLeftSeatNumber() + "",planeInfo.getStartTime(),planeInfo.getEndTime(),planeInfo.getTotalTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PlaneInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询PlaneInfo信息*/
    public String FrontQueryPlaneInfo() {
        if(currentPage == 0) currentPage = 1;
        if(planeNumber == null) planeNumber = "";
        if(startDate == null) startDate = "";
        List<PlaneInfo> planeInfoList = planeInfoDAO.QueryPlaneInfoInfo(planeNumber, startStation, endStation, startDate, seatType, currentPage);
        /*计算总的页数和总的记录数*/
        planeInfoDAO.CalculateTotalPageAndRecordNumber(planeNumber, startStation, endStation, startDate, seatType);
        /*获取到总的页码数目*/
        totalPage = planeInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = planeInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("planeInfoList",  planeInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("planeNumber", planeNumber);
        ctx.put("startStation", startStation);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        ctx.put("endStation", endStation);
        
        ctx.put("startDate", startDate);
        ctx.put("seatType", seatType);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "front_query_view";
    }

    /*查询要修改的PlaneInfo信息*/
    public String ModifyPlaneInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键seatId获取PlaneInfo对象*/
        PlaneInfo planeInfo = planeInfoDAO.GetPlaneInfoBySeatId(seatId);

        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
 
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("planeInfo",  planeInfo);
        return "modify_view";
    }

    /*查询要修改的PlaneInfo信息*/
    public String FrontShowPlaneInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键seatId获取PlaneInfo对象*/
        PlaneInfo planeInfo = planeInfoDAO.GetPlaneInfoBySeatId(seatId);

        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
       
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("planeInfo",  planeInfo);
        return "front_show_view";
    }

    /*更新修改PlaneInfo信息*/
    public String ModifyPlaneInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            StationInfo startStation = stationInfoDAO.GetStationInfoByStationId(planeInfo.getStartStation().getStationId());
            planeInfo.setStartStation(startStation);
            }
            if(true) {
            StationInfo endStation = stationInfoDAO.GetStationInfoByStationId(planeInfo.getEndStation().getStationId());
            planeInfo.setEndStation(endStation);
            }
            if(true) {
            SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(planeInfo.getSeatType().getSeatTypeId());
            planeInfo.setSeatType(seatType);
            }
            planeInfoDAO.UpdatePlaneInfo(planeInfo);
            ctx.put("message",  java.net.URLEncoder.encode("PlaneInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaneInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除PlaneInfo信息*/
    public String DeletePlaneInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            planeInfoDAO.DeletePlaneInfo(seatId);
            ctx.put("message",  java.net.URLEncoder.encode("PlaneInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaneInfo删除失败!"));
            return "error";
        }
    }

}
