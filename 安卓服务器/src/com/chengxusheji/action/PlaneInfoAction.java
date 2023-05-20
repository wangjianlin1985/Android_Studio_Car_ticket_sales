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

    /*�������Ҫ��ѯ������: ����*/
    private String planeNumber;
    public void setPlaneNumber(String planeNumber) {
        this.planeNumber = planeNumber;
    }
    public String getPlaneNumber() {
        return this.planeNumber;
    }

    /*�������Ҫ��ѯ������: ʼ������վ*/
    private StationInfo startStation;
    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }
    public StationInfo getStartStation() {
        return this.startStation;
    }

    /*�������Ҫ��ѯ������: �յ�����վ*/
    private StationInfo endStation;
    public void setEndStation(StationInfo endStation) {
        this.endStation = endStation;
    }
    public StationInfo getEndStation() {
        return this.endStation;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String startDate;
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return this.startDate;
    }

    /*�������Ҫ��ѯ������: ϯ��*/
    private SeatType seatType;
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    public SeatType getSeatType() {
        return this.seatType;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource StationInfoDAO stationInfoDAO;
 
    @Resource SeatTypeDAO seatTypeDAO;
    @Resource PlaneInfoDAO planeInfoDAO;

    /*��������PlaneInfo����*/
    private PlaneInfo planeInfo;
    public void setPlaneInfo(PlaneInfo planeInfo) {
        this.planeInfo = planeInfo;
    }
    public PlaneInfo getPlaneInfo() {
        return this.planeInfo;
    }

    /*��ת�����PlaneInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�StationInfo��Ϣ*/
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
       
        /*��ѯ���е�SeatType��Ϣ*/
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "add_view";
    }

    /*���PlaneInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("PlaneInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaneInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPlaneInfo��Ϣ*/
    public String QueryPlaneInfo() {
        if(currentPage == 0) currentPage = 1;
        if(planeNumber == null) planeNumber = "";
        if(startDate == null) startDate = "";
        List<PlaneInfo> planeInfoList = planeInfoDAO.QueryPlaneInfoInfo(planeNumber, startStation, endStation, startDate, seatType, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        planeInfoDAO.CalculateTotalPageAndRecordNumber(planeNumber, startStation, endStation, startDate, seatType);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = planeInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryPlaneInfoOutputToExcel() { 
        if(planeNumber == null) planeNumber = "";
        if(startDate == null) startDate = "";
        List<PlaneInfo> planeInfoList = planeInfoDAO.QueryPlaneInfoInfo(planeNumber,startStation,endStation,startDate,seatType);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PlaneInfo��Ϣ��¼"; 
        String[] headers = { "����","ʼ������վ","�յ�����վ","��������","ϯ��","Ʊ��","����λ��","ʣ����λ��","����ʱ��","�յ�ʱ��","��ʱ"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PlaneInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯPlaneInfo��Ϣ*/
    public String FrontQueryPlaneInfo() {
        if(currentPage == 0) currentPage = 1;
        if(planeNumber == null) planeNumber = "";
        if(startDate == null) startDate = "";
        List<PlaneInfo> planeInfoList = planeInfoDAO.QueryPlaneInfoInfo(planeNumber, startStation, endStation, startDate, seatType, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        planeInfoDAO.CalculateTotalPageAndRecordNumber(planeNumber, startStation, endStation, startDate, seatType);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = planeInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�PlaneInfo��Ϣ*/
    public String ModifyPlaneInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������seatId��ȡPlaneInfo����*/
        PlaneInfo planeInfo = planeInfoDAO.GetPlaneInfoBySeatId(seatId);

        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
 
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("planeInfo",  planeInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�PlaneInfo��Ϣ*/
    public String FrontShowPlaneInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������seatId��ȡPlaneInfo����*/
        PlaneInfo planeInfo = planeInfoDAO.GetPlaneInfoBySeatId(seatId);

        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
       
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("planeInfo",  planeInfo);
        return "front_show_view";
    }

    /*�����޸�PlaneInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("PlaneInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaneInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��PlaneInfo��Ϣ*/
    public String DeletePlaneInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            planeInfoDAO.DeletePlaneInfo(seatId);
            ctx.put("message",  java.net.URLEncoder.encode("PlaneInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PlaneInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
