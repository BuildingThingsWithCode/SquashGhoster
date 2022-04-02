package com.example.tutorialbasics;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountDown_fragment extends Fragment {

	private int totalCount;
	private int checkEvery = 90;
	private TextView countdown;
	private CountDownTimer timer;
	private TextToSpeech speech;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		//this.speech = ((Container_activity)getActivity()).speech;
		//speech.setSpeechRate((float) 1.2);
		/**
		 * Inflate the layout for this fragment
		 */
		return inflater.inflate(
				R.layout.countdown_fragment_layout, container, false);

	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		countdown = (TextView)getView().findViewById(R.id.countdown);
		countdown.setBackgroundResource(R.drawable.blue_background);
		timer =new CountDownTimer(totalCount, checkEvery) {

			public void onTick(long millisUntilFinished) {
				//gewoon om de layout consistent te houden
				if (millisUntilFinished < 10000 && (millisUntilFinished % 1000 /10) < 10){
					countdown.setText("0" + millisUntilFinished / 1000 + " : 0"+ (millisUntilFinished%1000)/10);
				}
				if (millisUntilFinished >= 10000 && (millisUntilFinished % 1000 /10) < 10){
					countdown.setText(millisUntilFinished / 1000 + " : 0"+ (millisUntilFinished%1000)/10);
				}
				if (millisUntilFinished < 10000 && (millisUntilFinished % 1000 /10) >= 10){
					countdown.setText("0" + millisUntilFinished / 1000 + " : "+ (millisUntilFinished%1000)/10);
				}
				if (millisUntilFinished >= 10000 && (millisUntilFinished % 1000 /10) >= 10){
					countdown.setText(millisUntilFinished / 1000 + " : "+ (millisUntilFinished%1000)/10);
				}

				//Vanaf 5 seconden te gaan, geen rest bij deling dan oranje, wel rest dan blauw
				if (millisUntilFinished < 5000 && (millisUntilFinished/1000) % 2 == 0) {
					int x = (int)millisUntilFinished/1000;
					switch (x){
					case 0: 
						//speech.speak("five", TextToSpeech.QUEUE_FLUSH, null);
						countdown.setBackgroundResource(R.drawable.scarlet_background);
						break;
					case 2:
						//speech.speak("four", TextToSpeech.QUEUE_FLUSH, null);
						countdown.setBackgroundResource(R.drawable.blue_background);
						break;
					default:
						//speech.speak("two", TextToSpeech.QUEUE_FLUSH, null);
						countdown.setBackgroundResource(R.drawable.gray_background);
					}
				}
				if (millisUntilFinished < 5000 && (millisUntilFinished/1000) % 2 == 1) {
					if (millisUntilFinished/1000 == 3){
						//speech.speak("three", TextToSpeech.QUEUE_FLUSH, null);
						countdown.setBackgroundResource(R.drawable.scarlet_background);
					}
					else	
						//speech.speak("one", TextToSpeech.QUEUE_FLUSH, null);
						countdown.setBackgroundResource(R.drawable.gray_background);
				}
			}
			public void onFinish() {
				countdown.setText("00 : 00");
				((Container_activity)getActivity()).swapToGhosting();
			}
		}.start();

	}
	
	/*
	 * Setter om de het aantal seconden dat er wordt afgeteld
	 * te kunnen aanpassen
	 */
	public void setTotalCount(int i){
		this.totalCount = i * 1000;
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		timer.cancel();
	}


	
	
	
}

