<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.SeatType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�startStation��Ϣ
    List<StationInfo> stationInfoList = (List<StationInfo>)request.getAttribute("stationInfoList");
  
    //��ȡ���е�seatType��Ϣ
    List<SeatType> seatTypeList = (List<SeatType>)request.getAttribute("seatTypeList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӳ�����Ϣ</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var planeNumber = document.getElementById("planeInfo.planeNumber").value;
    if(planeNumber=="") {
        alert('�����복��!');
        return false;
    }
    var startTime = document.getElementById("planeInfo.startTime").value;
    if(startTime=="") {
        alert('�����뷢��ʱ��!');
        return false;
    }
    var endTime = document.getElementById("planeInfo.endTime").value;
    if(endTime=="") {
        alert('�������յ�ʱ��!');
        return false;
    }
    var totalTime = document.getElementById("planeInfo.totalTime").value;
    if(totalTime=="") {
        alert('��������ʱ!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="PlaneInfo/PlaneInfo_AddPlaneInfo.action" method="post" id="planeInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="planeInfo.planeNumber" name="planeInfo.planeNumber" type="text" size="0" /></td>
  </tr>

  <tr>
    <td width=30%>ʼ������վ:</td>
    <td width=70%>
      <select name="planeInfo.startStation.stationId">
      <%
        for(StationInfo stationInfo:stationInfoList) {
      %>
          <option value='<%=stationInfo.getStationId() %>'><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�յ�����վ:</td>
    <td width=70%>
      <select name="planeInfo.endStation.stationId">
      <%
        for(StationInfo stationInfo:stationInfoList) {
      %>
          <option value='<%=stationInfo.getStationId() %>'><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="planeInfo.startDate"  name="planeInfo.startDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>ϯ��:</td>
    <td width=70%>
      <select name="planeInfo.seatType.seatTypeId">
      <%
        for(SeatType seatType:seatTypeList) {
      %>
          <option value='<%=seatType.getSeatTypeId() %>'><%=seatType.getSeatTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>Ʊ��:</td>
    <td width=70%><input id="planeInfo.price" name="planeInfo.price" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>����λ��:</td>
    <td width=70%><input id="planeInfo.seatNumber" name="planeInfo.seatNumber" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>ʣ����λ��:</td>
    <td width=70%><input id="planeInfo.leftSeatNumber" name="planeInfo.leftSeatNumber" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="planeInfo.startTime" name="planeInfo.startTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�յ�ʱ��:</td>
    <td width=70%><input id="planeInfo.endTime" name="planeInfo.endTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ʱ:</td>
    <td width=70%><input id="planeInfo.totalTime" name="planeInfo.totalTime" type="text" size="20" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
