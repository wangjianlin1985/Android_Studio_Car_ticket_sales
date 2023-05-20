package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.StationInfo;

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

public class StationInfoQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ��������վ���������
	private EditText ET_stationName;
	// ������ϵ�������
	private EditText ET_connectPerson;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// �����ʱ������
	private EditText ET_postcode;
	/*��ѯ�����������浽���������*/
	private StationInfo queryConditionStationInfo = new StationInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ����վ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.stationinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_stationName = (EditText) findViewById(R.id.ET_stationName);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_postcode = (EditText) findViewById(R.id.ET_postcode);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionStationInfo.setStationName(ET_stationName.getText().toString());
					queryConditionStationInfo.setConnectPerson(ET_connectPerson.getText().toString());
					queryConditionStationInfo.setTelephone(ET_telephone.getText().toString());
					queryConditionStationInfo.setPostcode(ET_postcode.getText().toString());
					/*������ɺ󷵻ص�����վ��Ϣ�������*/ 
					Intent intent = new Intent();
					intent.setClass(StationInfoQueryActivity.this, StationInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionStationInfo", queryConditionStationInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					StationInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
