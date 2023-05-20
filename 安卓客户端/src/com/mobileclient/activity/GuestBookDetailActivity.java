package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.GuestBook;
import com.mobileclient.service.GuestBookService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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

public class GuestBookDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������¼��ſؼ�
	private TextView TV_guestBookId;
	// �������Ա���ؼ�
	private TextView TV_title;
	// �����������ݿؼ�
	private TextView TV_content;
	// ���������˿ؼ�
	private TextView TV_userObj;
	// ��������ʱ��ؼ�
	private TextView TV_addTime;
	/* Ҫ�����������Ϣ��Ϣ */
	GuestBook guestBook = new GuestBook(); 
	/* ������Ϣ����ҵ���߼��� */
	private GuestBookService guestBookService = new GuestBookService();
	private UserInfoService userInfoService = new UserInfoService();
	private int guestBookId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴������Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.guestbook_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_guestBookId = (TextView) findViewById(R.id.TV_guestBookId);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		guestBookId = extras.getInt("guestBookId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GuestBookDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    guestBook = guestBookService.GetGuestBook(guestBookId); 
		this.TV_guestBookId.setText(guestBook.getGuestBookId() + "");
		this.TV_title.setText(guestBook.getTitle());
		this.TV_content.setText(guestBook.getContent());
		UserInfo userInfo = userInfoService.GetUserInfo(guestBook.getUserObj());
		this.TV_userObj.setText(userInfo.getRealName());
		this.TV_addTime.setText(guestBook.getAddTime());
	} 
}
