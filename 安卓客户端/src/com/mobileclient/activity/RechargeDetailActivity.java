package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Recharge;
import com.mobileclient.service.RechargeService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RechargeDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������¼��ſؼ�
	private TextView TV_id;
	// ������ֵ�û��ؼ�
	private TextView TV_userObj;
	// ������ֵ���ؼ�
	private TextView TV_money;
	// ������ֵʱ��ؼ�
	private TextView TV_chargeTime;
	/* Ҫ����ĳ�ֵ��Ϣ��Ϣ */
	Recharge recharge = new Recharge(); 
	/* ��ֵ��Ϣ����ҵ���߼��� */
	private RechargeService rechargeService = new RechargeService();
	private UserInfoService userInfoService = new UserInfoService();
	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��ֵ��Ϣ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.recharge_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_id = (TextView) findViewById(R.id.TV_id);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_money = (TextView) findViewById(R.id.TV_money);
		TV_chargeTime = (TextView) findViewById(R.id.TV_chargeTime);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RechargeDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    recharge = rechargeService.GetRecharge(id); 
		this.TV_id.setText(recharge.getId() + "");
		UserInfo userInfo = userInfoService.GetUserInfo(recharge.getUserObj());
		this.TV_userObj.setText(userInfo.getRealName());
		this.TV_money.setText(recharge.getMoney() + "");
		this.TV_chargeTime.setText(recharge.getChargeTime());
	} 
}
