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

import com.mobileclient.domain.UserInfo;
import com.mobileclient.handler.UserInfoListHandler;
import com.mobileclient.util.HttpUtil;

/*�û���Ϣ����ҵ���߼���*/
public class UserInfoService {
	/* ����û���Ϣ */
	public String AddUserInfo(UserInfo userInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", userInfo.getUser_name());
		params.put("password", userInfo.getPassword());
		params.put("realName", userInfo.getRealName());
		params.put("sex", userInfo.getSex());
		params.put("birthday", userInfo.getBirthday().toString());
		params.put("cardNumber", userInfo.getCardNumber());
		params.put("city", userInfo.getCity());
		params.put("money", userInfo.getMoney()+"");
		params.put("photo", userInfo.getPhoto());
		params.put("address", userInfo.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ�û���Ϣ */
	public List<UserInfo> QueryUserInfo(UserInfo queryConditionUserInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "UserInfoServlet?action=query";
		if(queryConditionUserInfo != null) {
			urlString += "&user_name=" + URLEncoder.encode(queryConditionUserInfo.getUser_name(), "UTF-8") + "";
			urlString += "&realName=" + URLEncoder.encode(queryConditionUserInfo.getRealName(), "UTF-8") + "";
			if(queryConditionUserInfo.getBirthday() != null) {
				urlString += "&birthday=" + URLEncoder.encode(queryConditionUserInfo.getBirthday().toString(), "UTF-8");
			}
			urlString += "&cardNumber=" + URLEncoder.encode(queryConditionUserInfo.getCardNumber(), "UTF-8") + "";
			urlString += "&city=" + URLEncoder.encode(queryConditionUserInfo.getCity(), "UTF-8") + "";
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		UserInfoListHandler userInfoListHander = new UserInfoListHandler();
		xr.setContentHandler(userInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<UserInfo> userInfoList = userInfoListHander.getUserInfoList();
		return userInfoList;
	}
	/* �����û���Ϣ */
	public String UpdateUserInfo(UserInfo userInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", userInfo.getUser_name());
		params.put("password", userInfo.getPassword());
		params.put("realName", userInfo.getRealName());
		params.put("sex", userInfo.getSex());
		params.put("birthday", userInfo.getBirthday().toString());
		params.put("cardNumber", userInfo.getCardNumber());
		params.put("city", userInfo.getCity());
		params.put("money", userInfo.getMoney()+"");
		params.put("photo", userInfo.getPhoto());
		params.put("address", userInfo.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ���û���Ϣ */
	public String DeleteUserInfo(String user_name) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", user_name);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�û���Ϣ��Ϣɾ��ʧ��!";
		}
	}
	/* �����û�����ȡ�û���Ϣ���� */
	public UserInfo GetUserInfo(String user_name)  {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", user_name);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				UserInfo userInfo = new UserInfo();
				userInfo.setPassword(object.getString("password"));
				userInfo.setUser_name(object.getString("user_name")); 
				userInfo.setRealName(object.getString("realName"));
				userInfo.setSex(object.getString("sex"));
				userInfo.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				userInfo.setCardNumber(object.getString("cardNumber"));
				userInfo.setCity(object.getString("city"));
				userInfo.setMoney((float)object.getDouble("money"));
				userInfo.setPhoto(object.getString("photo"));
				userInfo.setAddress(object.getString("address"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = userInfoList.size();
		if(size>0) return userInfoList.get(0); 
		else return null; 
	}
}
