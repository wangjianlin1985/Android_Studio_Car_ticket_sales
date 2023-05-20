package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.UserInfo;

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

public class UserInfoQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����û��������
	private EditText ET_user_name;
	// �������������
	private EditText ET_realName;
	// �������ڿؼ�
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	// �������֤�����
	private EditText ET_cardNumber;
	// �������������
	private EditText ET_city;
	/*��ѯ�����������浽���������*/
	private UserInfo queryConditionUserInfo = new UserInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ�û���Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		ET_realName = (EditText) findViewById(R.id.ET_realName);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		ET_cardNumber = (EditText) findViewById(R.id.ET_cardNumber);
		ET_city = (EditText) findViewById(R.id.ET_city);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionUserInfo.setUser_name(ET_user_name.getText().toString());
					queryConditionUserInfo.setRealName(ET_realName.getText().toString());
					if(cb_birthday.isChecked()) {
						/*��ȡ��������*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionUserInfo.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionUserInfo.setBirthday(null);
					} 
					queryConditionUserInfo.setCardNumber(ET_cardNumber.getText().toString());
					queryConditionUserInfo.setCity(ET_city.getText().toString());
					/*������ɺ󷵻ص��û���Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(UserInfoQueryActivity.this, UserInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionUserInfo", queryConditionUserInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					UserInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
