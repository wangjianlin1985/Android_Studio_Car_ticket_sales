package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.SeatType;
import com.mobileclient.service.SeatTypeService;
import com.mobileclient.util.SeatTypeSimpleAdapter;
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

public class SeatTypeListActivity extends Activity {
	SeatTypeSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int seatTypeId;
	/* ��λϯ�����ҵ���߼������ */
	SeatTypeService seatTypeService = new SeatTypeService();
	/*�����ѯ������������λϯ�����*/
	private SeatType queryConditionSeatType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seattype_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--��λϯ���б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---��λϯ���б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionSeatType = (SeatType)extras.getSerializable("queryConditionSeatType");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new SeatTypeSimpleAdapter(this, list,
					R.layout.seattype_list_item,
					new String[] { "seatTypeId","seatTypeName" },
					new int[] { R.id.tv_seatTypeId,R.id.tv_seatTypeName,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(seatTypeListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int seatTypeId = Integer.parseInt(list.get(arg2).get("seatTypeId").toString());
            	Intent intent = new Intent();
            	intent.setClass(SeatTypeListActivity.this, SeatTypeDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("seatTypeId", seatTypeId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener seatTypeListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭��λϯ����Ϣ"); 
			menu.add(0, 1, 0, "ɾ����λϯ����Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭��λϯ����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			seatTypeId = Integer.parseInt(list.get(position).get("seatTypeId").toString());
			Intent intent = new Intent();
			intent.setClass(SeatTypeListActivity.this, SeatTypeEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("seatTypeId", seatTypeId);
			intent.putExtras(bundle);
			startActivity(intent);
			SeatTypeListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ����λϯ����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			seatTypeId = Integer.parseInt(list.get(position).get("seatTypeId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(SeatTypeListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = seatTypeService.DeleteSeatType(seatTypeId);
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
			/* ��ѯ��λϯ����Ϣ */
			List<SeatType> seatTypeList = seatTypeService.QuerySeatType(queryConditionSeatType);
			for (int i = 0; i < seatTypeList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("seatTypeId", seatTypeList.get(i).getSeatTypeId());
				map.put("seatTypeName", seatTypeList.get(i).getSeatTypeName());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "�����λϯ��");
		menu.add(0, 2, 2, "��ѯ��λϯ��");
		menu.add(0, 3, 3, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// �����λϯ����Ϣ
			Intent intent = new Intent();
			intent.setClass(SeatTypeListActivity.this, SeatTypeAddActivity.class);
			startActivity(intent);
			SeatTypeListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*��ѯ��λϯ����Ϣ*/
			Intent intent = new Intent();
			intent.setClass(SeatTypeListActivity.this, SeatTypeQueryActivity.class);
			startActivity(intent);
			SeatTypeListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(SeatTypeListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			SeatTypeListActivity.this.finish();
		}
		return true; 
	}
}
