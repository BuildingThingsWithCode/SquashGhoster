package com.example.tutorialbasics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;

import com.example.tutorialbasics.R.color;
import com.example.tutorialbasics.R.drawable;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.PorterDuff;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class Ghosting_fragment extends Fragment  {
	private VisibilityTimer myTimer;
	private Boolean flag=false;
	private TextToSpeech speech;

	//views
	private TextView frontLeft;
	private TextView frontRight;
	private TextView sideLeft;
	private TextView sideRight;
	private TextView backLeft;
	private TextView backRight;

	//view times
	private double frontLeftTime;
	private double frontRightTime;
	private double sideLeftTime;
	private double sideRightTime;
	private double backLeftTime;
	private double backRightTime;

	//# ghostings,-sets and amount of rest
	private int ghostings;
	private int sets;
	//private int rest = 5;
	//percentages van de hoeken
	private int frontPercentage;
	private int sidesPercentage;
	private int backPercentage;
	//hulpvariabelen
	private TextView view;
	private double count;
	private long backToMiddle = 800;
	private int hasGhosted = 0;
	private int checkEvery = 100;
	private double totaleAfstand = 0;
	private Iterator<String> iterator = null;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		this.speech = ((Container_activity)getActivity()).speech;
		speech.setSpeechRate((float) 1.2);
		return inflater.inflate(R.layout.ghosting_fragment_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences preferences = getActivity().getSharedPreferences("GhosterPrefs",Context.MODE_PRIVATE);
		//We zetten de tijden
		loadTimes(preferences);
		//(TextView)getView().findViewById(R.id.countdown);
		//set alle views
		frontLeft = (TextView)getView().findViewById(R.id.frontLeft);
		frontRight = (TextView)getView().findViewById(R.id.frontRight);
		sideLeft = (TextView)getView().findViewById(R.id.sideLeft);
		sideRight = (TextView)getView().findViewById(R.id.sideRight);
		backLeft = (TextView)getView().findViewById(R.id.backLeft);
		backRight = (TextView)getView().findViewById(R.id.backRight);
		iterator = null;
		chooseView();
		myTimer = new VisibilityTimer((long)count, checkEvery);
		myTimer.start();

	}

	@Override
	public void onPause() {
		myTimer.cancel();
		super.onPause();
	}



	//timer class
	class VisibilityTimer extends CountDownTimer {
		//de gekozen view wordt visible
		@SuppressWarnings("deprecation")
		public VisibilityTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			view.setVisibility(View.VISIBLE);
			speech.speak(view.getText().toString().toString().replaceAll("[\\d+\\^.\\n]",""), TextToSpeech.QUEUE_FLUSH, null);
			//view.setBackgroundDrawable(getResources().getDrawable(R.drawable.leftfront));
		}

		/*
		 *we kijken na of count (=de tijd die bij de gekozen view hoort, dus
		 *de tijd die de gebruiker heeft ingesteld om bijvoorbeeld van de T naar
		 *"side left" te lopen en terug naar de T te gaan) - de tijd die versteken
		 *is na het visible maken van de gekozen view >= count - backToMiddle.
		 *Dit dient om voordat count volledig is afgelopen de gekozen view
		 *onzichtbaar te maken. Dit om de gebruiker aan te geven dat hij terug naar
		 *de T moet lopen.
		 */
		@Override
		public void onTick(long millisUntilFinished) {
			if ((count-millisUntilFinished) >= (count - backToMiddle)){
				view.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onFinish() {
			hasGhosted++;
			//Log.d("TEST", "Ghosting "+hasGhosted+" onFinish");
			if (hasGhosted < ghostings){
				chooseView();
				myTimer = new VisibilityTimer((long)count, checkEvery);
				myTimer.start();
			}
			else {
				//Log.d("TEST", "a="+(Container_activity)getActivity());
				hasGhosted = 0;
				if (sets-1 > 0 ){
					sets--;
					//testen if getActivity()==null want indien gebruiker op back
					//knop drukt, dan is activity null en krijgen we nullpointerexc.
					//boolean isHijEr = getActivity()!=null;
					//Log.d("Activity=",""+isHijEr);
					if (getActivity()==null){
						return;
					}
					else
						((Container_activity)getActivity()).swapToCountDown();
				}
				else {
					//klaar met alles
					//Log.d("TEST",""+Ghosting_fragment.totaleAfstand);
					ActionBar actionBar = (ActionBar)getActivity().getActionBar();
					actionBar.show();
					Context context = (Container_activity)getActivity();
					BigDecimal bd = new BigDecimal(totaleAfstand).setScale(2, RoundingMode.HALF_EVEN);
					totaleAfstand = bd.doubleValue();
					CharSequence text = "Totale afstand= "+totaleAfstand;
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					return;
				}
			}
		}
	}

	/*
	 * Hulpmethode om de onCreate overzichtelijk te houden.
	 * Deze methode zorgt ervoor dat de preferences geladen
	 * worden
	 */
	public void loadTimes(SharedPreferences preferences) {
		if (preferences.contains("FLseconds"))
		{
			frontLeftTime = (Double.parseDouble(preferences.getString("FLseconds","")+"."+preferences.getString("FLtenths",""))*1000);

		} else {
			frontLeftTime = 3.1*1000;
		}
		if (preferences.contains("FRseconds"))
		{
			frontRightTime = (Double.parseDouble(preferences.getString("FRseconds", "")+"."+preferences.getString("FRtenths",""))*1000);

		} else {
			frontRightTime = 3.1*1000;
		}
		if (preferences.contains("SLseconds"))
		{
			sideLeftTime = (Double.parseDouble(preferences.getString("SLseconds", "")+"."+preferences.getString("SRtenths",""))*1000);

		}else {
			sideLeftTime = 3.1*1000;
		}
		if (preferences.contains("SRseconds"))
		{
			sideRightTime = (Double.parseDouble(preferences.getString("SRseconds", "")+"."+preferences.getString("SRtenths",""))*1000);

		}else {
			sideRightTime = 3.1*1000;
		}
		if (preferences.contains("BLseconds"))
		{
			backLeftTime = (Double.parseDouble(preferences.getString("BLseconds", "")+"."+preferences.getString("BLtenths",""))*1000);

		} else {
			backLeftTime = 3.1*1000;
		}
		if (preferences.contains("BRseconds"))
		{
			backRightTime = (Double.parseDouble(preferences.getString("BRseconds", "")+"."+preferences.getString("BRtenths",""))*1000);

		} else {
			backRightTime = 3.1*1000;
		}
	}

	//hulpmethode om willekeurig een van de 6 views te kiezen
	public void chooseView() {
		//wanneer de gebruiker de default focusvalues niet heeft aangepast
		if (MyProperties.getInstance().getPercentages() == null) {
			Random number = new Random();
			int x = number.nextInt(6) + 1;
			Log.d("Gewone (geen %)","view="+x);
			switch (x){
			case 1 : 
				view = frontLeft;
				count = frontLeftTime;
				totaleAfstand = totaleAfstand+4.73;
				break;
			case 2 : 
				view = frontRight;
				count = frontRightTime;
				totaleAfstand = totaleAfstand+4.73;
				break;
			case 3 : 
				view = sideLeft;
				count = sideLeftTime;
				totaleAfstand = totaleAfstand+2.94;
				break;
			case 4 : 
				view = sideRight;
				count = sideRightTime;
				totaleAfstand = totaleAfstand+2.94;
				break;
			case 5 : 
				view = backLeft;
				count = backLeftTime;
				totaleAfstand = totaleAfstand+4.34;
				break;
			case 6 : 
				view = backRight;
				count = backRightTime;
				totaleAfstand = totaleAfstand+4.34;
				break;
			}
		}
		/*
		 * de gebruiker heeft de default focusvalues aangepast
		 * en we gaan nu de Arraylist aanmaken met daarin alle
		 * hoeken in een geshuffelde volgorde
		 */
		else if (iterator == null){
			int[] percentages = MyProperties.getInstance().getPercentages();
			frontPercentage = percentages[0];
			sidesPercentage = percentages[1];
			backPercentage = percentages[2];
			AantalHoekenBerekenaar berekenaar = new AantalHoekenBerekenaar(frontPercentage,sidesPercentage,backPercentage,ghostings);
			int[] aantallen = berekenaar.aantallen();
			totaleAfstand = totaleAfstand + (aantallen[0] * 4.73 + aantallen[1] * 2.94 + aantallen[2] * 4.34);
			Log.d("aantallen1= ",""+aantallen[0]);
			Log.d("aantallen2= ",""+aantallen[1]);
			Log.d("aantallen3= ",""+aantallen[2]);
			ArrayList<String> volgorde = new ArrayList<String>();
			for (int i=0; i<aantallen[0];i++){
				volgorde.add("front");
			}
			for (int i=0; i<aantallen[1];i++){
				volgorde.add("sides");
			}
			for (int i=0; i<aantallen[2];i++){
				volgorde.add("back");
			}
			Collections.shuffle(volgorde);
			iterator = volgorde.iterator();
			hoekKeuze();


		}else {
			//de Arraylist is reeds aangemaakt 
			hoekKeuze();	
		}
	}

	/*
	 * Hulpmethode die de hoek kiest wanneer een gebruiker
	 * de percentages heeft aangepast.
	 */
	public void hoekKeuze() {
		String hoek = iterator.next();
		Random number = new Random();
		int x = number.nextInt(2) + 1;
		if (hoek == "front"){
			switch (x){
			case 1:
				view = frontLeft;
				count = frontLeftTime;
				//totaleAfstand = totaleAfstand+4.73;
				break;
			case 2 : 
				view = frontRight;
				count = frontRightTime;
				//totaleAfstand = totaleAfstand+4.73;
				break;
			}
		}
		else if (hoek == "sides"){
			switch (x){
			case 1 : 
				view = sideLeft;
				count = sideLeftTime;
				//totaleAfstand = totaleAfstand+2.94;
				break;
			case 2 : 
				view = sideRight;
				count = sideRightTime;
				//totaleAfstand = totaleAfstand+2.94;
				break;
			}

		}
		else if (hoek == "back"){
			switch (x){
			case 1 : 
				view = backLeft;
				count = backLeftTime;
				//totaleAfstand = totaleAfstand+4.34;
				break;
			case 2 : 
				view = backRight;
				count = backRightTime;
				//totaleAfstand = totaleAfstand+4.34;
				break;
			}

		}
	}
	//setter voor ghostings en sets
	public void setGhostings (int i){
		this.ghostings = i;
	}

	public void setSets (int i){
		this.sets = i;
	}
}