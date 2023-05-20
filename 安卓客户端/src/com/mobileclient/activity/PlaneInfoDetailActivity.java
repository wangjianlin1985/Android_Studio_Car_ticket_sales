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
	// 声明返回按钮
	private Button btnReturn,btnSubmit;
	// 声明记录编号控件
	private TextView TV_seatId;
	// 声明汽车站控件
	private TextView TV_planeNumber;
	// 声明始发汽车站控件
	private TextView TV_startStation;
	// 声明终到汽车站控件
	private TextView TV_endStation;
	// 声明汽车站日期控件
	private TextView TV_startDate;
	// 声明席别控件
	private TextView TV_seatType;
	// 声明票价控件
	private TextView TV_price;
	// 声明总座位数控件
	private TextView TV_seatNumber;
	// 声明剩余座位数控件
	private TextView TV_leftSeatNumber;
	// 声明发车时间控件
	private TextView TV_startTime;
	// 声明终到时间控件
	private TextView TV_endTime;
	// 声明历时控件
	private TextView TV_totalTime;
	// 声明订票张数控件
	private EditText TicketNum;
	
	/* 要保存的汽车站信息信息 */
	PlaneInfo planeInfo = new PlaneInfo(); 
	/* 汽车站信息管理业务逻辑层 */
	private PlaneInfoService planeInfoService = new PlaneInfoService();
	private StationInfoService stationInfoService = new StationInfoService(); 
	private SeatTypeService seatTypeService = new SeatTypeService();
	private int seatId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看汽车站信息详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.planeinfo_detail);
		// 通过findViewById方法实例化组件
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
					Toast.makeText(PlaneInfoDetailActivity.this, "订票数输入不能为空!", Toast.LENGTH_LONG).show();
					TicketNum.setFocusable(true);
					TicketNum.requestFocus();
					return;	
				}
				int ticketNum = 0;
				try {
					ticketNum = Integer.parseInt(TicketNum.getText().toString()); 
					if(ticketNum <=0 ) {
						Toast.makeText(PlaneInfoDetailActivity.this, "订票数请输入正整数!", Toast.LENGTH_LONG).show();
						TicketNum.setFocusable(true);
						TicketNum.requestFocus();
						return;	
					}
				} catch(Exception ex) {
					Toast.makeText(PlaneInfoDetailActivity.this, "订票数格式不对!", Toast.LENGTH_LONG).show();
					TicketNum.setFocusable(true);
					TicketNum.requestFocus();
					return;	
				} 	
				
				if(ticketNum >5 || ticketNum>planeInfo.getLeftSeatNumber()) {
					Toast.makeText(PlaneInfoDetailActivity.this, "一次只能最多订5张，且不能超过余票数!", Toast.LENGTH_LONG).show();
					TicketNum.setFocusable(true);
					TicketNum.requestFocus();
					return;	
				}
				
				//经过上面的验证，下面开始提交给服务器进行用户订票
				Declare declare = (Declare)PlaneInfoDetailActivity.this.getApplication(); 
				OrderInfoService orderInfoService = new OrderInfoService(); 
				String result = orderInfoService.UserSubmitOrderInfo(declare.getUserName(), seatId, ticketNum);
				Toast.makeText(getApplicationContext(), result, 1).show(); 
				/*操作完成后返回到我的订单界面*/ 
				Intent intent = new Intent();
				intent.setClass(PlaneInfoDetailActivity.this, OrderInfoUserListActivity.class);
				startActivity(intent); 
				PlaneInfoDetailActivity.this.finish();
				
				
			} 
		});
	}
	
	/* 初始化显示详情界面的数据 */
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
