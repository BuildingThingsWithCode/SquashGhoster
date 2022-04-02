package com.example.tutorialbasics;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class MyMain extends Activity {

	protected Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//dient om actionbar over de background te plaatsen
		//zodat bij overgang naar countdonw de background niet
		//verplaatst. Dit moet voor setContentView worden gedaan
		//en vanuit XML aanroepen bleek niet te werken
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY); 
		setContentView(R.layout.my_main_layout);
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();
		ImageButton bStart = (ImageButton) findViewById(R.id.start);
		bStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				final ImageButton knop = (ImageButton) v;
				knop.setImageResource(R.drawable.ic_start_pressed);
				handler.postDelayed(new Runnable(){
					public void run(){
						knop.setImageResource(R.drawable.ic_start);
						Intent intent = new Intent(getApplicationContext(),Container_activity.class);
						//de volgende regels zorgt ervoor dat ook al klik je tweemaal op de
						//start knop, de activity maar 1 maal wordt geopend.
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						//verwijderd de standaard animatie bij starten nieuwe activity
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(intent);
						}
				}, 80);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu_layout, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(getApplicationContext(), Settings.class));
			return true;
		case R.id.stopwatch:
			startActivity(new Intent(getApplicationContext(), CornerTimes.class));
			return true;
		case R.id.focus:
			startActivity(new Intent(getApplicationContext(), Focus.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	/*@Override
	protected void onDestroy() {
		System.out.println("MyMain onDestroy is called");
		SharedPreferences preferences = getSharedPreferences("GhosterPrefs",Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		if (preferences.contains("frontPercentage"))
	      {
			editor.remove("frontPercentage");

	      }
		if (preferences.contains("sidesPercentage"))
	      {
			editor.remove("sidesPercentage");

	      }
		if (preferences.contains("backPercentage"))
	      {
			editor.remove("backPercentage");

	      }
		editor.commit();
		super.onDestroy();
	}*/


	
	
	
}



