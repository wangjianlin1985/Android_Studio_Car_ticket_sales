package com.mobileclient.activity;

import java.util.Date;
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

public class UserInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �����û����ؼ�
	private TextView TV_user_name;
	// ��������ؼ�
	private TextView TV_password;
	// ���������ؼ�
	private TextView TV_realName;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// �����������ڿؼ�
	private TextView TV_birthday;
	// �������֤�ؼ�
	private TextView TV_cardNumber;
	// ��������ؼ�
	private TextView TV_city;
	// �����˻����ؼ�
	private TextView TV_money;
	// ������ƬͼƬ��
	private ImageView iv_photo;
	// ������ͥ��ַ�ؼ�
	private TextView TV_address;
	/* Ҫ������û���Ϣ��Ϣ */
	UserInfo userInfo = new UserInfo(); 
	/* �û���Ϣ����ҵ���߼��� */
	private UserInfoService userInfoService = new UserInfoService();
	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴�û���Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_realName = (TextView) findViewById(R.id.TV_realName);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		TV_cardNumber = (TextView) findViewById(R.id.TV_cardNumber);
		TV_city = (TextView) findViewById(R.id.TV_city);
		TV_money = (TextView) findViewById(R.id.TV_money);
		iv_photo = (ImageView) findViewById(R.id.iv_photo); 
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserInfoDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    userInfo = userInfoService.GetUserInfo(user_name); 
		this.TV_user_name.setText(userInfo.getUser_name());
		this.TV_password.setText(userInfo.getPassword());
		this.TV_realName.setText(userInfo.getRealName());
		this.TV_sex.setText(userInfo.getSex());
		Date birthday = new Date(userInfo.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		this.TV_cardNumber.setText(userInfo.getCardNumber());
		this.TV_city.setText(userInfo.getCity());
		this.TV_money.setText(userInfo.getMoney()+"");
		byte[] photo_data = null;
		try {
			// ��ȡͼƬ����
			photo_data = ImageService.getImage(HttpUtil.BASE_URL + userInfo.getPhoto());
			Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0,photo_data.length);
			this.iv_photo.setImageBitmap(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_address.setText(userInfo.getAddress());
	} 
}
