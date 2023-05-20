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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
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
    /*第一次装载这个view时=null,就新建一个调用inflate宣誓一个view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.stationinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*绑定该view各个控件*/
				holder.tv_stationName = (TextView)convertView.findViewById(R.id.tv_stationName);
				holder.tv_connectPerson = (TextView)convertView.findViewById(R.id.tv_connectPerson);
				holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
				holder.tv_postcode = (TextView)convertView.findViewById(R.id.tv_postcode);
			} catch(Exception ex) {}
			/*标记这个view*/
			convertView.setTag(holder);
		}else{
			/*直接取出标记的view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*设置各个控件的展示内容*/
		holder.tv_stationName.setText("汽车站名称：" + mData.get(position).get("stationName").toString());
		holder.tv_connectPerson.setText("联系人：" + mData.get(position).get("connectPerson").toString());
		holder.tv_telephone.setText("联系电话：" + mData.get(position).get("telephone").toString());
		holder.tv_postcode.setText("邮编：" + mData.get(position).get("postcode").toString());
		/*返回修改好的view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_stationName;
    	TextView tv_connectPerson;
    	TextView tv_telephone;
    	TextView tv_postcode;
    }
} 
