package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.PlaneInfo;
import com.mobileclient.service.PlaneInfoService;
import com.mobileclient.util.PlaneInfoSimpleAdapter;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class PlaneInfoListActivity extends Activity {
	PlaneInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int seatId;
	/* ����վ��Ϣ����ҵ���߼������ */
	PlaneInfoService planeInfoService = new PlaneInfoService();
	/*�����ѯ��������������վ��Ϣ����*/
	private PlaneInfo queryConditionPlaneInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.planeinfo_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--����վ��Ϣ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---����վ��Ϣ�б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionPlaneInfo = (PlaneInfo)extras.getSerializable("queryConditionPlaneInfo");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new PlaneInfoSimpleAdapter(this, list,
					R.layout.planeinfo_list_item,
					new String[] { "planeNumber","startStation","endStation","startDate","seatType","price","seatNumber","leftSeatNumber" },
					new int[] { R.id.tv_planeNumber,R.id.tv_startStation,R.id.tv_endStation,R.id.tv_startDate,R.id.tv_seatType,R.id.tv_price,R.id.tv_seatNumber,R.id.tv_leftSeatNumber,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(planeInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int seatId = Integer.parseInt(list.get(arg2).get("seatId").toString());
            	Intent intent = new Intent();
            	intent.setClass(PlaneInfoListActivity.this, PlaneInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("seatId", seatId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener planeInfoListItemListener = new OnCreateContextMenuListener() {
		
		
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		
			Declare declare = (Declare)PlaneInfoListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin"))  {
				menu.add(0, 0, 0, "�༭����վ��Ϣ��Ϣ"); 
				menu.add(0, 1, 0, "ɾ������վ��Ϣ��Ϣ");
			}
			
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭����վ��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			seatId = Integer.parseInt(list.get(position).get("seatId").toString());
			Intent intent = new Intent();
			intent.setClass(PlaneInfoListActivity.this, PlaneInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("seatId", seatId);
			intent.putExtras(bundle);
			startActivity(intent);
			PlaneInfoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ������վ��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			seatId = Integer.parseInt(list.get(position).get("seatId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(PlaneInfoListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = planeInfoService.DeletePlaneInfo(seatId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯ����վ��Ϣ��Ϣ */
			List<PlaneInfo> planeInfoList = planeInfoService.QueryPlaneInfo(queryConditionPlaneInfo);
			for (int i = 0; i < planeInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("seatId",planeInfoList.get(i).getSeatId());
				map.put("planeNumber", planeInfoList.get(i).getPlaneNumber());
				map.put("startStation", planeInfoList.get(i).getStartStation());
				map.put("endStation", planeInfoList.get(i).getEndStation());
				map.put("startDate", planeInfoList.get(i).getStartDate());
				map.put("seatType", planeInfoList.get(i).getSeatType());
				map.put("price", planeInfoList.get(i).getPrice());
				map.put("seatNumber", planeInfoList.get(i).getSeatNumber());
				map.put("leftSeatNumber", planeInfoList.get(i).getLeftSeatNumber());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Declare declare = (Declare)PlaneInfoListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin"))  {
			menu.add(0, 1, 1, "�������վ��Ϣ");
			menu.add(0, 2, 2, "��ѯ����վ��Ϣ");
			menu.add(0, 3, 3, "����������");
		} else {
			menu.add(0, 1, 1, "��ѯ����վ��Ϣ");
			menu.add(0, 2, 2, "����������");
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare)PlaneInfoListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin"))  {
			if (item.getItemId() == 1) {
				// �������վ��Ϣ��Ϣ
				Intent intent = new Intent();
				intent.setClass(PlaneInfoListActivity.this, PlaneInfoAddActivity.class);
				startActivity(intent);
				PlaneInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*��ѯ����վ��Ϣ��Ϣ*/
				Intent intent = new Intent();
				intent.setClass(PlaneInfoListActivity.this, PlaneInfoQueryActivity.class);
				startActivity(intent);
				PlaneInfoListActivity.this.finish();
			} else if (item.getItemId() == 3) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(PlaneInfoListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				PlaneInfoListActivity.this.finish();
			}
		} else {
			if (item.getItemId() == 1) {
				/*��ѯ����վ��Ϣ��Ϣ*/
				Intent intent = new Intent();
				intent.setClass(PlaneInfoListActivity.this, PlaneInfoQueryActivity.class);
				startActivity(intent);
				PlaneInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(PlaneInfoListActivity.this, MainMenuUserActivity.class);
				startActivity(intent);
				PlaneInfoListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
