package com.example.tutorialbasics;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.NumberPicker;

public class NumberPickerDividerColor {
	private Object picker;
	private Class<?> classPicker;
	private Context context;

	/**
	 * De constructor
	 * @param o de numberpicker die we als parameter meegeven
	 * @param x de huidige context die de activity waarin dit object wordt gebruikt aanreikt
	 */
	public NumberPickerDividerColor(Object o, Context x) {
		this.picker = o;
		classPicker = picker.getClass();
		this.context = x;
	}
	
	public NumberPicker setDividerColorBlue(){
		try {
			Field divider = classPicker.getDeclaredField("mSelectionDivider");
			divider.setAccessible(true);
			/*
			 * de context is nodig omdat de klasse niet rechtstreeks
			 * toegang heeft tot de context en dus geen toegand tot
			 * de drawable
			 */
			divider.set(picker, context.getResources().getDrawable(
            R.drawable.np_numberpicker_selection_divider_blue));
			
		}
		catch (NoSuchFieldException x) {
			x.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//Opgepast we casten hier
		return (NumberPicker)picker;
	}
	
	public NumberPicker setDividerColorOrange(){
		try {
			Field divider = classPicker.getDeclaredField("mSelectionDivider");
			divider.setAccessible(true);
			divider.set(picker, context.getResources().getDrawable(
            R.drawable.np_numberpicker_selection_divider_scarlet));
			
		}
		catch (NoSuchFieldException x) {
			x.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//Opgepast we casten hier
		return (NumberPicker)picker;
	}
}


