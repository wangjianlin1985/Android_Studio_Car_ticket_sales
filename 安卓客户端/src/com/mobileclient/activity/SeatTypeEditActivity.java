package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class SeatTypeEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ������¼���TextView
	private TextView TV_seatTypeId;
	// ����ϯ�����������
	private EditText ET_seatTypeName;
	protected String carmera_path;
	/*Ҫ�������λϯ����Ϣ*/
	SeatType seatType = new SeatType();
	/*��λϯ�����ҵ���߼���*/
	private SeatTypeService seatTypeService = new SeatTypeService();

	private int seatTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸���λϯ��");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seattype_edit); 
		TV_seatTypeId = (TextView) findViewById(R.id.TV_seatTypeId);
		ET_seatTypeName = (EditText) findViewById(R.id.ET_seatTypeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		seatTypeId = extras.getInt("seatTypeId");
		initViewData();
		/*�����޸���λϯ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡϯ������*/ 
					if(ET_seatTypeName.getText().toString().equals("")) {
						Toast.makeText(SeatTypeEditActivity.this, "ϯ���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_seatTypeName.setFocusable(true);
						ET_seatTypeName.requestFocus();
						return;	
					}
					seatType.setSeatTypeName(ET_seatTypeName.getText().toString());
					/*����ҵ���߼����ϴ���λϯ����Ϣ*/
					SeatTypeEditActivity.this.setTitle("���ڸ�����λϯ����Ϣ���Ե�...");
					String result = seatTypeService.UpdateSeatType(seatType);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���λϯ��������*/ 
					Intent intent = new Intent();
					intent.setClass(SeatTypeEditActivity.this, SeatTypeListActivity.class);
					startActivity(intent); 
					SeatTypeEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    seatType = seatTypeService.GetSeatType(seatTypeId);
		this.TV_seatTypeId.setText(seatTypeId+"");
		this.ET_seatTypeName.setText(seatType.getSeatTypeName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
