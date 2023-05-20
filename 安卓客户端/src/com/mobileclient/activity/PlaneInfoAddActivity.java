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
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ��������վ�����
	private EditText ET_planeNumber;
	// ����ʼ������վ������
	private Spinner spinner_startStation;
	private ArrayAdapter<String> startStation_adapter;
	private static  String[] startStation_ShowText  = null;
	 
	// �����յ�����վ������
	private Spinner spinner_endStation;
	private ArrayAdapter<String> endStation_adapter;
	private static  String[] endStation_ShowText  = null;
	private List<StationInfo> stationInfoList = null;
	/*�յ�����վ����ҵ���߼���*/
	private StationInfoService stationInfoService = new StationInfoService();
	// ��������վ���ڿؼ�
	private DatePicker dp_startDate;
	// ����ϯ��������
	private Spinner spinner_seatType;
	private ArrayAdapter<String> seatType_adapter;
	private static  String[] seatType_ShowText  = null;
	private List<SeatType> seatTypeList = null;
	/*ϯ�����ҵ���߼���*/
	private SeatTypeService seatTypeService = new SeatTypeService();
	// ����Ʊ�������
	private EditText ET_price;
	// ��������λ�������
	private EditText ET_seatNumber;
	// ����ʣ����λ�������
	private EditText ET_leftSeatNumber;
	// ��������ʱ�������
	private EditText ET_startTime;
	// �����յ�ʱ�������
	private EditText ET_endTime;
	// ������ʱ�����
	private EditText ET_totalTime;
	protected String carmera_path;
	/*Ҫ���������վ��Ϣ��Ϣ*/
	PlaneInfo planeInfo = new PlaneInfo();
	/*����վ��Ϣ����ҵ���߼���*/
	private PlaneInfoService planeInfoService = new PlaneInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�������վ��Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.planeinfo_add); 
		ET_planeNumber = (EditText) findViewById(R.id.ET_planeNumber);
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// ��ȡ���е�ʼ������վ
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
		// ����ѡ������ArrayAdapter��������
		startStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startStation_ShowText);
		// ���������б�ķ��
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_startStation.setAdapter(startStation_adapter);
		// ����¼�Spinner�¼�����
		spinner_startStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				planeInfo.setStartStation(stationInfoList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_startStation.setVisibility(View.VISIBLE);
		spinner_endStation = (Spinner) findViewById(R.id.Spinner_endStation);
		// ��ȡ���е��յ�����վ
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
	 
		// ����ѡ������ArrayAdapter��������
		endStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endStation_ShowText);
		// ���������б�ķ��
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_endStation.setAdapter(endStation_adapter);
		// ����¼�Spinner�¼�����
		spinner_endStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				planeInfo.setEndStation(stationInfoList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_endStation.setVisibility(View.VISIBLE);
		dp_startDate = (DatePicker)this.findViewById(R.id.dp_startDate);
		spinner_seatType = (Spinner) findViewById(R.id.Spinner_seatType);
		// ��ȡ���е�ϯ��
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
		// ����ѡ������ArrayAdapter��������
		seatType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatType_ShowText);
		// ���������б�ķ��
		seatType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_seatType.setAdapter(seatType_adapter);
		// ����¼�Spinner�¼�����
		spinner_seatType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				planeInfo.setSeatType(seatTypeList.get(arg2).getSeatTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_seatType.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_seatNumber = (EditText) findViewById(R.id.ET_seatNumber);
		ET_leftSeatNumber = (EditText) findViewById(R.id.ET_leftSeatNumber);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		ET_totalTime = (EditText) findViewById(R.id.ET_totalTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�����������վ��Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����վ*/ 
					if(ET_planeNumber.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "����վ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_planeNumber.setFocusable(true);
						ET_planeNumber.requestFocus();
						return;	
					}
					planeInfo.setPlaneNumber(ET_planeNumber.getText().toString());
					/*��ȡ��������*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					planeInfo.setStartDate(new Timestamp(startDate.getTime()));
					/*��֤��ȡƱ��*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "Ʊ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					planeInfo.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*��֤��ȡ����λ��*/ 
					if(ET_seatNumber.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "����λ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_seatNumber.setFocusable(true);
						ET_seatNumber.requestFocus();
						return;	
					}
					planeInfo.setSeatNumber(Integer.parseInt(ET_seatNumber.getText().toString()));
					/*��֤��ȡʣ����λ��*/ 
					if(ET_leftSeatNumber.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "ʣ����λ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_leftSeatNumber.setFocusable(true);
						ET_leftSeatNumber.requestFocus();
						return;	
					}
					planeInfo.setLeftSeatNumber(Integer.parseInt(ET_leftSeatNumber.getText().toString()));
					/*��֤��ȡ����ʱ��*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "����ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					planeInfo.setStartTime(ET_startTime.getText().toString());
					/*��֤��ȡ�յ�ʱ��*/ 
					if(ET_endTime.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "�յ�ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_endTime.setFocusable(true);
						ET_endTime.requestFocus();
						return;	
					}
					planeInfo.setEndTime(ET_endTime.getText().toString());
					/*��֤��ȡ��ʱ*/ 
					if(ET_totalTime.getText().toString().equals("")) {
						Toast.makeText(PlaneInfoAddActivity.this, "��ʱ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_totalTime.setFocusable(true);
						ET_totalTime.requestFocus();
						return;	
					}
					planeInfo.setTotalTime(ET_totalTime.getText().toString());
					/*����ҵ���߼����ϴ�����վ��Ϣ��Ϣ*/
					PlaneInfoAddActivity.this.setTitle("�����ϴ�����վ��Ϣ��Ϣ���Ե�...");
					String result = planeInfoService.AddPlaneInfo(planeInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�����վ��Ϣ�������*/ 
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
