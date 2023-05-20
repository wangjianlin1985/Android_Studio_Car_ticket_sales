package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.NewsInfo;
import com.mobileclient.service.NewsInfoService;
import com.mobileclient.util.NewsInfoSimpleAdapter;
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

public class NewsInfoListActivity extends Activity {
	NewsInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int newsId;
	/* ���Ź������ҵ���߼������ */
	NewsInfoService newsInfoService = new NewsInfoService();
	/*�����ѯ�������������Ź������*/
	private NewsInfo queryConditionNewsInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsinfo_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--���Ź����б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---���Ź����б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionNewsInfo = (NewsInfo)extras.getSerializable("queryConditionNewsInfo");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new NewsInfoSimpleAdapter(this, list,
					R.layout.newsinfo_list_item,
					new String[] { "newsTitle","newsContent","newsDate" },
					new int[] { R.id.tv_newsTitle,R.id.tv_newsContent,R.id.tv_newsDate,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(newsInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int newsId = Integer.parseInt(list.get(arg2).get("newsId").toString());
            	Intent intent = new Intent();
            	intent.setClass(NewsInfoListActivity.this, NewsInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("newsId", newsId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener newsInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare)getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "�༭���Ź�����Ϣ"); 
				menu.add(0, 1, 0, "ɾ�����Ź�����Ϣ");
			}
			
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭���Ź�����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			newsId = Integer.parseInt(list.get(position).get("newsId").toString());
			Intent intent = new Intent();
			intent.setClass(NewsInfoListActivity.this, NewsInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("newsId", newsId);
			intent.putExtras(bundle);
			startActivity(intent);
			NewsInfoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ�����Ź�����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼���
			newsId = Integer.parseInt(list.get(position).get("newsId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(NewsInfoListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = newsInfoService.DeleteNewsInfo(newsId);
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
			/* ��ѯ���Ź�����Ϣ */
			List<NewsInfo> newsInfoList = newsInfoService.QueryNewsInfo(queryConditionNewsInfo);
			for (int i = 0; i < newsInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("newsId",newsInfoList.get(i).getNewsId());
				map.put("newsTitle", newsInfoList.get(i).getNewsTitle());
				map.put("newsContent", newsInfoList.get(i).getNewsContent());
				map.put("newsDate", newsInfoList.get(i).getNewsDate());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Declare declare = (Declare)getApplication();
		if(declare.getIdentify().equals("admin")) {
			menu.add(0, 1, 1, "������Ź���");
			menu.add(0, 2, 2, "��ѯ���Ź���");
			menu.add(0, 3, 3, "����������");
		} else { 
			menu.add(0, 1, 1, "��ѯ���Ź���");
			menu.add(0, 2, 2, "����������");
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare)getApplication();
		if(declare.getIdentify().equals("admin")) {
			if (item.getItemId() == 1) {
				// ������Ź�����Ϣ
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, NewsInfoAddActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*��ѯ���Ź�����Ϣ*/
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, NewsInfoQueryActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			} else if (item.getItemId() == 3) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			}	
		} else {
			if (item.getItemId() == 1) {
				/*��ѯ���Ź�����Ϣ*/
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, NewsInfoQueryActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, MainMenuUserActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
