package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Recharge;
import com.mobileclient.service.RechargeService;
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

public class RechargeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_id;
	// 声明充值用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*充值用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明充值金额输入框
	private EditText ET_money;
	// 声明充值时间输入框
	private EditText ET_chargeTime;
	protected String carmera_path;
	/*要保存的充值信息信息*/
	Recharge recharge = new Recharge();
	/*充值信息管理业务逻辑层*/
	private RechargeService rechargeService = new RechargeService();

	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-修改充值信息");
		// 设置当前Activity界面布局
		setContentView(R.layout.recharge_edit); 
		TV_id = (TextView) findViewById(R.id.TV_id);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的充值用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getRealName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				recharge.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_money = (EditText) findViewById(R.id.ET_money);
		ET_chargeTime = (EditText) findViewById(R.id.ET_chargeTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		initViewData();
		/*单击修改充值信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取充值金额*/ 
					if(ET_money.getText().toString().equals("")) {
						Toast.makeText(RechargeEditActivity.this, "充值金额输入不能为空!", Toast.LENGTH_LONG).show();
						ET_money.setFocusable(true);
						ET_money.requestFocus();
						return;	
					}
					recharge.setMoney(Float.parseFloat(ET_money.getText().toString()));
					/*验证获取充值时间*/ 
					if(ET_chargeTime.getText().toString().equals("")) {
						Toast.makeText(RechargeEditActivity.this, "充值时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_chargeTime.setFocusable(true);
						ET_chargeTime.requestFocus();
						return;	
					}
					recharge.setChargeTime(ET_chargeTime.getText().toString());
					/*调用业务逻辑层上传充值信息信息*/
					RechargeEditActivity.this.setTitle("正在更新充值信息信息，稍等...");
					String result = rechargeService.UpdateRecharge(recharge);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到充值信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(RechargeEditActivity.this, RechargeListActivity.class);
					startActivity(intent); 
					RechargeEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    recharge = rechargeService.GetRecharge(id);
		this.TV_id.setText(id+"");
		for (int i = 0; i < userInfoList.size(); i++) {
			if (recharge.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_money.setText(recharge.getMoney() + "");
		this.ET_chargeTime.setText(recharge.getChargeTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
