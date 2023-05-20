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
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������Ա��������
	private EditText ET_title;
	// �����������������
	private EditText ET_content;
	 
	  
	/*Ҫ�����������Ϣ��Ϣ*/
	GuestBook guestBook = new GuestBook();
	/*������Ϣ����ҵ���߼���*/
	private GuestBookService guestBookService = new GuestBookService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���������Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.guestbook_user_add); 
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�������������Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ���Ա���*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(GuestBookUserAddActivity.this, "���Ա������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					guestBook.setTitle(ET_title.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(GuestBookUserAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					guestBook.setContent(ET_content.getText().toString());
					
					Declare declare = (Declare)getApplication();
					guestBook.setUserObj(declare.getUserName());
					
				
					/*����ҵ���߼����ϴ�������Ϣ��Ϣ*/
					GuestBookUserAddActivity.this.setTitle("�����ϴ�������Ϣ��Ϣ���Ե�...");
					String result = guestBookService.AddGuestBook(guestBook);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�������Ϣ�������*/ 
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
