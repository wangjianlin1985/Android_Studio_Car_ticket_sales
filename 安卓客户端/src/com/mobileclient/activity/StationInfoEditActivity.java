package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
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

public class StationInfoEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ������¼���TextView
	private TextView TV_stationId;
	// ��������վ���������
	private EditText ET_stationName;
	// ������ϵ�������
	private EditText ET_connectPerson;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// �����ʱ������
	private EditText ET_postcode;
	// ����ͨѶ��ַ�����
	private EditText ET_address;
	protected String carmera_path;
	/*Ҫ���������վ��Ϣ��Ϣ*/
	StationInfo stationInfo = new StationInfo();
	/*����վ��Ϣ����ҵ���߼���*/
	private StationInfoService stationInfoService = new StationInfoService();

	private int stationId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸�����վ��Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.stationinfo_edit); 
		TV_stationId = (TextView) findViewById(R.id.TV_stationId);
		ET_stationName = (EditText) findViewById(R.id.ET_stationName);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_postcode = (EditText) findViewById(R.id.ET_postcode);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		stationId = extras.getInt("stationId");
		initViewData();
		/*�����޸�����վ��Ϣ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����վ����*/ 
					if(ET_stationName.getText().toString().equals("")) {
						Toast.makeText(StationInfoEditActivity.this, "����վ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_stationName.setFocusable(true);
						ET_stationName.requestFocus();
						return;	
					}
					stationInfo.setStationName(ET_stationName.getText().toString());
					/*��֤��ȡ��ϵ��*/ 
					if(ET_connectPerson.getText().toString().equals("")) {
						Toast.makeText(StationInfoEditActivity.this, "��ϵ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_connectPerson.setFocusable(true);
						ET_connectPerson.requestFocus();
						return;	
					}
					stationInfo.setConnectPerson(ET_connectPerson.getText().toString());
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(StationInfoEditActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					stationInfo.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡ�ʱ�*/ 
					if(ET_postcode.getText().toString().equals("")) {
						Toast.makeText(StationInfoEditActivity.this, "�ʱ����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_postcode.setFocusable(true);
						ET_postcode.requestFocus();
						return;	
					}
					stationInfo.setPostcode(ET_postcode.getText().toString());
					/*��֤��ȡͨѶ��ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(StationInfoEditActivity.this, "ͨѶ��ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					stationInfo.setAddress(ET_address.getText().toString());
					/*����ҵ���߼����ϴ�����վ��Ϣ��Ϣ*/
					StationInfoEditActivity.this.setTitle("���ڸ�������վ��Ϣ��Ϣ���Ե�...");
					String result = stationInfoService.UpdateStationInfo(stationInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�����վ��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(StationInfoEditActivity.this, StationInfoListActivity.class);
					startActivity(intent); 
					StationInfoEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    stationInfo = stationInfoService.GetStationInfo(stationId);
		this.TV_stationId.setText(stationId+"");
		this.ET_stationName.setText(stationInfo.getStationName());
		this.ET_connectPerson.setText(stationInfo.getConnectPerson());
		this.ET_telephone.setText(stationInfo.getTelephone());
		this.ET_postcode.setText(stationInfo.getPostcode());
		this.ET_address.setText(stationInfo.getAddress());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
