package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.SeatType;
import com.mobileclient.service.SeatTypeService;
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

public class SeatTypeDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������¼��ſؼ�
	private TextView TV_seatTypeId;
	// ����ϯ�����ƿؼ�
	private TextView TV_seatTypeName;
	/* Ҫ�������λϯ����Ϣ */
	SeatType seatType = new SeatType(); 
	/* ��λϯ�����ҵ���߼��� */
	private SeatTypeService seatTypeService = new SeatTypeService();
	private int seatTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��λϯ������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seattype_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_seatTypeId = (TextView) findViewById(R.id.TV_seatTypeId);
		TV_seatTypeName = (TextView) findViewById(R.id.TV_seatTypeName);
		Bundle extras = this.getIntent().getExtras();
		seatTypeId = extras.getInt("seatTypeId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeatTypeDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    seatType = seatTypeService.GetSeatType(seatTypeId); 
		this.TV_seatTypeId.setText(seatType.getSeatTypeId() + "");
		this.TV_seatTypeName.setText(seatType.getSeatTypeName());
	} 
}
