package com.example.tutorialbasics;

import com.example.tutorialbasics.R.color;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;


public class Settings extends Activity implements OnClickListener{

	private ImageButton ib_countdown, ib_ghosting, ib_sets, ib_rest;
	private TextView tv_countdown, tv_ghosting, tv_sets, tv_rest;
	private ImageView iv_countdown, iv_ghosting, iv_sets, iv_rest;
	private NumberPicker picker;
	private ImageButton[] buttons;
	private EditText pickerChild = null;
	private Textwatcher watcher = null;
	private NumberPickerDividerColor colorChanger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		//initialiseer de ImageViews, de TextViews, de ImageButtons en de picker
		iv_countdown = (ImageView)findViewById(R.id.iv_countdown);
		iv_ghosting = (ImageView)findViewById(R.id.iv_ghosting);
		iv_sets = (ImageView)findViewById(R.id.iv_sets);
		iv_rest = (ImageView)findViewById(R.id.iv_rest);
		ib_countdown = (ImageButton)findViewById(R.id.ib_countdown);
		ib_ghosting = (ImageButton)findViewById(R.id.ib_ghosting);
		ib_sets = (ImageButton)findViewById(R.id.ib_sets);
		ib_rest = (ImageButton)findViewById(R.id.ib_rest);
		tv_countdown = (TextView)findViewById(R.id.tv_countdown);
		tv_ghosting = (TextView)findViewById(R.id.tv_ghosting);
		tv_sets = (TextView)findViewById(R.id.tv_sets);
		tv_rest = (TextView)findViewById(R.id.tv_rest);
		picker = (NumberPicker)findViewById(R.id.picker);

		//Haal de SharedPreferences op
		SharedPreferences preferences = getSharedPreferences("GhosterPrefs",Context.MODE_PRIVATE);
		setPreferences(preferences);
		//regelt snelheid picker on longpress (standaard 300 ms), 150ms is helft sneller
		picker.setOnLongPressUpdateInterval(150);
		//pas kleur numberpicker aan
		colorChanger = new NumberPickerDividerColor(picker, this);
		picker=colorChanger.setDividerColorBlue();
		NumberPickerColor.setNumberPickerTextColor(picker, getResources().getColor(R.color.blue));
		//zorg dat de picker niet gebruikt kan worden voordat er op een knop is gedrukt
		picker.setEnabled(false);
		//add Listener to the buttons
		buttons = new ImageButton[]{ib_countdown, ib_ghosting, ib_sets, ib_rest};
		for (ImageButton button:buttons){
			button.setOnClickListener(this);
		}
	}


	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		//knop reeds aan dan wordt hij bij nieuwe klik uitgezet en de picker ook
		if(v.isSelected()){
			v.setSelected(false);
			//welke knop staat uit en dus welk TextView moet worden uitgezet?
			textViewUit(v, R.color.blue);
			picker = colorChanger.setDividerColorBlue();
			NumberPickerColor.setNumberPickerTextColor(picker, getResources().getColor(R.color.blue));
			picker.setEnabled(false);
			/*pickerChild is de EditText v.d. picker. Bij eerste doortoch langs deze code
			 * is dit null. Maar vanaf moment dat iemand op een van de 4 knoppen heeft geduwd
			 * is dit 
			 */
			if (pickerChild != null){
				pickerChild.removeTextChangedListener(watcher);
			}
		} 
		else {
			//Ben niet zeker dat deze twee regels nodig zijn
			if (pickerChild != null){
				pickerChild.removeTextChangedListener(watcher);
			}
			//indien niet aan, zet aan en zet andere knoppen uit
			v.setSelected(true);
			for (ImageButton button:buttons){
				if(button.isSelected() && button!=v){
					button.setSelected(false);
					textViewUit(button, R.color.blue);
				}

			}
			//Er is een knop aangezet dus de picker moet ook actief worden
			picker.setEnabled(true);
			picker = colorChanger.setDividerColorOrange();
			NumberPickerColor.setNumberPickerTextColor(picker, getResources().getColor(R.color.scarlet));
			//welke knop staat aan en dus welk TextView moet worden geupdate?
			textViewAan(v, R.color.scarlet);
		}
	}

	/*
	 * Wanneer deze activity wordt onderbroken, worden
	 * de instellingen opgeslagen.	
	 */
	@Override
	protected void onPause() {
		super.onPause();
		tv_countdown.getText();
		tv_ghosting.getText();
		tv_sets.getText();
		tv_rest.getText();
		SharedPreferences preferences = getSharedPreferences("GhosterPrefs",Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("countdown", tv_countdown.getText().toString());
		editor.putString("ghostings", tv_ghosting.getText().toString());
		editor.putString("sets", tv_sets.getText().toString());
		editor.putString("rest", tv_rest.getText().toString());
		editor.commit();
	}

	/*
	 * Van textViewAan en -uit, 1 methode maken die
	 * vraagt of de huidige kleur blauw is of
	 * scarlet en omzet in de nieuwe andere kleur.
	 */
	public void textViewAan(View v, int color) {
		switch (v.getId()) {
		case R.id.ib_countdown:
			//we zetten de picker op de waarde van het textveld
			String tekst = (String)tv_countdown.getText();
			tekst = tekst.replaceAll("\\D+","");
			//Log.d("TEST ",""+tekst);
			updatePickerTextView (R.id.tv_countdown, 60, 0, getString(R.string.defaultCountdown));
			//Bij eerste keer gebruik is tv.countdown.getText()="" en dit kan je niet als value aan picker doorgeven
			if (tekst!=""){
				picker.setValue(Integer.parseInt(tekst));
			}
			else
				picker.setValue(picker.getMinValue());
			tv_countdown.setTextColor(getResources().getColor(color));
			iv_countdown.setImageResource(R.drawable.ic_countdown_scarlet);
			break;

		case R.id.ib_ghosting:
			//we zetten de picker op de waarde van het textveld
			tekst = (String)tv_ghosting.getText();
			tekst = tekst.replaceAll("\\D+","");
			//Log.d("TEST ",""+tekst);
			updatePickerTextView (R.id.tv_ghosting, 100, 1, getString(R.string.defaultGhosting));
			//Bij eerste keer gebruik is tv.countdown.getText()="" en dit kan je niet als value aan picker doorgeven
			if (tekst!=""){
				picker.setValue(Integer.parseInt(tekst));
			}
			else
				picker.setValue(picker.getMinValue());
			tv_ghosting.setTextColor(getResources().getColor(color));
			iv_ghosting.setImageResource(R.drawable.ic_ghostings_scarlet);
			break;	

		case R.id.ib_sets:
			//we zetten de picker op de waarde van het textveld
			tekst = (String)tv_sets.getText();
			tekst = tekst.replaceAll("\\D+","");
			//Log.d("TEST ",""+tekst);
			updatePickerTextView (R.id.tv_sets, 100, 1, getString(R.string.defaultSets));
			//Bij eerste keer gebruik is tv.countdown.getText()="" en dit kan je niet als value aan picker doorgeven
			if (tekst!=""){
				picker.setValue(Integer.parseInt(tekst));
			}
			else
				picker.setValue(picker.getMinValue());
			tv_sets.setTextColor(getResources().getColor(color));
			//iv_sets.setBackgroundResource((R.drawable.rounded_left));
			iv_sets.setImageResource(R.drawable.ic_sets_scarlet);
			break;

		case R.id.ib_rest:
			//we zetten de picker op de waarde van het textveld
			tekst = (String)tv_rest.getText();
			tekst = tekst.replaceAll("\\D+","");
			//Log.d("TEST ",""+tekst);
			updatePickerTextView (R.id.tv_rest, 360, 0, getString(R.string.defaultRest));
			//Bij eerste keer gebruik is tv.countdown.getText()="" en dit kan je niet als value aan picker doorgeven
			if (tekst!=""){
				picker.setValue(Integer.parseInt(tekst));
			}
			else
				picker.setValue(picker.getMinValue());
			tv_rest.setTextColor(getResources().getColor(color));
			//iv_rest.setBackgroundResource((R.drawable.rounded_left));
			iv_rest.setImageResource(R.drawable.ic_rest_scarlet);
			break;
		}
	}

	public void textViewUit(View v, int color) {
		switch (v.getId()) {
		case R.id.ib_countdown:
			tv_countdown.setTextColor(getResources().getColor(color));
			//tv_countdown.setBackgroundResource((R.drawable.non_rounded));
			//iv_countdown.setBackgroundResource((R.drawable.rounded_left));
			iv_countdown.setImageResource(R.drawable.ic_countdown_blue);
			//ib_countdown.setBackgroundResource(R.drawable.rounded_right);
			break;

		case R.id.ib_ghosting:
			tv_ghosting.setTextColor(getResources().getColor(color));
			//iv_ghosting.setBackgroundResource((R.drawable.rounded_left));
			iv_ghosting.setImageResource(R.drawable.ic_ghostings_blue);
			break;	

		case R.id.ib_sets:
			tv_sets.setTextColor(getResources().getColor(color));
			//iv_sets.setBackgroundResource((R.drawable.rounded_left));
			iv_sets.setImageResource(R.drawable.ic_sets_blue);
			break;

		case R.id.ib_rest:
			tv_rest.setTextColor(getResources().getColor(color));
			//iv_rest.setBackgroundResource((R.drawable.rounded_left));
			iv_rest.setImageResource(R.drawable.ic_rest_blue);
			break;
		}
	}

	/*
	 * Beetje hack-achtige methode om de EditText vd picker
	 * te bekomen. Dit is nodig omdat indien we enkel een 
	 * setOnScrollListener loslaten op de picker, ons TextView
	 * niet wordt geupdate wanneer een gebruiker manueel de EditText
	 * vd picker aanpast. Dus ipv een scrollListener hebben we een 
	 * TextWatcher gebruikt, waardoor zowel bij scrollen als bij
	 * manueel aanpassen, de wijziging wordt doorgegeven aan ons
	 * TextView
	 */
	private EditText findInput(ViewGroup np) {
		int count = np.getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = np.getChildAt(i);
			if (child instanceof ViewGroup) {
				findInput((ViewGroup) child);
			} else if (child instanceof EditText) {
				return (EditText) child;
			}
		}
		return null;
	}

	/*
	 * Deze methode wordt toegepast van zodra er op een
	 * vd 4 knoppen wordt gedrukt.
	 */
	public void updatePickerTextView(int id, int max, int min, String message){
		final int idFinal = id;
		final String messageFinal = message;
		picker.setMaxValue(max);
		picker.setMinValue(min);
		picker.setWrapSelectorWheel(true);

		//we vragen het EditText veld op vd picker
		pickerChild = this.findInput(picker);
		/*
		 * we maken een nieuwe TextWatcher aan met als parameters de picker
		 * het view-ID, de boodschap die we willen weergeven en de huidige
		 * activity (dit is nodig omdat anders in de klasse Textwatcher niet via
		 * findViewById de juiste view kan gevonden worden)
		 */
		watcher = new Textwatcher(picker, idFinal, messageFinal, this);
		pickerChild.addTextChangedListener(watcher);
	}

	/*
	 * methode die in de onCreate wordt aangeroepen om
	 * de settings aan de gebruiker te laten zien.
	 */

	public void setPreferences(SharedPreferences preferences) {
		if (preferences.contains("countdown"))
		{
			tv_countdown.setText(preferences.getString("countdown", ""));

		} else {
			tv_countdown.setText("10 seconds");
		}
		if (preferences.contains("ghostings"))
		{
			tv_ghosting.setText(preferences.getString("ghostings", ""));

		} else {
			tv_ghosting.setText("20 ghostings");
		}
		if (preferences.contains("sets"))
		{
			tv_sets.setText(preferences.getString("sets", ""));

		}
		else {
			tv_sets.setText("2 sets");
		}
		if (preferences.contains("rest"))
		{
			tv_rest.setText(preferences.getString("rest", ""));

		}
		else {
			tv_rest.setText("90 seconds");
		}
	}


}

