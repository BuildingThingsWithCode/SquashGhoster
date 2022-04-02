package com.example.tutorialbasics;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.NumberPicker;
import android.widget.TextView;

public class DialogHelper extends Dialog implements OnClickListener{


	private NumberPicker pickerSeconds;
	private NumberPicker pickerTenths;
	private TextView corner;
	private Button okButton;
	private Button cancelButton;
	private Context context;
	public DialogHelper(Activity a, TextView corner) {
		super(a);
		context = a;
		this.corner = corner;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_helper_layout);

		//Volgende regels zijn niet nodig. Staat reeds op wrap_content
		//en zal het scherm enkel volledig opvullen als het niet anders kan
		//DisplayMetrics metrics = a.getResources().getDisplayMetrics();
		//int screenWidth = (int) (metrics.widthPixels * 0.80);
		//getWindow().setLayout(screenWidth, LayoutParams.WRAP_CONTENT); //set below the setContentview

		//this.setTitle("Go fast!");
		TextView titel = (TextView)findViewById(R.id.titel);
		titel.setText(corner.getText().toString().replaceAll("[\\d+\\^.\\n]",""));
		this.getWindow().setBackgroundDrawableResource(R.drawable.rounded_gray_left);
		pickerSeconds = (NumberPicker) this.findViewById(R.id.pickerSeconds);
		pickerTenths = (NumberPicker) this.findViewById(R.id.pickerTenths);

		//set colors for the pickers
		NumberPickerDividerColor colorChanger1 = new NumberPickerDividerColor(pickerSeconds, context);
		NumberPickerDividerColor colorChanger2 = new NumberPickerDividerColor(pickerTenths, context);
		pickerSeconds=colorChanger1.setDividerColorBlue();
		pickerTenths=colorChanger2.setDividerColorBlue();
		NumberPickerColor.setNumberPickerTextColor(pickerSeconds, a.getResources().getColor(R.color.blue));
		NumberPickerColor.setNumberPickerTextColor(pickerTenths, a.getResources().getColor(R.color.blue));
		pickerSeconds.setMaxValue(16);
		pickerSeconds.setMinValue(1);
		pickerTenths.setMaxValue(9);
		pickerTenths.setMinValue(0);
		pickerSeconds.setOnLongPressUpdateInterval(150);
		pickerTenths.setOnLongPressUpdateInterval(150);
		//Log.d("TEST",(corner.getText().toString().replaceAll("[\\D]","")));
		if(corner.getText().toString().replaceAll("[\\D]","") != ""){
			pickerSeconds.setValue(Integer.parseInt(corner.getText().toString().replaceAll("[\\D]",""))/10);
		}
		else pickerSeconds.setValue(3);
		if (corner.getText().toString().replaceAll("[\\D]","")!=""){
			pickerTenths.setValue(Integer.parseInt(corner.getText().toString().replaceAll("[\\D]",""))%10);
		}
		else pickerTenths.setValue(1);
		
		okButton = (Button) this.findViewById(R.id.okButton);
		cancelButton = (Button) this.findViewById(R.id.cancelButton);
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);


	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.okButton:
			okButton.setTextColor(context.getResources().getColor(R.color.scarlet));
			int seconds = pickerSeconds.getValue();
			int tenths = pickerTenths.getValue();
			switch (corner.getId()){
			case R.id.frontLeft_time:
				corner.setText("Front left\n"+seconds+"."+tenths);
				break;
			case R.id.frontRight_time:
				corner.setText("Front right\n"+seconds+"."+tenths);
				break;
			case R.id.sideLeft_time:
				corner.setText("Side left\n"+seconds+"."+tenths);
				break;
			case R.id.sideRight_time:
				corner.setText("Side right\n"+seconds+"."+tenths);
				break;
			case R.id.backLeft_time:
				corner.setText("Back left\n"+seconds+"."+tenths);
				break;
			case R.id.backRight_time:
				corner.setText("Back right\n"+seconds+"."+tenths);
				break;
			}
			break;

		case R.id.cancelButton:
			cancelButton.setTextColor(context.getResources().getColor(R.color.scarlet));
			dismiss();
			break;
		}	
		dismiss();
	}
}





