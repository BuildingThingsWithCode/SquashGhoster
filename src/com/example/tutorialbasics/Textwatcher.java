package com.example.tutorialbasics;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Textwatcher implements TextWatcher {

	/*
	 * class die de wijzigingen aan de numberpicker
	 * in de gaten houdt. Dit is handiger dan een 
	 * onScrollListener want die houdt enkel een scroll
	 * in de gaten. Deze houdt elke wijziging aan 
	 * de EditText in de gaten of dit nu via scroll
	 * of via manuele wijziging gebeurd.
	 */


	private NumberPicker picker;
	private int viewID;
	private String message;
	private Activity activity;

	public Textwatcher(NumberPicker picker, int viewID, String message, Activity activity){
		this.picker = picker;
		this.viewID = viewID;
		this.message = message;
		this.activity = activity;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.toString().length() != 0) {
			Integer value = Integer.parseInt(s.toString());
			if (value >= picker.getMinValue()) {
				picker.setValue(value);
				//Log.d("TEST","value in textwatcher= "+value);
				TextView view = (TextView) activity.findViewById(viewID);
				//we willen enkel de "seconden" of "sets" of "ghostings" vd message niet het getal wat er 
				//standaard voorstaat
				view.setText(Integer.toString(picker.getValue()) + (message.replaceAll("[0-9]","")));
			}
		}
	}
}



