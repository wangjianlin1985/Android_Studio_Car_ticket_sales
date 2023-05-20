package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class UserInfoEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����û���TextView
	private TextView TV_user_name;
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
	// �����˻���������
	private EditText ET_money;
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

	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸��û���Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_edit); 
		
		
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_realName = (EditText) findViewById(R.id.ET_realName);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		ET_cardNumber = (EditText) findViewById(R.id.ET_cardNumber);
		ET_city = (EditText) findViewById(R.id.ET_city);
		ET_money = (EditText) findViewById(R.id.ET_money);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		
		
		//�˺������޸ģ������������
		this.findViewById(R.id.tableRowMoney).setVisibility(View.GONE);
		 
		
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserInfoEditActivity.this,photoListActivity.class);
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
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		initViewData();
		/*�����޸��û���Ϣ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_realName.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_realName.setFocusable(true);
						ET_realName.requestFocus();
						return;	
					}
					userInfo.setRealName(ET_realName.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
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
						Toast.makeText(UserInfoEditActivity.this, "���֤���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_cardNumber.setFocusable(true);
						ET_cardNumber.requestFocus();
						return;	
					}
					userInfo.setCardNumber(ET_cardNumber.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_city.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_city.setFocusable(true);
						ET_city.requestFocus();
						return;	
					}
					userInfo.setCity(ET_city.getText().toString());
					/*��֤��ȡ�˻����*/ 
					if(ET_money.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�˻�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_money.setFocusable(true);
						ET_money.requestFocus();
						return;	
					}
					userInfo.setMoney(Float.parseFloat(ET_money.getText().toString()));
					if (!userInfo.getPhoto().startsWith("upload/")) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						UserInfoEditActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String photo = HttpUtil.uploadFile(userInfo.getPhoto());
						UserInfoEditActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						userInfo.setPhoto(photo);
					} 
					/*��֤��ȡ��ͥ��ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "��ͥ��ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					/*����ҵ���߼����ϴ��û���Ϣ��Ϣ*/
					UserInfoEditActivity.this.setTitle("���ڸ����û���Ϣ��Ϣ���Ե�...");
					String result = userInfoService.UpdateUserInfo(userInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص��û���Ϣ�������*/ 
					Intent intent = new Intent(); 
					//intent.setClass(UserInfoEditActivity.this, UserInfoListActivity.class);
					//startActivity(intent); 
					UserInfoEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    userInfo = userInfoService.GetUserInfo(user_name);
		this.TV_user_name.setText(user_name);
		this.ET_password.setText(userInfo.getPassword());
		this.ET_realName.setText(userInfo.getRealName());
		this.ET_sex.setText(userInfo.getSex());
		Date birthday = new Date(userInfo.getBirthday().getTime());
		this.dp_birthday.init(birthday.getYear() + 1900,birthday.getMonth(), birthday.getDate(), null);
		this.ET_cardNumber.setText(userInfo.getCardNumber());
		this.ET_city.setText(userInfo.getCity());
		this.ET_money.setText(userInfo.getMoney()+"");
		byte[] photo_data = null;
		try {
			// ��ȡͼƬ����
			photo_data = ImageService.getImage(HttpUtil.BASE_URL + userInfo.getPhoto());
			Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0, photo_data.length);
			this.iv_photo.setImageBitmap(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_address.setText(userInfo.getAddress());
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
