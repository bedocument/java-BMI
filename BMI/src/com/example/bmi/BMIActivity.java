package com.example.bmi;

import java.text.DecimalFormat;

import android.app.AlertDialog;
//對話框加入確認按鈕
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
//沒import下列兩行會onClickListener會錯誤
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//顯示BMI計算機字樣
import android.widget.Toast;
//log功能
import android.util.Log;
//ch22 preference
import android.content.SharedPreferences;

public class BMIActivity extends ActionBarActivity {

	private Button calcbutton;
	private EditText fieldheight;
	private EditText fieldweight;
	private TextView view_result;
	private TextView view_suggest;
	protected static final int MENU_ABOUT = Menu.FIRST;
	protected static final int MENU_QUIT=Menu.FIRST+1;
	private static final String TAG="BMIActivity";
	public static final String PREF="BMI_PREF";
	public static final String PREF_HEIGHT="BMI_HEIGHT";
	/*
	private static final String TAG="BMIActivity";
	Log.d(TAG,"find Views");
	Log.d(TAG,"set Listeners");
	*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.bmi, menu);
		//return true;		
		menu.add(0,MENU_ABOUT,0,"關於...")
		.setIcon(R.drawable.help_browser);

		menu.add(0,MENU_QUIT,0,"結束")
		.setIcon(R.drawable.emblem_unreadable);
	    return super.onCreateOptionsMenu(menu);	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
		//	return true;
		//}
		//return super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
		case MENU_ABOUT:
			 openOptionsDialog();
			 break;
		case MENU_QUIT:
			 finish();
			 break;
		}		
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_bmi);
		Log.v(TAG, "onCreateX");
		findViews();
		restorePrefs();
		setListensers();		
		
	}
	//Restore preferences
	private void restorePrefs()
	{
		SharedPreferences settings = getSharedPreferences(PREF,0);
		String pref_height=settings.getString(PREF_HEIGHT,"");
		if(!"".equals(pref_height))
		{
			fieldheight.setText(pref_height);
			fieldweight.requestFocus();
		}
	}
	private void findViews()
	{
		calcbutton = (Button)findViewById(R.id.submit);
		fieldheight = (EditText)findViewById(R.id.height);
		fieldweight = (EditText)findViewById(R.id.weight);
		view_result=(TextView)findViewById(R.id.result);
		view_suggest=(TextView)findViewById(R.id.suggest);
		
	}
	private void setListensers()
	{
		calcbutton.setOnClickListener(calcBMI);
		Log.v(TAG, "setListensersX");
	}
		
	private Button.OnClickListener calcBMI=new Button.OnClickListener(){
	    public void onClick(View v)
	    {
	    	/*
			DecimalFormat nf = new DecimalFormat("0.00");	
			try
			{
			double height=Double.parseDouble(fieldheight.getText().toString())/100;
			double weight=Double.parseDouble(fieldweight.getText().toString());
			double BMI = weight/(height*height);
			
			view_result.setText("your BMI is"+nf.format(BMI));

			if(BMI>25)
			{
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
			openOptionsDialog();
			}
			catch(Exception obj)
			{
				Toast.makeText(BMIActivity.this, R.string.input_error, Toast.LENGTH_SHORT).show();
			}	
	    	*/
	    	
         Intent intent = new Intent();
         intent.setClass(BMIActivity.this, Report.class);
         Bundle bundle = new Bundle();
         bundle.putString("KEY_HEIGHT", fieldheight.getText().toString());
         bundle.putString("KEY_WEIGHT", fieldweight.getText().toString());
         intent.putExtras(bundle);
         startActivity(intent);        
		
		}
	};
	
	private void openOptionsDialog()
	{
		//點選bmi計算會顯示一個對話框"BMI計算器"
		//Toast.makeText(BMIActivity.this, R.string.BMI, Toast.LENGTH_SHORT).show();
		//節省記憶體寫法
	    //點選menu按鍵 會顯示首頁/確認
		new AlertDialog.Builder(BMIActivity.this)
		.setTitle(R.string.about_title)
		.setMessage(R.string.about_msg)
		.setPositiveButton
		("確認", 
		new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialognterfacex,int i)
			{
			}
		}
	    )
	    .setNegativeButton(R.string.homepage_label, 
	    new DialogInterface.OnClickListener()
	    {
	    	public void onClick(DialogInterface dialognterfacex,int i)
	    	{
	    		Uri uri=Uri.parse(getString(R.string.homepage_uri));
	    		Intent intent=new Intent(Intent.ACTION_VIEW,uri);
	    		startActivity(intent);
	    	}
	    }
	    	    )
		.show();
		
		//浪費記憶體寫法
		//AlertDialog.Builder builder = new AlertDialog.Builder(BMIActivity.this);
		//builder.setTitle("關於 Android BMI");
		//builder.setMessage("Android BMI Calc");
		//builder.show();
	}
	
	public void onRestart()
	{
		
		super.onRestart();
		Log.v(TAG, "onRestartX");		
	}
	public void onstart()
	{
		super.onStart();
		Log.v(TAG, "onStartX");		
	}	
	public void onResume()
	{
		super.onResume();
		Log.v(TAG, "onResumeX");		
	}	
	public void onPause()
	{
		super.onPause();
		Log.v(TAG, "onPauseX");		
		SharedPreferences settings=getSharedPreferences(PREF,0);
		settings.edit()
		        .putString(PREF_HEIGHT,fieldheight.getText().toString())
		        .commit();
		
	}	
	public void onStop()
	{
		super.onStop();
		Log.v(TAG, "onStopX");		
	}
	public void onDestory()
	{
		super.onDestroy();
		Log.v(TAG,"onDesrtoryX");
	}

	
}//end of BMIActivity
