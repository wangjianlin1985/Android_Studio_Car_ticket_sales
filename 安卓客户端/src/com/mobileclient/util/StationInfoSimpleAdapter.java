package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class StationInfoSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public StationInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.stationinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_stationName = (TextView)convertView.findViewById(R.id.tv_stationName);
				holder.tv_connectPerson = (TextView)convertView.findViewById(R.id.tv_connectPerson);
				holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
				holder.tv_postcode = (TextView)convertView.findViewById(R.id.tv_postcode);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_stationName.setText("����վ���ƣ�" + mData.get(position).get("stationName").toString());
		holder.tv_connectPerson.setText("��ϵ�ˣ�" + mData.get(position).get("connectPerson").toString());
		holder.tv_telephone.setText("��ϵ�绰��" + mData.get(position).get("telephone").toString());
		holder.tv_postcode.setText("�ʱࣺ" + mData.get(position).get("postcode").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_stationName;
    	TextView tv_connectPerson;
    	TextView tv_telephone;
    	TextView tv_postcode;
    }
} 
