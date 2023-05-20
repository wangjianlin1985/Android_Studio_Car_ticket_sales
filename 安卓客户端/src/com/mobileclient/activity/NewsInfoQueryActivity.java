package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.NewsInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class NewsInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明标题输入框
	private EditText ET_newsTitle;
	// 发布日期控件
	private DatePicker dp_newsDate;
	private CheckBox cb_newsDate;
	/*查询过滤条件保存到这个对象中*/
	private NewsInfo queryConditionNewsInfo = new NewsInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询新闻公告条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.newsinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_newsTitle = (EditText) findViewById(R.id.ET_newsTitle);
		dp_newsDate = (DatePicker) findViewById(R.id.dp_newsDate);
		cb_newsDate = (CheckBox) findViewById(R.id.cb_newsDate);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionNewsInfo.setNewsTitle(ET_newsTitle.getText().toString());
					if(cb_newsDate.isChecked()) {
						/*获取发布日期*/
						Date newsDate = new Date(dp_newsDate.getYear()-1900,dp_newsDate.getMonth(),dp_newsDate.getDayOfMonth());
						queryConditionNewsInfo.setNewsDate(new Timestamp(newsDate.getTime()));
					} else {
						queryConditionNewsInfo.setNewsDate(null);
					} 
					/*操作完成后返回到新闻公告管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(NewsInfoQueryActivity.this, NewsInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionNewsInfo", queryConditionNewsInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					NewsInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
