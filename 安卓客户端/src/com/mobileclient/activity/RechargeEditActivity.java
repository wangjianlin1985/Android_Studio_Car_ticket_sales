package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Recharge;
import com.mobileclient.service.RechargeService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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

public class RechargeEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ������¼���TextView
	private TextView TV_id;
	// ������ֵ�û�������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*��ֵ�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// ������ֵ��������
	private EditText ET_money;
	// ������ֵʱ�������
	private EditText ET_chargeTime;
	protected String carmera_path;
	/*Ҫ����ĳ�ֵ��Ϣ��Ϣ*/
	Recharge recharge = new Recharge();
	/*��ֵ��Ϣ����ҵ���߼���*/
	private RechargeService rechargeService = new RechargeService();

	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸ĳ�ֵ��Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.recharge_edit); 
		TV_id = (TextView) findViewById(R.id.TV_id);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���еĳ�ֵ�û�
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getRealName();
		}
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ����ͼ����������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				recharge.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_money = (EditText) findViewById(R.id.ET_money);
		ET_chargeTime = (EditText) findViewById(R.id.ET_chargeTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		initViewData();
		/*�����޸ĳ�ֵ��Ϣ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��ֵ���*/ 
					if(ET_money.getText().toString().equals("")) {
						Toast.makeText(RechargeEditActivity.this, "��ֵ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_money.setFocusable(true);
						ET_money.requestFocus();
						return;	
					}
					recharge.setMoney(Float.parseFloat(ET_money.getText().toString()));
					/*��֤��ȡ��ֵʱ��*/ 
					if(ET_chargeTime.getText().toString().equals("")) {
						Toast.makeText(RechargeEditActivity.this, "��ֵʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_chargeTime.setFocusable(true);
						ET_chargeTime.requestFocus();
						return;	
					}
					recharge.setChargeTime(ET_chargeTime.getText().toString());
					/*����ҵ���߼����ϴ���ֵ��Ϣ��Ϣ*/
					RechargeEditActivity.this.setTitle("���ڸ��³�ֵ��Ϣ��Ϣ���Ե�...");
					String result = rechargeService.UpdateRecharge(recharge);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���ֵ��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(RechargeEditActivity.this, RechargeListActivity.class);
					startActivity(intent); 
					RechargeEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    recharge = rechargeService.GetRecharge(id);
		this.TV_id.setText(id+"");
		for (int i = 0; i < userInfoList.size(); i++) {
			if (recharge.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_money.setText(recharge.getMoney() + "");
		this.ET_chargeTime.setText(recharge.getChargeTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
