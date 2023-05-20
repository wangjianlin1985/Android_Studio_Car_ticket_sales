package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Recharge;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class RechargeQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明充值用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户信息管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明充值时间输入框
	private EditText ET_chargeTime;
	/*查询过滤条件保存到这个对象中*/
	private Recharge queryConditionRecharge = new Recharge();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询充值信息条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.recharge_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户信息
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getRealName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置充值用户下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionRecharge.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionRecharge.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_chargeTime = (EditText) findViewById(R.id.ET_chargeTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionRecharge.setChargeTime(ET_chargeTime.getText().toString());
					/*操作完成后返回到充值信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(RechargeQueryActivity.this, RechargeListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionRecharge", queryConditionRecharge);
					intent.putExtras(bundle);
					startActivity(intent);  
					RechargeQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
