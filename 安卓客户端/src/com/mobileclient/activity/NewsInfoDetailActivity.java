package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.NewsInfo;
import com.mobileclient.service.NewsInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������¼��ſؼ�
	private TextView TV_newsId;
	// ��������ؼ�
	private TextView TV_newsTitle;
	// �����������ݿؼ�
	private TextView TV_newsContent;
	// �����������ڿؼ�
	private TextView TV_newsDate;
	/* Ҫ��������Ź�����Ϣ */
	NewsInfo newsInfo = new NewsInfo(); 
	/* ���Ź������ҵ���߼��� */
	private NewsInfoService newsInfoService = new NewsInfoService();
	private int newsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴���Ź�������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.newsinfo_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_newsId = (TextView) findViewById(R.id.TV_newsId);
		TV_newsTitle = (TextView) findViewById(R.id.TV_newsTitle);
		TV_newsContent = (TextView) findViewById(R.id.TV_newsContent);
		TV_newsDate = (TextView) findViewById(R.id.TV_newsDate);
		Bundle extras = this.getIntent().getExtras();
		newsId = extras.getInt("newsId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NewsInfoDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    newsInfo = newsInfoService.GetNewsInfo(newsId); 
		this.TV_newsId.setText(newsInfo.getNewsId() + "");
		this.TV_newsTitle.setText(newsInfo.getNewsTitle());
		this.TV_newsContent.setText(newsInfo.getNewsContent());
		Date newsDate = new Date(newsInfo.getNewsDate().getTime());
		String newsDateStr = (newsDate.getYear() + 1900) + "-" + (newsDate.getMonth()+1) + "-" + newsDate.getDate();
		this.TV_newsDate.setText(newsDateStr);
	} 
}
