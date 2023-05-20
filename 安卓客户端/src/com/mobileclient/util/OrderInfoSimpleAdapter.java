package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.PlaneInfoService;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.service.SeatTypeService;
import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class OrderInfoSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public OrderInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
    ViewHolder holder = null; 
    /*��һ��װ�����viewʱ=null,���½�һ������inflate����һ��view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.orderinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
				holder.tv_planeNumber = (TextView)convertView.findViewById(R.id.tv_planeNumber);
				holder.tv_startStation = (TextView)convertView.findViewById(R.id.tv_startStation);
				holder.tv_endStation = (TextView)convertView.findViewById(R.id.tv_endStation);
				holder.tv_startDate = (TextView)convertView.findViewById(R.id.tv_startDate);
				holder.tv_seatType = (TextView)convertView.findViewById(R.id.tv_seatType);
				holder.tv_seatInfo = (TextView)convertView.findViewById(R.id.tv_seatInfo);
				holder.tv_totalPrice = (TextView)convertView.findViewById(R.id.tv_totalPrice);
				holder.tv_startTime = (TextView)convertView.findViewById(R.id.tv_startTime);
				holder.tv_endTime = (TextView)convertView.findViewById(R.id.tv_endTime);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_userObj.setText("�û���" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getRealName());
		holder.tv_planeNumber.setText("����վ��" + mData.get(position).get("planeNumber").toString());
		holder.tv_startStation.setText("ʼ������վ��" + (new StationInfoService()).GetStationInfo(Integer.parseInt(mData.get(position).get("startStation").toString())).getStationName());
		holder.tv_endStation.setText("�յ�����վ��" + (new StationInfoService()).GetStationInfo(Integer.parseInt(mData.get(position).get("endStation").toString())).getStationName());
		holder.tv_startDate.setText("����վ���ڣ�" + mData.get(position).get("startDate").toString().substring(0, 10));
		holder.tv_seatType.setText("ϯ��" + (new SeatTypeService()).GetSeatType(Integer.parseInt(mData.get(position).get("seatType").toString())).getSeatTypeName());
		holder.tv_seatInfo.setText("��λ��Ϣ��" + mData.get(position).get("seatInfo").toString());
		holder.tv_totalPrice.setText("��Ʊ�ۣ�" + mData.get(position).get("totalPrice").toString());
		holder.tv_startTime.setText("����ʱ�䣺" + mData.get(position).get("startTime").toString());
		holder.tv_endTime.setText("�յ�ʱ�䣺" + mData.get(position).get("endTime").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_userObj;
    	TextView tv_planeNumber;
    	TextView tv_startStation;
    	TextView tv_endStation;
    	TextView tv_startDate;
    	TextView tv_seatType;
    	TextView tv_seatInfo;
    	TextView tv_totalPrice;
    	TextView tv_startTime;
    	TextView tv_endTime;
    }
} 
