package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.UserInfoService;
import com.mobileclient.activity.R;
import com.mobileclient.app.Declare;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

import android.app.Activity;

public class GuestBookSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public GuestBookSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.guestbook_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
				holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
				holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_title.setText("���Ա��⣺" + mData.get(position).get("title").toString());
		holder.tv_content.setText("�������ݣ�" + mData.get(position).get("content").toString());
		holder.tv_userObj.setText("�����ˣ�" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getRealName());
		holder.tv_addTime.setText("����ʱ�䣺" + mData.get(position).get("addTime").toString());
		/*�����޸ĺõ�view*/
		Declare declare = (Declare)((Activity)this.context).getApplication();
		if(declare.getIdentify().equals("user"))
			holder.tv_userObj.setVisibility(View.GONE); 
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_title;
    	TextView tv_content;
    	TextView tv_userObj;
    	TextView tv_addTime;
    }
} 
