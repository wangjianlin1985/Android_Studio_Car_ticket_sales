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

import com.mobileclient.domain.StationInfo;
import com.mobileclient.handler.StationInfoListHandler;
import com.mobileclient.util.HttpUtil;

/*汽车站信息管理业务逻辑层*/
public class StationInfoService {
	/* 添加汽车站信息 */
	public String AddStationInfo(StationInfo stationInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stationId", stationInfo.getStationId() + "");
		params.put("stationName", stationInfo.getStationName());
		params.put("connectPerson", stationInfo.getConnectPerson());
		params.put("telephone", stationInfo.getTelephone());
		params.put("postcode", stationInfo.getPostcode());
		params.put("address", stationInfo.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StationInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 查询汽车站信息 */
	public List<StationInfo> QueryStationInfo(StationInfo queryConditionStationInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "StationInfoServlet?action=query";
		if(queryConditionStationInfo != null) {
			urlString += "&stationName=" + URLEncoder.encode(queryConditionStationInfo.getStationName(), "UTF-8") + "";
			urlString += "&connectPerson=" + URLEncoder.encode(queryConditionStationInfo.getConnectPerson(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionStationInfo.getTelephone(), "UTF-8") + "";
			urlString += "&postcode=" + URLEncoder.encode(queryConditionStationInfo.getPostcode(), "UTF-8") + "";
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		StationInfoListHandler stationInfoListHander = new StationInfoListHandler();
		xr.setContentHandler(stationInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<StationInfo> stationInfoList = stationInfoListHander.getStationInfoList();
		return stationInfoList;
	}
	/* 更新汽车站信息 */
	public String UpdateStationInfo(StationInfo stationInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stationId", stationInfo.getStationId() + "");
		params.put("stationName", stationInfo.getStationName());
		params.put("connectPerson", stationInfo.getConnectPerson());
		params.put("telephone", stationInfo.getTelephone());
		params.put("postcode", stationInfo.getPostcode());
		params.put("address", stationInfo.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StationInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 删除汽车站信息 */
	public String DeleteStationInfo(int stationId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stationId", stationId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StationInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "汽车站信息信息删除失败!";
		}
	}
	/* 根据记录编号获取汽车站信息对象 */
	public StationInfo GetStationInfo(int stationId)  {
		List<StationInfo> stationInfoList = new ArrayList<StationInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stationId", stationId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StationInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				StationInfo stationInfo = new StationInfo();
				stationInfo.setStationId(object.getInt("stationId"));
				stationInfo.setStationName(object.getString("stationName"));
				stationInfo.setConnectPerson(object.getString("connectPerson"));
				stationInfo.setTelephone(object.getString("telephone"));
				stationInfo.setPostcode(object.getString("postcode"));
				stationInfo.setAddress(object.getString("address"));
				stationInfoList.add(stationInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = stationInfoList.size();
		if(size>0) return stationInfoList.get(0); 
		else return null; 
	}
}
