package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StationInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������¼��ſؼ�
	private TextView TV_stationId;
	// ��������վ���ƿؼ�
	private TextView TV_stationName;
	// ������ϵ�˿ؼ�
	private TextView TV_connectPerson;
	// ������ϵ�绰�ؼ�
	private TextView TV_telephone;
	// �����ʱ�ؼ�
	private TextView TV_postcode;
	// ����ͨѶ��ַ�ؼ�
	private TextView TV_address;
	/* Ҫ���������վ��Ϣ��Ϣ */
	StationInfo stationInfo = new StationInfo(); 
	/* ����վ��Ϣ����ҵ���߼��� */
	private StationInfoService stationInfoService = new StationInfoService();
	private int stationId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴����վ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.stationinfo_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_stationId = (TextView) findViewById(R.id.TV_stationId);
		TV_stationName = (TextView) findViewById(R.id.TV_stationName);
		TV_connectPerson = (TextView) findViewById(R.id.TV_connectPerson);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_postcode = (TextView) findViewById(R.id.TV_postcode);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		stationId = extras.getInt("stationId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StationInfoDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    stationInfo = stationInfoService.GetStationInfo(stationId); 
		this.TV_stationId.setText(stationInfo.getStationId() + "");
		this.TV_stationName.setText(stationInfo.getStationName());
		this.TV_connectPerson.setText(stationInfo.getConnectPerson());
		this.TV_telephone.setText(stationInfo.getTelephone());
		this.TV_postcode.setText(stationInfo.getPostcode());
		this.TV_address.setText(stationInfo.getAddress());
	} 
}
