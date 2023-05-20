package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.PlaneInfo;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.SeatType;
import com.mobileclient.service.SeatTypeService;

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
import android.widget.Toast;

public class PlaneInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
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
	/*汽车站信息管理业务逻辑层*/
	private StationInfoService stationInfoService = new StationInfoService();
	// 汽车站日期控件
	private DatePicker dp_startDate;
	private CheckBox cb_startDate;
	// 声明席别下拉框
	private Spinner spinner_seatType;
	private ArrayAdapter<String> seatType_adapter;
	private static  String[] seatType_ShowText  = null;
	private List<SeatType> seatTypeList = null; 
	/*座位席别管理业务逻辑层*/
	private SeatTypeService seatTypeService = new SeatTypeService();
	/*查询过滤条件保存到这个对象中*/
	private PlaneInfo queryConditionPlaneInfo = new PlaneInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询汽车站信息条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.planeinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_planeNumber = (EditText) findViewById(R.id.ET_planeNumber);
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// 获取所有的汽车站信息
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int stationInfoCount = stationInfoList.size();
		startStation_ShowText = new String[stationInfoCount+1];
		startStation_ShowText[0] = "不限制";
		for(int i=1;i<=stationInfoCount;i++) { 
			startStation_ShowText[i] = stationInfoList.get(i-1).getStationName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		startStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startStation_ShowText);
		// 设置始发汽车站下拉列表的风格
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startStation.setAdapter(startStation_adapter);
		// 添加事件Spinner事件监听
		spinner_startStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionPlaneInfo.setStartStation(stationInfoList.get(arg2-1).getStationId()); 
				else
					queryConditionPlaneInfo.setStartStation(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startStation.setVisibility(View.VISIBLE);
		spinner_endStation = (Spinner) findViewById(R.id.Spinner_endStation);
		// 获取所有的汽车站信息
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	 
		endStation_ShowText = new String[stationInfoCount+1];
		endStation_ShowText[0] = "不限制";
		for(int i=1;i<=stationInfoCount;i++) { 
			endStation_ShowText[i] = stationInfoList.get(i-1).getStationName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		endStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endStation_ShowText);
		// 设置终到汽车站下拉列表的风格
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endStation.setAdapter(endStation_adapter);
		// 添加事件Spinner事件监听
		spinner_endStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionPlaneInfo.setEndStation(stationInfoList.get(arg2-1).getStationId()); 
				else
					queryConditionPlaneInfo.setEndStation(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endStation.setVisibility(View.VISIBLE);
		dp_startDate = (DatePicker) findViewById(R.id.dp_startDate);
		cb_startDate = (CheckBox) findViewById(R.id.cb_startDate);
		spinner_seatType = (Spinner) findViewById(R.id.Spinner_seatType);
		// 获取所有的座位席别
		try {
			seatTypeList = seatTypeService.QuerySeatType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int seatTypeCount = seatTypeList.size();
		seatType_ShowText = new String[seatTypeCount+1];
		seatType_ShowText[0] = "不限制";
		for(int i=1;i<=seatTypeCount;i++) { 
			seatType_ShowText[i] = seatTypeList.get(i-1).getSeatTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		seatType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatType_ShowText);
		// 设置席别下拉列表的风格
		seatType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatType.setAdapter(seatType_adapter);
		// 添加事件Spinner事件监听
		spinner_seatType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionPlaneInfo.setSeatType(seatTypeList.get(arg2-1).getSeatTypeId()); 
				else
					queryConditionPlaneInfo.setSeatType(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatType.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/  
					
					if(queryConditionPlaneInfo.getStartStation()==0) {
						Toast.makeText(PlaneInfoQueryActivity.this, "请选择起始站", Toast.LENGTH_LONG).show();
						return;
					}
					
					if(queryConditionPlaneInfo.getEndStation()==0) {
						Toast.makeText(PlaneInfoQueryActivity.this, "请选择终到汽车站", Toast.LENGTH_LONG).show();
						return;
					}
					
					queryConditionPlaneInfo.setPlaneNumber(ET_planeNumber.getText().toString());
				 
					/*获取汽车站日期*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					queryConditionPlaneInfo.setStartDate(new Timestamp(startDate.getTime()));
					 
							 
					/*操作完成后返回到汽车站信息管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(PlaneInfoQueryActivity.this, PlaneInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionPlaneInfo", queryConditionPlaneInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					PlaneInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
