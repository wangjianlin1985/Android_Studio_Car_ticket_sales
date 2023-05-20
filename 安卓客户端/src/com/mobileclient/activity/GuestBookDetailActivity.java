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
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_guestBookId;
	// 声明留言标题控件
	private TextView TV_title;
	// 声明留言内容控件
	private TextView TV_content;
	// 声明留言人控件
	private TextView TV_userObj;
	// 声明留言时间控件
	private TextView TV_addTime;
	/* 要保存的留言信息信息 */
	GuestBook guestBook = new GuestBook(); 
	/* 留言信息管理业务逻辑层 */
	private GuestBookService guestBookService = new GuestBookService();
	private UserInfoService userInfoService = new UserInfoService();
	private int guestBookId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看留言信息详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.guestbook_detail);
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
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
