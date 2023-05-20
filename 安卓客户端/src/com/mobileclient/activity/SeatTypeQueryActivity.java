package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.SeatType;

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

public class SeatTypeQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	/*��ѯ�����������浽���������*/
	private SeatType queryConditionSeatType = new SeatType();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ��λϯ������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seattype_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					/*������ɺ󷵻ص���λϯ��������*/ 
					Intent intent = new Intent();
					intent.setClass(SeatTypeQueryActivity.this, SeatTypeListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionSeatType", queryConditionSeatType);
					intent.putExtras(bundle);
					startActivity(intent);  
					SeatTypeQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
