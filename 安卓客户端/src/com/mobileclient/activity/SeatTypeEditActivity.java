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
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_seatTypeId;
	// 声明席别名称输入框
	private EditText ET_seatTypeName;
	protected String carmera_path;
	/*要保存的座位席别信息*/
	SeatType seatType = new SeatType();
	/*座位席别管理业务逻辑层*/
	private SeatTypeService seatTypeService = new SeatTypeService();

	private int seatTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-修改座位席别");
		// 设置当前Activity界面布局
		setContentView(R.layout.seattype_edit); 
		TV_seatTypeId = (TextView) findViewById(R.id.TV_seatTypeId);
		ET_seatTypeName = (EditText) findViewById(R.id.ET_seatTypeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		seatTypeId = extras.getInt("seatTypeId");
		initViewData();
		/*单击修改座位席别按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取席别名称*/ 
					if(ET_seatTypeName.getText().toString().equals("")) {
						Toast.makeText(SeatTypeEditActivity.this, "席别名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_seatTypeName.setFocusable(true);
						ET_seatTypeName.requestFocus();
						return;	
					}
					seatType.setSeatTypeName(ET_seatTypeName.getText().toString());
					/*调用业务逻辑层上传座位席别信息*/
					SeatTypeEditActivity.this.setTitle("正在更新座位席别信息，稍等...");
					String result = seatTypeService.UpdateSeatType(seatType);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到座位席别管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(SeatTypeEditActivity.this, SeatTypeListActivity.class);
					startActivity(intent); 
					SeatTypeEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* 初始化显示编辑界面的数据 */
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
