package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.PlaneInfo;
import com.mobileclient.service.PlaneInfoService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.SeatType;
import com.mobileclient.service.SeatTypeService;
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
public class PlaneInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明汽车站输入框
	private EditText ET_planeNumber;
	// 声明始发汽车站下拉框
	private Spinner spinner_startStation;
	private ArrayAdapter<String> startStation_adapter;
	private static  String[] startStation_ShowText  = null;
	 
	// 声明终到汽车站下拉框
	private Spinner spinner_endStation;
	private ArrayAdapter<String> endStation_adapter;
	private static  String[] endStation_ShowText  = null;
	private List<StationInfo> stationInfoList = null;
	/*终到汽车站管理业务逻辑层*/
	private StationInfoService stationInfoService = new StationInfoService();
	// 出版汽车站日期控件
	private DatePicker dp_startDate;
	// 声明席别下拉框
	private Spinner spinner_seatType;
	private ArrayAdapter<String> seatType_adapter;
	private static  String[] seatType_ShowText  = null;
	private List<SeatType> seatTypeList = null;
	/*席别管理业务逻辑层*/
	private SeatTypeService seatTypeService = new SeatTypeService();
	// 声明票价输入框
	private EditText ET_price;
	// 声明总座位数输入框
	private EditText ET_seatNumber;
	// 声明剩余座位数输入框
	private EditText ET_leftSeatNumber;
	// 声明发车时间输入框
	private EditText ET_startTime;
	// 声明终到时间输入框
	private EditText ET_endTime;
	// 声明历时输入框
	private EditText ET_totalTime;
	protected String carmera_path;
	/*要保存的汽车站信息信息*/
	PlaneInfo planeInfo = new PlaneInfo();
	/*汽车站信息管理业务逻辑层*/
	private PlaneInfoService planeInfoService = new PlaneInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加汽车站信息");
		// 设置当前Activity界面布局
		setContentView(R.layout.planeinfo_add); 
		ET_planeNumber = (EditText) findViewById(R.id.ET_planeNumber);
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// 获取所有的始发汽车站
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int stationInfoCount = stationInfoList.size();
		startStation_ShowText = new String[stationInfoCount];
		endStation_ShowText = new String[stationInfoCount];
		
		for(int i=0;i<stationInfoCount;i++) { 
			endStation_ShowText[i] = stationInfoList.get(i).getStationName();
		}
		for(int i=0;i<stationInfoCount;i++) { 
			startStation_ShowText[i] = stationInfoList.get(i).getStationName();
		}
		// 将可选内容与ArrayAdapter连接起来
		startStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startStation_ShowText);
		// 设置下拉列表的风格
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startStation.setAdapter(startStation_adapter);
		// 添加事件Spinner事件监听
		spinner_startStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				planeInfo.setStartStation(stationInfoList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startStation.setVisibility(View.VISIBLE);
		spinner_endStation = (Spinner) findViewById(R.id.Spinner_endStation);
		// 获取所有的终到汽车站
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
	 
		// 将可选内容与ArrayAdapter连接起来
		endStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endStation_ShowText);
		// 设置下拉列表的风格
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endStation.setAdapter(endStation_adapter);
		// 添加事件Spinner事件监听
		spinner_endStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				planeInfo.setEndStation(stationInfoList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endStation.setVisibility(View.VISIBLE);
		dp_startDate = (DatePicker)this.findViewById(R.id.dp_startDate);
		spinner_seatType = (Spinner) findViewById(R.id.Spinner_seatType);
		// 获取所有的席别
		try {
			seatTypeList = seatTypeService.QuerySeatType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int seatTypeCount = seatTypeList.size();
		seatType_ShowText = new String[seatTypeCount];
		for(int i=0;i<seatTypeCount;i++) { 
			seatType_ShowText[i] = seatTypeList.get(i).getSeatTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		seatType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatType_ShowText);
		// 设置下拉列表的风格
		seatType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatType.setAdapter(seatType_adapter);
		// 添加事件Spinner事件监听
		spinner_seatType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				planeInfo.setSeatType(seatTypeList.get(arg2).getSeatTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatType.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_seatNumber = (EditText) findViewById(R.id.ET_seatNumber);
		ET_leftSeatNumber = (EditText) findViewById(R.id.ET_leftSeatNumber);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		ET_totalTime = (EditText) findViewById(R.id.ET_totalTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加汽车站信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取汽车站*/ 
					if(ET_planeNumber.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "汽车站输入不能为空!", Toast.LENGTH_LONG).show();
						ET_planeNumber.setFocusable(true);
						ET_planeNumber.requestFocus();
						return;	
					}
					planeInfo.setPlaneNumber(ET_planeNumber.getText().toString());
					/*获取出版日期*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					planeInfo.setStartDate(new Timestamp(startDate.getTime()));
					/*验证获取票价*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "票价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					planeInfo.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取总座位数*/ 
					if(ET_seatNumber.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "总座位数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_seatNumber.setFocusable(true);
						ET_seatNumber.requestFocus();
						return;	
					}
					planeInfo.setSeatNumber(Integer.parseInt(ET_seatNumber.getText().toString()));
					/*验证获取剩余座位数*/ 
					if(ET_leftSeatNumber.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "剩余座位数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leftSeatNumber.setFocusable(true);
						ET_leftSeatNumber.requestFocus();
						return;	
					}
					planeInfo.setLeftSeatNumber(Integer.parseInt(ET_leftSeatNumber.getText().toString()));
					/*验证获取发车时间*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "发车时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					planeInfo.setStartTime(ET_startTime.getText().toString());
					/*验证获取终到时间*/ 
					if(ET_endTime.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "终到时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_endTime.setFocusable(true);
						ET_endTime.requestFocus();
						return;	
					}
					planeInfo.setEndTime(ET_endTime.getText().toString());
					/*验证获取历时*/ 
					if(ET_totalTime.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "历时输入不能为空!", Toast.LENGTH_LONG).show();
						ET_totalTime.setFocusable(true);
						ET_totalTime.requestFocus();
						return;	
					}
					planeInfo.setTotalTime(ET_totalTime.getText().toString());
					/*调用业务逻辑层上传汽车站信息信息*/
					PlaneInfoAddActivity.this.setTitle("正在上传汽车站信息信息，稍等...");
					String result = planeInfoService.AddPlaneInfo(planeInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到汽车站信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(PlaneInfoAddActivity.this, PlaneInfoListActivity.class);
					startActivity(intent); 
					PlaneInfoAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
