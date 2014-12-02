package com.example.bmi;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




public class Report extends Activity {
	private Button button_back;
	private TextView view_result; 
	private TextView view_suggest; 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);	
		findViews();
		showResults();
		setListeners();
		
	}
	
	private void findViews()
	{
		button_back=(Button)findViewById(R.id.btn_report_bk);
		view_result=(TextView)findViewById(R.id.report_result);
		view_suggest=(TextView)findViewById(R.id.report_suggest);		
	}
	private void setListeners()
	{
		button_back.setOnClickListener(backMain);
	}
	private Button.OnClickListener backMain=new Button.OnClickListener()
	{
		public void onClick(View v)
		{
			Report.this.finish();
		}		
	};
	private void showResults()
	{
		DecimalFormat nf = new DecimalFormat("0.00");
		Bundle bundle = this.getIntent().getExtras();
		double height=Double.parseDouble(bundle.getString("KEY_HEIGHT"))/100;
		double weight=Double.parseDouble(bundle.getString("KEY_WEIGHT"));
	    double BMI = weight/(height*height);
	    view_result.setText(getString(R.string.bmi_result)+nf.format(BMI));
	    if(BMI>25)
	    {
	        showNotification(BMI);
	    	view_suggest.setText(R.string.advice_heavy);
	     
	    }
	    else if(BMI<20)
	    {
	    	view_suggest.setText(R.string.advice_light);
	    }
	    else
	    {
	    	view_suggest.setText(R.string.advice_average);
	    }	    
	}
	protected void showNotification(double BMI)
	{
		/*
		NotificationManager barManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Notification barMsg = new Notification.Builder(Report.this)
				              .setContentText("¼Ú,§A¹L­«Åo!")
				              .setSmallIcon(R.drawable.ic_launcher)
				              .setWhen(System.currentTimeMillis())
				              .build();
		PendingIntent contentIntent=PendingIntent.getActivity
				(
						this,
						0, 
						new Intent(this,BMIActivity.class), 
						PendingIntent.FLAG_UPDATE_CURRENT
				);
		*/
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setContentTitle("Notification Alert, Click Me!");
		mBuilder.setContentText("Hi, This is Android Notification Detail!");

	}
	
}//end of Report
