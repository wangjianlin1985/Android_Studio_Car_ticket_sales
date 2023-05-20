package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class UserInfoRegisterActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����û��������
	private EditText ET_user_name;
	// �������������
	private EditText ET_password;
	// �������������
	private EditText ET_realName;
	// �����Ա������
	private EditText ET_sex;
	// ����������ڿؼ�
	private DatePicker dp_birthday;
	// �������֤�����
	private EditText ET_cardNumber;
	// �������������
	private EditText ET_city;
	 
	// ������ƬͼƬ��ؼ�
	private ImageView iv_photo;
	private Button btn_photo;
	protected int REQ_CODE_SELECT_IMAGE_photo = 1;
	private int REQ_CODE_CAMERA_photo = 2;
	// ������ͥ��ַ�����
	private EditText ET_address;
	protected String carmera_path;
	/*Ҫ������û���Ϣ��Ϣ*/
	UserInfo userInfo = new UserInfo(); 
	/*�û���Ϣ����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-����û���Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_add); 
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_realName = (EditText) findViewById(R.id.ET_realName);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		ET_cardNumber = (EditText) findViewById(R.id.ET_cardNumber);
		ET_city = (EditText) findViewById(R.id.ET_city);
		findViewById(R.id.tableRowMoney).setVisibility(View.GONE);
		
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserInfoRegisterActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_photo);
			}
		});
		btn_photo = (Button) findViewById(R.id.btn_photo);
		btn_photo.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_photo.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_photo);  
			}
		});
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*��������û���Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�û���*/
					if(ET_user_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�û������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_user_name.setFocusable(true);
						ET_user_name.requestFocus();
						return;
					}
					userInfo.setUser_name(ET_user_name.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_realName.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_realName.setFocusable(true);
						ET_realName.requestFocus();
						return;	
					}
					userInfo.setRealName(ET_realName.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					userInfo.setSex(ET_sex.getText().toString());
					/*��ȡ��������*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					userInfo.setBirthday(new Timestamp(birthday.getTime()));
					/*��֤��ȡ���֤*/ 
					if(ET_cardNumber.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "���֤���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_cardNumber.setFocusable(true);
						ET_cardNumber.requestFocus();
						return;	
					}
					userInfo.setCardNumber(ET_cardNumber.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_city.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_city.setFocusable(true);
						ET_city.requestFocus();
						return;	
					}
					userInfo.setCity(ET_city.getText().toString());
					
					//�û�ע��ʱ��ʼ���˺Ž��=0
					userInfo.setMoney(0.0f);
					 
				 
					if(userInfo.getPhoto() != null) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						UserInfoRegisterActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String photo = HttpUtil.uploadFile(userInfo.getPhoto());
						UserInfoRegisterActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						userInfo.setPhoto(photo);
					} else {
						userInfo.setPhoto("upload/noimage.jpg");
					}
					/*��֤��ȡ��ͥ��ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "��ͥ��ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					/*����ҵ���߼����ϴ��û���Ϣ��Ϣ*/
					UserInfoRegisterActivity.this.setTitle("�����ϴ��û���Ϣ��Ϣ���Ե�...");
					String result = userInfoService.AddUserInfo(userInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���½����*/ 
					Intent intent = new Intent();
					intent.setClass(UserInfoRegisterActivity.this, LoginActivity.class);
					startActivity(intent); 
					UserInfoRegisterActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_photo  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_photo.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_photo.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_photo.setImageBitmap(booImageBm);
				this.iv_photo.setScaleType(ScaleType.FIT_CENTER);
				this.userInfo.setPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_photo && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_photo.setImageBitmap(bm); 
				this.iv_photo.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			userInfo.setPhoto(filename); 
		}
	}
}
