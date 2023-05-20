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

import com.mobileclient.domain.SeatType;
import com.mobileclient.handler.SeatTypeListHandler;
import com.mobileclient.util.HttpUtil;

/*座位席别管理业务逻辑层*/
public class SeatTypeService {
	/* 添加座位席别 */
	public String AddSeatType(SeatType seatType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatTypeId", seatType.getSeatTypeId() + "");
		params.put("seatTypeName", seatType.getSeatTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 查询座位席别 */
	public List<SeatType> QuerySeatType(SeatType queryConditionSeatType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SeatTypeServlet?action=query";
		if(queryConditionSeatType != null) {
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SeatTypeListHandler seatTypeListHander = new SeatTypeListHandler();
		xr.setContentHandler(seatTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SeatType> seatTypeList = seatTypeListHander.getSeatTypeList();
		return seatTypeList;
	}
	/* 更新座位席别 */
	public String UpdateSeatType(SeatType seatType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatTypeId", seatType.getSeatTypeId() + "");
		params.put("seatTypeName", seatType.getSeatTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 删除座位席别 */
	public String DeleteSeatType(int seatTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatTypeId", seatTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "座位席别信息删除失败!";
		}
	}
	/* 根据记录编号获取座位席别对象 */
	public SeatType GetSeatType(int seatTypeId)  {
		List<SeatType> seatTypeList = new ArrayList<SeatType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatTypeId", seatTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SeatType seatType = new SeatType();
				seatType.setSeatTypeId(object.getInt("seatTypeId"));
				seatType.setSeatTypeName(object.getString("seatTypeName"));
				seatTypeList.add(seatType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = seatTypeList.size();
		if(size>0) return seatTypeList.get(0); 
		else return null; 
	}
}
