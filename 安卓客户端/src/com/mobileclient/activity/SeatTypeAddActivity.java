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
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明席别名称输入框
	private EditText ET_seatTypeName;
	protected String carmera_path;
	/*要保存的座位席别信息*/
	SeatType seatType = new SeatType();
	/*座位席别管理业务逻辑层*/
	private SeatTypeService seatTypeService = new SeatTypeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加座位席别");
		// 设置当前Activity界面布局
		setContentView(R.layout.seattype_add); 
		ET_seatTypeName = (EditText) findViewById(R.id.ET_seatTypeName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加座位席别按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取席别名称*/ 
					if(ET_seatTypeName.getText().toString().equals("")) {
						Toast.makeText(SeatTypeAddActivity.this, "席别名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_seatTypeName.setFocusable(true);
						ET_seatTypeName.requestFocus();
						return;	
					}
					seatType.setSeatTypeName(ET_seatTypeName.getText().toString());
					/*调用业务逻辑层上传座位席别信息*/
					SeatTypeAddActivity.this.setTitle("正在上传座位席别信息，稍等...");
					String result = seatTypeService.AddSeatType(seatType);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到座位席别管理界面*/ 
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
