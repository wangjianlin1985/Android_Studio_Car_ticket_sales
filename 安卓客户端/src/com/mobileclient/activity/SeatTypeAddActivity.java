package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class SeatTypeAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����ϯ�����������
	private EditText ET_seatTypeName;
	protected String carmera_path;
	/*Ҫ�������λϯ����Ϣ*/
	SeatType seatType = new SeatType();
	/*��λϯ�����ҵ���߼���*/
	private SeatTypeService seatTypeService = new SeatTypeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�����λϯ��");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seattype_add); 
		ET_seatTypeName = (EditText) findViewById(R.id.ET_seatTypeName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*���������λϯ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡϯ������*/ 
					if(ET_seatTypeName.getText().toString().equals("")) {
						Toast.makeText(SeatTypeAddActivity.this, "ϯ���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_seatTypeName.setFocusable(true);
						ET_seatTypeName.requestFocus();
						return;	
					}
					seatType.setSeatTypeName(ET_seatTypeName.getText().toString());
					/*����ҵ���߼����ϴ���λϯ����Ϣ*/
					SeatTypeAddActivity.this.setTitle("�����ϴ���λϯ����Ϣ���Ե�...");
					String result = seatTypeService.AddSeatType(seatType);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���λϯ��������*/ 
					Intent intent = new Intent();
					intent.setClass(SeatTypeAddActivity.this, SeatTypeListActivity.class);
					startActivity(intent); 
					SeatTypeAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
