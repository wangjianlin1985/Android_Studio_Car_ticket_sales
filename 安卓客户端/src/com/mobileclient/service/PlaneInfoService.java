package com.mobileclient.service;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.mobileclient.domain.PlaneInfo;
import com.mobileclient.handler.PlaneInfoListHandler;
import com.mobileclient.util.HttpUtil;

/*汽车站信息管理业务逻辑层*/
public class PlaneInfoService {
	/* 添加汽车站信息 */
	public String AddPlaneInfo(PlaneInfo planeInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", planeInfo.getSeatId() + "");
		params.put("planeNumber", planeInfo.getPlaneNumber());
		params.put("startStation", planeInfo.getStartStation() + "");
		params.put("endStation", planeInfo.getEndStation() + "");
		params.put("startDate", planeInfo.getStartDate().toString());
		params.put("seatType", planeInfo.getSeatType() + "");
		params.put("price", planeInfo.getPrice() + "");
		params.put("seatNumber", planeInfo.getSeatNumber() + "");
		params.put("leftSeatNumber", planeInfo.getLeftSeatNumber() + "");
		params.put("startTime", planeInfo.getStartTime());
		params.put("endTime", planeInfo.getEndTime());
		params.put("totalTime", planeInfo.getTotalTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaneInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 查询汽车站信息 */
	public List<PlaneInfo> QueryPlaneInfo(PlaneInfo queryConditionPlaneInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PlaneInfoServlet?action=query";
		if(queryConditionPlaneInfo != null) {
			urlString += "&planeNumber=" + URLEncoder.encode(queryConditionPlaneInfo.getPlaneNumber(), "UTF-8") + "";
			urlString += "&startStation=" + queryConditionPlaneInfo.getStartStation();
			urlString += "&endStation=" + queryConditionPlaneInfo.getEndStation();
			if(queryConditionPlaneInfo.getStartDate() != null) {
				urlString += "&startDate=" + URLEncoder.encode(queryConditionPlaneInfo.getStartDate().toString(), "UTF-8");
			}
			urlString += "&seatType=" + queryConditionPlaneInfo.getSeatType();
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PlaneInfoListHandler planeInfoListHander = new PlaneInfoListHandler();
		xr.setContentHandler(planeInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<PlaneInfo> planeInfoList = planeInfoListHander.getPlaneInfoList();
		return planeInfoList;
	}
	/* 更新汽车站信息 */
	public String UpdatePlaneInfo(PlaneInfo planeInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", planeInfo.getSeatId() + "");
		params.put("planeNumber", planeInfo.getPlaneNumber());
		params.put("startStation", planeInfo.getStartStation() + "");
		params.put("endStation", planeInfo.getEndStation() + "");
		params.put("startDate", planeInfo.getStartDate().toString());
		params.put("seatType", planeInfo.getSeatType() + "");
		params.put("price", planeInfo.getPrice() + "");
		params.put("seatNumber", planeInfo.getSeatNumber() + "");
		params.put("leftSeatNumber", planeInfo.getLeftSeatNumber() + "");
		params.put("startTime", planeInfo.getStartTime());
		params.put("endTime", planeInfo.getEndTime());
		params.put("totalTime", planeInfo.getTotalTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaneInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 删除汽车站信息 */
	public String DeletePlaneInfo(int seatId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seatId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaneInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "汽车站信息信息删除失败!";
		}
	}
	/* 根据记录编号获取汽车站信息对象 */
	public PlaneInfo GetPlaneInfo(int seatId)  {
		List<PlaneInfo> planeInfoList = new ArrayList<PlaneInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seatId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PlaneInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PlaneInfo planeInfo = new PlaneInfo();
				planeInfo.setSeatId(object.getInt("seatId"));
				planeInfo.setPlaneNumber(object.getString("planeNumber"));
				planeInfo.setStartStation(object.getInt("startStation"));
				planeInfo.setEndStation(object.getInt("endStation"));
				planeInfo.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				planeInfo.setSeatType(object.getInt("seatType"));
				planeInfo.setPrice((float) object.getDouble("price"));
				planeInfo.setSeatNumber(object.getInt("seatNumber"));
				planeInfo.setLeftSeatNumber(object.getInt("leftSeatNumber"));
				planeInfo.setStartTime(object.getString("startTime"));
				planeInfo.setEndTime(object.getString("endTime"));
				planeInfo.setTotalTime(object.getString("totalTime"));
				planeInfoList.add(planeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = planeInfoList.size();
		if(size>0) return planeInfoList.get(0); 
		else return null; 
	}
}
