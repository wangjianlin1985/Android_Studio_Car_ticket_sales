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
	// ������ѯ��ť
	private Button btnQuery;
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
	/*����վ��Ϣ����ҵ���߼���*/
	private StationInfoService stationInfoService = new StationInfoService();
	// ����վ���ڿؼ�
	private DatePicker dp_startDate;
	private CheckBox cb_startDate;
	// ����ϯ��������
	private Spinner spinner_seatType;
	private ArrayAdapter<String> seatType_adapter;
	private static  String[] seatType_ShowText  = null;
	private List<SeatType> seatTypeList = null; 
	/*��λϯ�����ҵ���߼���*/
	private SeatTypeService seatTypeService = new SeatTypeService();
	/*��ѯ�����������浽���������*/
	private PlaneInfo queryConditionPlaneInfo = new PlaneInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ����վ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.planeinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_planeNumber = (EditText) findViewById(R.id.ET_planeNumber);
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// ��ȡ���е�����վ��Ϣ
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int stationInfoCount = stationInfoList.size();
		startStation_ShowText = new String[stationInfoCount+1];
		startStation_ShowText[0] = "������";
		for(int i=1;i<=stationInfoCount;i++) { 
			startStation_ShowText[i] = stationInfoList.get(i-1).getStationName();
		} 
		// ����ѡ������ArrayAdapter��������
		startStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startStation_ShowText);
		// ����ʼ������վ�����б�ķ��
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_startStation.setAdapter(startStation_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_startStation.setVisibility(View.VISIBLE);
		spinner_endStation = (Spinner) findViewById(R.id.Spinner_endStation);
		// ��ȡ���е�����վ��Ϣ
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	 
		endStation_ShowText = new String[stationInfoCount+1];
		endStation_ShowText[0] = "������";
		for(int i=1;i<=stationInfoCount;i++) { 
			endStation_ShowText[i] = stationInfoList.get(i-1).getStationName();
		} 
		// ����ѡ������ArrayAdapter��������
		endStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endStation_ShowText);
		// �����յ�����վ�����б�ķ��
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_endStation.setAdapter(endStation_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_endStation.setVisibility(View.VISIBLE);
		dp_startDate = (DatePicker) findViewById(R.id.dp_startDate);
		cb_startDate = (CheckBox) findViewById(R.id.cb_startDate);
		spinner_seatType = (Spinner) findViewById(R.id.Spinner_seatType);
		// ��ȡ���е���λϯ��
		try {
			seatTypeList = seatTypeService.QuerySeatType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int seatTypeCount = seatTypeList.size();
		seatType_ShowText = new String[seatTypeCount+1];
		seatType_ShowText[0] = "������";
		for(int i=1;i<=seatTypeCount;i++) { 
			seatType_ShowText[i] = seatTypeList.get(i-1).getSeatTypeName();
		} 
		// ����ѡ������ArrayAdapter��������
		seatType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatType_ShowText);
		// ����ϯ�������б�ķ��
		seatType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_seatType.setAdapter(seatType_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_seatType.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/  
					
					if(queryConditionPlaneInfo.getStartStation()==0) {
						Toast.makeText(PlaneInfoQueryActivity.this, "��ѡ����ʼվ", Toast.LENGTH_LONG).show();
						return;
					}
					
					if(queryConditionPlaneInfo.getEndStation()==0) {
						Toast.makeText(PlaneInfoQueryActivity.this, "��ѡ���յ�����վ", Toast.LENGTH_LONG).show();
						return;
					}
					
					queryConditionPlaneInfo.setPlaneNumber(ET_planeNumber.getText().toString());
				 
					/*��ȡ����վ����*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					queryConditionPlaneInfo.setStartDate(new Timestamp(startDate.getTime()));
					 
							 
					/*������ɺ󷵻ص�����վ��Ϣ�������*/ 
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
