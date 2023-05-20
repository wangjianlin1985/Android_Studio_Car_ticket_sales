package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.util.StationInfoSimpleAdapter;
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

public class StationInfoListActivity extends Activity {
	StationInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int stationId;
	/* ����վ��Ϣ����ҵ���߼������ */
	StationInfoService stationInfoService = new StationInfoService();
	/*�����ѯ��������������վ��Ϣ����*/
	private StationInfo queryConditionStationInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stationinfo_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--����վ��Ϣ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---����վ��Ϣ�б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionStationInfo = (StationInfo)extras.getSerializable("queryConditionStationInfo");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new StationInfoSimpleAdapter(this, list,
					R.layout.stationinfo_list_item,
					new String[] { "stationName","connectPerson","telephone","postcode" },
					new int[] { R.id.tv_stationName,R.id.tv_connectPerson,R.id.tv_telephone,R.id.tv_postcode,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(stationInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int stationId = Integer.parseInt(list.get(arg2).get("stationId").toString());
            	Intent intent = new Intent();
            	intent.setClass(StationInfoListActivity.this, StationInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("stationId", stationId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener stationInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭����վ��Ϣ��Ϣ"); 
			menu.add(0, 1, 0, "ɾ������վ��Ϣ��Ϣ");
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
			stationId = Integer.parseInt(list.get(position).get("stationId").toString());
			Intent intent = new Intent();
			intent.setClass(StationInfoListActivity.this, StationInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("stationId", stationId);
			intent.putExtras(bundle);
			startActivity(intent);
			StationInfoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ������վ��Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			stationId = Integer.parseInt(list.get(position).get("stationId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(StationInfoListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = stationInfoService.DeleteStationInfo(stationId);
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
			List<StationInfo> stationInfoList = stationInfoService.QueryStationInfo(queryConditionStationInfo);
			for (int i = 0; i < stationInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("stationId",stationInfoList.get(i).getStationId());
				map.put("stationName", stationInfoList.get(i).getStationName());
				map.put("connectPerson", stationInfoList.get(i).getConnectPerson());
				map.put("telephone", stationInfoList.get(i).getTelephone());
				map.put("postcode", stationInfoList.get(i).getPostcode());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "�������վ��Ϣ");
		menu.add(0, 2, 2, "��ѯ����վ��Ϣ");
		menu.add(0, 3, 3, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// �������վ��Ϣ��Ϣ
			Intent intent = new Intent();
			intent.setClass(StationInfoListActivity.this, StationInfoAddActivity.class);
			startActivity(intent);
			StationInfoListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*��ѯ����վ��Ϣ��Ϣ*/
			Intent intent = new Intent();
			intent.setClass(StationInfoListActivity.this, StationInfoQueryActivity.class);
			startActivity(intent);
			StationInfoListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(StationInfoListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			StationInfoListActivity.this.finish();
		}
		return true; 
	}
}
