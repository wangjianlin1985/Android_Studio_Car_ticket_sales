package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
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
public class StationInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明汽车站名称输入框
	private EditText ET_stationName;
	// 声明联系人输入框
	private EditText ET_connectPerson;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明邮编输入框
	private EditText ET_postcode;
	// 声明通讯地址输入框
	private EditText ET_address;
	protected String carmera_path;
	/*要保存的汽车站信息信息*/
	StationInfo stationInfo = new StationInfo();
	/*汽车站信息管理业务逻辑层*/
	private StationInfoService stationInfoService = new StationInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加汽车站信息");
		// 设置当前Activity界面布局
		setContentView(R.layout.stationinfo_add); 
		ET_stationName = (EditText) findViewById(R.id.ET_stationName);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_postcode = (EditText) findViewById(R.id.ET_postcode);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加汽车站信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取汽车站名称*/ 
					if(ET_stationName.getText().toString().equals("")) {
						Toast.makeText(StationInfoAddActivity.this, "汽车站名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_stationName.setFocusable(true);
						ET_stationName.requestFocus();
						return;	
					}
					stationInfo.setStationName(ET_stationName.getText().toString());
					/*验证获取联系人*/ 
					if(ET_connectPerson.getText().toString().equals("")) {
						Toast.makeText(StationInfoAddActivity.this, "联系人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_connectPerson.setFocusable(true);
						ET_connectPerson.requestFocus();
						return;	
					}
					stationInfo.setConnectPerson(ET_connectPerson.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(StationInfoAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					stationInfo.setTelephone(ET_telephone.getText().toString());
					/*验证获取邮编*/ 
					if(ET_postcode.getText().toString().equals("")) {
						Toast.makeText(StationInfoAddActivity.this, "邮编输入不能为空!", Toast.LENGTH_LONG).show();
						ET_postcode.setFocusable(true);
						ET_postcode.requestFocus();
						return;	
					}
					stationInfo.setPostcode(ET_postcode.getText().toString());
					/*验证获取通讯地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(StationInfoAddActivity.this, "通讯地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					stationInfo.setAddress(ET_address.getText().toString());
					/*调用业务逻辑层上传汽车站信息信息*/
					StationInfoAddActivity.this.setTitle("正在上传汽车站信息信息，稍等...");
					String result = stationInfoService.AddStationInfo(stationInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到汽车站信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(StationInfoAddActivity.this, StationInfoListActivity.class);
					startActivity(intent); 
					StationInfoAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
