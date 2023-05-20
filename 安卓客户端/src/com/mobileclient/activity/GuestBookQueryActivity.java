package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.GuestBook;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

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

public class GuestBookQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �������Ա��������
	private EditText ET_title;
	// ����������������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*�û���Ϣ����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	/*��ѯ�����������浽���������*/
	private GuestBook queryConditionGuestBook = new GuestBook();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ������Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.guestbook_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���е��û���Ϣ
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "������";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getRealName();
		} 
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ���������������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionGuestBook.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionGuestBook.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionGuestBook.setTitle(ET_title.getText().toString());
					/*������ɺ󷵻ص�������Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(GuestBookQueryActivity.this, GuestBookListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionGuestBook", queryConditionGuestBook);
					intent.putExtras(bundle);
					startActivity(intent);  
					GuestBookQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
