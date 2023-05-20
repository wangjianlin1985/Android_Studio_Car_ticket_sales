package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;
public class RechargeAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-��ӳ�ֵ��Ϣ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.recharge_add); 
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
		// ���������б�ķ��
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
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������ӳ�ֵ��Ϣ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��ֵ���*/ 
					if(ET_money.getText().toString().equals("")) {
						Toast.makeText(RechargeAddActivity.this, "��ֵ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_money.setFocusable(true);
						ET_money.requestFocus();
						return;	
					}
					recharge.setMoney(Float.parseFloat(ET_money.getText().toString()));
					 
					/*����ҵ���߼����ϴ���ֵ��Ϣ��Ϣ*/
					RechargeAddActivity.this.setTitle("�����ϴ���ֵ��Ϣ��Ϣ���Ե�...");
					String result = rechargeService.AddRecharge(recharge);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص���ֵ��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(RechargeAddActivity.this, RechargeListActivity.class);
					startActivity(intent); 
					RechargeAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
