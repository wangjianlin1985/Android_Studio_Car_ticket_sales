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

import com.mobileclient.domain.Recharge;
import com.mobileclient.handler.RechargeListHandler;
import com.mobileclient.util.HttpUtil;

/*充值信息管理业务逻辑层*/
public class RechargeService {
	/* 添加充值信息 */
	public String AddRecharge(Recharge recharge) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", recharge.getId() + "");
		params.put("userObj", recharge.getUserObj());
		params.put("money", recharge.getMoney() + ""); 
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RechargeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 查询充值信息 */
	public List<Recharge> QueryRecharge(Recharge queryConditionRecharge) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RechargeServlet?action=query";
		if(queryConditionRecharge != null) {
			urlString += "&userObj=" + URLEncoder.encode(queryConditionRecharge.getUserObj(), "UTF-8") + "";
			urlString += "&chargeTime=" + URLEncoder.encode(queryConditionRecharge.getChargeTime(), "UTF-8") + "";
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RechargeListHandler rechargeListHander = new RechargeListHandler();
		xr.setContentHandler(rechargeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Recharge> rechargeList = rechargeListHander.getRechargeList();
		return rechargeList;
	}
	/* 更新充值信息 */
	public String UpdateRecharge(Recharge recharge) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", recharge.getId() + "");
		params.put("userObj", recharge.getUserObj());
		params.put("money", recharge.getMoney() + "");
		params.put("chargeTime", recharge.getChargeTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RechargeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 删除充值信息 */
	public String DeleteRecharge(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RechargeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "充值信息信息删除失败!";
		}
	}
	/* 根据记录编号获取充值信息对象 */
	public Recharge GetRecharge(int id)  {
		List<Recharge> rechargeList = new ArrayList<Recharge>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RechargeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Recharge recharge = new Recharge();
				recharge.setId(object.getInt("id"));
				recharge.setUserObj(object.getString("userObj"));
				recharge.setMoney((float) object.getDouble("money"));
				recharge.setChargeTime(object.getString("chargeTime"));
				rechargeList.add(recharge);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = rechargeList.size();
		if(size>0) return rechargeList.get(0); 
		else return null; 
	}
}
