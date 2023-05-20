package com.mobileclient.activity;

import java.util.Date;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.PlaneInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.service.PlaneInfoService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.SeatType;
import com.mobileclient.service.SeatTypeService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlaneInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn,btnSubmit;
	// ������¼��ſؼ�
	private TextView TV_seatId;
	// ��������վ�ؼ�
	private TextView TV_planeNumber;
	// ����ʼ������վ�ؼ�
	private TextView TV_startStation;
	// �����յ�����վ�ؼ�
	private TextView TV_endStation;
	// ��������վ���ڿؼ�
	private TextView TV_startDate;
	// ����ϯ��ؼ�
	private TextView TV_seatType;
	// ����Ʊ�ۿؼ�
	private TextView TV_price;
	// ��������λ���ؼ�
	private TextView TV_seatNumber;
	// ����ʣ����λ���ؼ�
	private TextView TV_leftSeatNumber;
	// ��������ʱ��ؼ�
	private TextView TV_startTime;
	// �����յ�ʱ��ؼ�
	private TextView TV_endTime;
	// ������ʱ�ؼ�
	private TextView TV_totalTime;
	// ������Ʊ�����ؼ�
	private EditText TicketNum;
	
	/* Ҫ���������վ��Ϣ��Ϣ */
	PlaneInfo planeInfo = new PlaneInfo(); 
	/* ����վ��Ϣ����ҵ���߼��� */
	private PlaneInfoService planeInfoService = new PlaneInfoService();
	private StationInfoService stationInfoService = new StationInfoService(); 
	private SeatTypeService seatTypeService = new SeatTypeService();
	private int seatId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴����վ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.planeinfo_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		TV_seatId = (TextView) findViewById(R.id.TV_seatId);
		TV_planeNumber = (TextView) findViewById(R.id.TV_planeNumber);
		TV_startStation = (TextView) findViewById(R.id.TV_startStation);
		TV_endStation = (TextView) findViewById(R.id.TV_endStation);
		TV_startDate = (TextView) findViewById(R.id.TV_startDate);
		TV_seatType = (TextView) findViewById(R.id.TV_seatType);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_seatNumber = (TextView) findViewById(R.id.TV_seatNumber);
		TV_leftSeatNumber = (TextView) findViewById(R.id.TV_leftSeatNumber);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_endTime = (TextView) findViewById(R.id.TV_endTime);
		TV_totalTime = (TextView) findViewById(R.id.TV_totalTime);
		TicketNum = (EditText) findViewById(R.id.ticketNum);
		Bundle extras = this.getIntent().getExtras();
		seatId = extras.getInt("seatId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {  
			@Override
			public void onClick(View arg0) {
				PlaneInfoDetailActivity.this.finish(); 
			}
		}); 
		
		btnSubmit.setOnClickListener(new OnClickListener(){ 
			public void onClick(View v) {
				if(TicketNum.getText().toString().equals("")) {
					Toast.makeText(PlaneInfoDetailActivity.this, "��Ʊ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
					TicketNum.setFocusable(true);
					TicketNum.requestFocus();
					return;	
				}
				int ticketNum = 0;
				try {
					ticketNum = Integer.parseInt(TicketNum.getText().toString()); 
					if(ticketNum <=0 ) {
						Toast.makeText(PlaneInfoDetailActivity.this, "��Ʊ��������������!", Toast.LENGTH_LONG).show();
						TicketNum.setFocusable(true);
						TicketNum.requestFocus();
						return;	
					}
				} catch(Exception ex) {
					Toast.makeText(PlaneInfoDetailActivity.this, "��Ʊ����ʽ����!", Toast.LENGTH_LONG).show();
					TicketNum.setFocusable(true);
					TicketNum.requestFocus();
					return;	
				} 	
				
				if(ticketNum >5 || ticketNum>planeInfo.getLeftSeatNumber()) {
					Toast.makeText(PlaneInfoDetailActivity.this, "һ��ֻ����ඩ5�ţ��Ҳ��ܳ�����Ʊ��!", Toast.LENGTH_LONG).show();
					TicketNum.setFocusable(true);
					TicketNum.requestFocus();
					return;	
				}
				
				//�����������֤�����濪ʼ�ύ�������������û���Ʊ
				Declare declare = (Declare)PlaneInfoDetailActivity.this.getApplication(); 
				OrderInfoService orderInfoService = new OrderInfoService(); 
				String result = orderInfoService.UserSubmitOrderInfo(declare.getUserName(), seatId, ticketNum);
				Toast.makeText(getApplicationContext(), result, 1).show(); 
				/*������ɺ󷵻ص��ҵĶ�������*/ 
				Intent intent = new Intent();
				intent.setClass(PlaneInfoDetailActivity.this, OrderInfoUserListActivity.class);
				startActivity(intent); 
				PlaneInfoDetailActivity.this.finish();
				
				
			} 
		});
	}
	
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    planeInfo = planeInfoService.GetPlaneInfo(seatId); 
		this.TV_seatId.setText(planeInfo.getSeatId() + "");
		this.TV_planeNumber.setText(planeInfo.getPlaneNumber());
		StationInfo stationInfo = stationInfoService.GetStationInfo(planeInfo.getStartStation());
		this.TV_startStation.setText(stationInfo.getStationName());
		StationInfo endStationInfo = stationInfoService.GetStationInfo(planeInfo.getEndStation());
		this.TV_endStation.setText(endStationInfo.getStationName());
		Date startDate = new Date(planeInfo.getStartDate().getTime());
		String startDateStr = (startDate.getYear() + 1900) + "-" + (startDate.getMonth()+1) + "-" + startDate.getDate();
		this.TV_startDate.setText(startDateStr);
		SeatType seatType = seatTypeService.GetSeatType(planeInfo.getSeatType());
		this.TV_seatType.setText(seatType.getSeatTypeName());
		this.TV_price.setText(planeInfo.getPrice() + "");
		this.TV_seatNumber.setText(planeInfo.getSeatNumber() + "");
		this.TV_leftSeatNumber.setText(planeInfo.getLeftSeatNumber() + "");
		this.TV_startTime.setText(planeInfo.getStartTime());
		this.TV_endTime.setText(planeInfo.getEndTime());
		this.TV_totalTime.setText(planeInfo.getTotalTime());
		
		Declare declare = (Declare)this.getApplication();
		if(declare.getIdentify().equals("user")) {
			this.findViewById(R.id.tableRowTicket).setVisibility(View.VISIBLE);
			this.findViewById(R.id.btnSubmit).setVisibility(View.VISIBLE);
		}
		 
	} 
}
