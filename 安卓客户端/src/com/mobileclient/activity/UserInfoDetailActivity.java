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
	// 声明返回按钮
	private Button btnReturn;
	// 声明用户名控件
	private TextView TV_user_name;
	// 声明密码控件
	private TextView TV_password;
	// 声明姓名控件
	private TextView TV_realName;
	// 声明性别控件
	private TextView TV_sex;
	// 声明出生日期控件
	private TextView TV_birthday;
	// 声明身份证控件
	private TextView TV_cardNumber;
	// 声明籍贯控件
	private TextView TV_city;
	// 声明账户余额控件
	private TextView TV_money;
	// 声明照片图片框
	private ImageView iv_photo;
	// 声明家庭地址控件
	private TextView TV_address;
	/* 要保存的用户信息信息 */
	UserInfo userInfo = new UserInfo(); 
	/* 用户信息管理业务逻辑层 */
	private UserInfoService userInfoService = new UserInfoService();
	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看用户信息详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_detail);
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
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
			// 获取图片数据
			photo_data = ImageService.getImage(HttpUtil.BASE_URL + userInfo.getPhoto());
			Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0,photo_data.length);
			this.iv_photo.setImageBitmap(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_address.setText(userInfo.getAddress());
	} 
}
