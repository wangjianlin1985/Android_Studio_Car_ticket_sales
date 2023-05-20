package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.GuestBook;
import com.mobileclient.service.GuestBookService;
import com.mobileclient.util.GuestBookSimpleAdapter;
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

public class GuestBookListActivity extends Activity {
	GuestBookSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int guestBookId;
	/* ������Ϣ����ҵ���߼������ */
	GuestBookService guestBookService = new GuestBookService();
	/*�����ѯ����������������Ϣ����*/
	private GuestBook queryConditionGuestBook;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guestbook_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--������Ϣ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---������Ϣ�б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionGuestBook = (GuestBook)extras.getSerializable("queryConditionGuestBook");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new GuestBookSimpleAdapter(this, list,
					R.layout.guestbook_list_item,
					new String[] { "title","content","userObj","addTime" },
					new int[] { R.id.tv_title,R.id.tv_content,R.id.tv_userObj,R.id.tv_addTime,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(guestBookListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int guestBookId = Integer.parseInt(list.get(arg2).get("guestBookId").toString());
            	Intent intent = new Intent();
            	intent.setClass(GuestBookListActivity.this, GuestBookDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("guestBookId", guestBookId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener guestBookListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭������Ϣ��Ϣ"); 
			menu.add(0, 1, 0, "ɾ��������Ϣ��Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭������Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			guestBookId = Integer.parseInt(list.get(position).get("guestBookId").toString());
			Intent intent = new Intent();
			intent.setClass(GuestBookListActivity.this, GuestBookEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("guestBookId", guestBookId);
			intent.putExtras(bundle);
			startActivity(intent);
			GuestBookListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ��������Ϣ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			guestBookId = Integer.parseInt(list.get(position).get("guestBookId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(GuestBookListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = guestBookService.DeleteGuestBook(guestBookId);
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
			/* ��ѯ������Ϣ��Ϣ */
			List<GuestBook> guestBookList = guestBookService.QueryGuestBook(queryConditionGuestBook);
			for (int i = 0; i < guestBookList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("guestBookId",guestBookList.get(i).getGuestBookId());
				map.put("title", guestBookList.get(i).getTitle());
				map.put("content", guestBookList.get(i).getContent());
				map.put("userObj", guestBookList.get(i).getUserObj());
				map.put("addTime", guestBookList.get(i).getAddTime());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "���������Ϣ");
		menu.add(0, 2, 2, "��ѯ������Ϣ");
		menu.add(0, 3, 3, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// ���������Ϣ��Ϣ
			Intent intent = new Intent();
			intent.setClass(GuestBookListActivity.this, GuestBookAddActivity.class);
			startActivity(intent);
			GuestBookListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*��ѯ������Ϣ��Ϣ*/
			Intent intent = new Intent();
			intent.setClass(GuestBookListActivity.this, GuestBookQueryActivity.class);
			startActivity(intent);
			GuestBookListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(GuestBookListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			GuestBookListActivity.this.finish();
		}
		return true; 
	}
}
