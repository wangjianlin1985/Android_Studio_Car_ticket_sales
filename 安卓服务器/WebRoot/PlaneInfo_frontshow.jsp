<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.PlaneInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.SeatType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的startStation信息
    List<StationInfo> stationInfoList = (List<StationInfo>)request.getAttribute("stationInfoList");
   
    //获取所有的seatType信息
    List<SeatType> seatTypeList = (List<SeatType>)request.getAttribute("seatTypeList");
    PlaneInfo planeInfo = (PlaneInfo)request.getAttribute("planeInfo");

%>
<HTML><HEAD><TITLE>查看车次信息</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><%=planeInfo.getSeatId() %></td>
  </tr>

  <tr>
    <td width=30%>车次:</td>
    <td width=70%><%=planeInfo.getPlaneNumber() %></td>
  </tr>

  <tr>
    <td width=30%>始发汽车站:</td>
    <td width=70%>
      <select name="planeInfo.startStation.stationId" disabled>
      <%
        for(StationInfo stationInfo:stationInfoList) {
          String selected = "";
          if(stationInfo.getStationId() == planeInfo.getStartStation().getStationId())
            selected = "selected";
      %>
          <option value='<%=stationInfo.getStationId() %>' <%=selected %>><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>终到汽车站:</td>
    <td width=70%>
      <select name="planeInfo.startStation.stationId" disabled>
      <%
        for(StationInfo stationInfo:stationInfoList) {
          String selected = "";
          if(stationInfo.getStationId() == planeInfo.getStartStation().getStationId())
            selected = "selected";
      %>
          <option value='<%=stationInfo.getStationId() %>' <%=selected %>><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>车次日期:</td>
        <% java.text.DateFormat startDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=startDateSDF.format(planeInfo.getStartDate()) %></td>
  </tr>

  <tr>
    <td width=30%>席别:</td>
    <td width=70%>
      <select name="planeInfo.seatType.seatTypeId" disabled>
      <%
        for(SeatType seatType:seatTypeList) {
          String selected = "";
          if(seatType.getSeatTypeId() == planeInfo.getSeatType().getSeatTypeId())
            selected = "selected";
      %>
          <option value='<%=seatType.getSeatTypeId() %>' <%=selected %>><%=seatType.getSeatTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>票价:</td>
    <td width=70%><%=planeInfo.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>总座位数:</td>
    <td width=70%><%=planeInfo.getSeatNumber() %></td>
  </tr>

  <tr>
    <td width=30%>剩余座位数:</td>
    <td width=70%><%=planeInfo.getLeftSeatNumber() %></td>
  </tr>

  <tr>
    <td width=30%>发车时间:</td>
    <td width=70%><%=planeInfo.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>终到时间:</td>
    <td width=70%><%=planeInfo.getEndTime() %></td>
  </tr>

  <tr>
    <td width=30%>历时:</td>
    <td width=70%><%=planeInfo.getTotalTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
