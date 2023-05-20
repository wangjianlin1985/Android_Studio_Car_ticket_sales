package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.GuestBook;
import com.mobileclient.service.GuestBookService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class GuestBookUserAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明留言标题输入框
	private EditText ET_title;
	// 声明留言内容输入框
	private EditText ET_content;
	 
	  
	/*要保存的留言信息信息*/
	GuestBook guestBook = new GuestBook();
	/*留言信息管理业务逻辑层*/
	private GuestBookService guestBookService = new GuestBookService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加留言信息");
		// 设置当前Activity界面布局
		setContentView(R.layout.guestbook_user_add); 
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加留言信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取留言标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(GuestBookUserAddActivity.this, "留言标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					guestBook.setTitle(ET_title.getText().toString());
					/*验证获取留言内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(GuestBookUserAddActivity.this, "留言内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					guestBook.setContent(ET_content.getText().toString());
					
					Declare declare = (Declare)getApplication();
					guestBook.setUserObj(declare.getUserName());
					
				
					/*调用业务逻辑层上传留言信息信息*/
					GuestBookUserAddActivity.this.setTitle("正在上传留言信息信息，稍等...");
					String result = guestBookService.AddGuestBook(guestBook);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到留言信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(GuestBookUserAddActivity.this, GuestBookUserListActivity.class);
					startActivity(intent); 
					GuestBookUserAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
