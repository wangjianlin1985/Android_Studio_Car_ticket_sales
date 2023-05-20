package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.StationInfo;

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

public class StationInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明汽车站名称输入框
	private EditText ET_stationName;
	// 声明联系人输入框
	private EditText ET_connectPerson;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明邮编输入框
	private EditText ET_postcode;
	/*查询过滤条件保存到这个对象中*/
	private StationInfo queryConditionStationInfo = new StationInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询汽车站信息条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.stationinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_stationName = (EditText) findViewById(R.id.ET_stationName);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_postcode = (EditText) findViewById(R.id.ET_postcode);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionStationInfo.setStationName(ET_stationName.getText().toString());
					queryConditionStationInfo.setConnectPerson(ET_connectPerson.getText().toString());
					queryConditionStationInfo.setTelephone(ET_telephone.getText().toString());
					queryConditionStationInfo.setPostcode(ET_postcode.getText().toString());
					/*操作完成后返回到汽车站信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(StationInfoQueryActivity.this, StationInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionStationInfo", queryConditionStationInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					StationInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
