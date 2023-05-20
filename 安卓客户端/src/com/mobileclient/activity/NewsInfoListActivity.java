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
	/* 新闻公告操作业务逻辑层对象 */
	NewsInfoService newsInfoService = new NewsInfoService();
	/*保存查询参数条件的新闻公告对象*/
	private NewsInfo queryConditionNewsInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsinfo_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--新闻公告列表");
		} else {
			setTitle("您好：" + username + "   当前位置---新闻公告列表");
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
		// 添加长按点击
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
				menu.add(0, 0, 0, "编辑新闻公告信息"); 
				menu.add(0, 1, 0, "删除新闻公告信息");
			}
			
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑新闻公告信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			newsId = Integer.parseInt(list.get(position).get("newsId").toString());
			Intent intent = new Intent();
			intent.setClass(NewsInfoListActivity.this, NewsInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("newsId", newsId);
			intent.putExtras(bundle);
			startActivity(intent);
			NewsInfoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除新闻公告信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			newsId = Integer.parseInt(list.get(position).get("newsId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(NewsInfoListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = newsInfoService.DeleteNewsInfo(newsId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询新闻公告信息 */
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
			menu.add(0, 1, 1, "添加新闻公告");
			menu.add(0, 2, 2, "查询新闻公告");
			menu.add(0, 3, 3, "返回主界面");
		} else { 
			menu.add(0, 1, 1, "查询新闻公告");
			menu.add(0, 2, 2, "返回主界面");
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare)getApplication();
		if(declare.getIdentify().equals("admin")) {
			if (item.getItemId() == 1) {
				// 添加新闻公告信息
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, NewsInfoAddActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*查询新闻公告信息*/
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, NewsInfoQueryActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			} else if (item.getItemId() == 3) {
				/*返回主界面*/
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			}	
		} else {
			if (item.getItemId() == 1) {
				/*查询新闻公告信息*/
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, NewsInfoQueryActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*返回主界面*/
				Intent intent = new Intent();
				intent.setClass(NewsInfoListActivity.this, MainMenuUserActivity.class);
				startActivity(intent);
				NewsInfoListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
