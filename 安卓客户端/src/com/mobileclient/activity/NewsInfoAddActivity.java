package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.NewsInfo;
import com.mobileclient.service.NewsInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;
public class NewsInfoAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������������
	private EditText ET_newsTitle;
	// �����������������
	private EditText ET_newsContent;
	// ���淢�����ڿؼ�
	private DatePicker dp_newsDate;
	protected String carmera_path;
	/*Ҫ��������Ź�����Ϣ*/
	NewsInfo newsInfo = new NewsInfo();
	/*���Ź������ҵ���߼���*/
	private NewsInfoService newsInfoService = new NewsInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-������Ź���");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.newsinfo_add); 
		ET_newsTitle = (EditText) findViewById(R.id.ET_newsTitle);
		ET_newsContent = (EditText) findViewById(R.id.ET_newsContent);
		dp_newsDate = (DatePicker)this.findViewById(R.id.dp_newsDate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*����������Ź��水ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����*/ 
					if(ET_newsTitle.getText().toString().equals("")) {
						Toast.makeText(NewsInfoAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_newsTitle.setFocusable(true);
						ET_newsTitle.requestFocus();
						return;	
					}
					newsInfo.setNewsTitle(ET_newsTitle.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_newsContent.getText().toString().equals("")) {
						Toast.makeText(NewsInfoAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_newsContent.setFocusable(true);
						ET_newsContent.requestFocus();
						return;	
					}
					newsInfo.setNewsContent(ET_newsContent.getText().toString());
					/*��ȡ��������*/
					Date newsDate = new Date(dp_newsDate.getYear()-1900,dp_newsDate.getMonth(),dp_newsDate.getDayOfMonth());
					newsInfo.setNewsDate(new Timestamp(newsDate.getTime()));
					/*����ҵ���߼����ϴ����Ź�����Ϣ*/
					NewsInfoAddActivity.this.setTitle("�����ϴ����Ź�����Ϣ���Ե�...");
					String result = newsInfoService.AddNewsInfo(newsInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص����Ź���������*/ 
					Intent intent = new Intent();
					intent.setClass(NewsInfoAddActivity.this, NewsInfoListActivity.class);
					startActivity(intent); 
					NewsInfoAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
