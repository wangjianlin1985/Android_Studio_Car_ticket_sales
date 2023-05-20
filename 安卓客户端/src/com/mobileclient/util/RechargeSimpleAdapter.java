package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.UserInfoService;
import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class RechargeSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public RechargeSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.recharge_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
				holder.tv_money = (TextView)convertView.findViewById(R.id.tv_money);
				holder.tv_chargeTime = (TextView)convertView.findViewById(R.id.tv_chargeTime);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_userObj.setText("��ֵ�û���" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getRealName());
		holder.tv_money.setText("��ֵ��" + mData.get(position).get("money").toString());
		holder.tv_chargeTime.setText("��ֵʱ�䣺" + mData.get(position).get("chargeTime").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_userObj;
    	TextView tv_money;
    	TextView tv_chargeTime;
    }
} 
