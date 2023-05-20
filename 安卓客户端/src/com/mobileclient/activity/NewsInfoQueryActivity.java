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
	// ������ѯ��ť
	private Button btnQuery;
	// �������������
	private EditText ET_newsTitle;
	// �������ڿؼ�
	private DatePicker dp_newsDate;
	private CheckBox cb_newsDate;
	/*��ѯ�����������浽���������*/
	private NewsInfo queryConditionNewsInfo = new NewsInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ���Ź�������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.newsinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_newsTitle = (EditText) findViewById(R.id.ET_newsTitle);
		dp_newsDate = (DatePicker) findViewById(R.id.dp_newsDate);
		cb_newsDate = (CheckBox) findViewById(R.id.cb_newsDate);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionNewsInfo.setNewsTitle(ET_newsTitle.getText().toString());
					if(cb_newsDate.isChecked()) {
						/*��ȡ��������*/
						Date newsDate = new Date(dp_newsDate.getYear()-1900,dp_newsDate.getMonth(),dp_newsDate.getDayOfMonth());
						queryConditionNewsInfo.setNewsDate(new Timestamp(newsDate.getTime()));
					} else {
						queryConditionNewsInfo.setNewsDate(null);
					} 
					/*������ɺ󷵻ص����Ź���������*/ 
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
