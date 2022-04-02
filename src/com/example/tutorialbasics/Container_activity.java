package com.example.tutorialbasics;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class Container_activity extends Activity implements OnInitListener{

    private CountDown_fragment countDown;
	private Ghosting_fragment ghosting;
	private FragmentTransaction transaction;
	public TextToSpeech speech;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY); 
		setContentView(R.layout.container_activity_layout);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		/*
		 * Volgende regel zorgt ervoor dat indien je de hardware
		 * volumeknoppen gebruikt, de juiste stream wordt gewijzigd.
		 * Nl. het speech volume en niet het volume van de ringtone.
		 */
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		speech=new TextToSpeech(this, this); 
		transaction = getFragmentManager().beginTransaction();
		countDown = new CountDown_fragment();
		ghosting = new Ghosting_fragment();
		//preferences instellen van Ghosting_Fragment
		//Haal de SharedPreferences op
		SharedPreferences preferences = getSharedPreferences("GhosterPrefs",Context.MODE_PRIVATE);
		if (preferences.contains("ghostings"))
		{

			ghosting.setGhostings(Integer.parseInt((preferences.getString("ghostings", "").replaceAll("\\D+",""))));

		} else {
			ghosting.setGhostings(20);
	      }
		if (preferences.contains("sets"))
		{

			ghosting.setSets(Integer.parseInt(preferences.getString("sets", "").replaceAll("\\D+","")));
		} else {
			ghosting.setSets(2);
		}
		
		if (preferences.contains("countdown"))
		{
			countDown.setTotalCount(Integer.parseInt(preferences.getString("countdown", "").replaceAll("\\D+","")));
		}else {
			countDown.setTotalCount(10);
		}
		
		transaction.add(R.id.fragment_container,countDown).commit();
	}



	@Override
	protected void onPause() {
		if(speech !=null){
	         speech.stop();
	         //speech.shutdown();
		}
		finish();
		super.onPause();
	}
	
	

	@Override
	protected void onDestroy() {
		if(speech !=null){
	         speech.stop();
	         speech.shutdown();
		}
		super.onDestroy();
	}



	public void swapToGhosting(){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, ghosting);
		//transaction.addToBackStack(null);
		transaction.commitAllowingStateLoss();
	}

	public void swapToCountDown(){
		//Haal de SharedPreferences op
		SharedPreferences preferences = getSharedPreferences("GhosterPrefs",Context.MODE_PRIVATE);
		//We stellen de rust tussen de sets in
		if (preferences.contains("rest"))
		{
			countDown.setTotalCount(Integer.parseInt(preferences.getString("rest", "").replaceAll("\\D+","")));       
		} else {
			countDown.setTotalCount(90);
		}
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, countDown);
		//transaction.addToBackStack(null);
		transaction.commitAllowingStateLoss();
	}

	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if(status != TextToSpeech.ERROR){
			speech.setLanguage(Locale.UK);
		}				
	}
}