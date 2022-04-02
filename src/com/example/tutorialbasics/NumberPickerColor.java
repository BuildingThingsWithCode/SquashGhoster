package com.example.tutorialbasics;

import java.lang.reflect.Field;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;


public class NumberPickerColor {



	public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
	{
		final int count = numberPicker.getChildCount();
		for(int i = 0; i < count; i++){
			View child = numberPicker.getChildAt(i);
			if(child instanceof EditText){
				try{
					Field selectorWheelPaintField = numberPicker.getClass()
							.getDeclaredField("mSelectorWheelPaint");
					selectorWheelPaintField.setAccessible(true);
					((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
					((EditText)child).setTextColor(color);
					numberPicker.invalidate();
					return true;
				}
				catch(NoSuchFieldException e){
					Log.w("setNumberPickerTextColor", e);
				}
				catch(IllegalAccessException e){
					Log.w("setNumberPickerTextColor", e);
				}
				catch(IllegalArgumentException e){
					Log.w("setNumberPickerTextColor", e);
				}
			}
		}
		return false;
	}
}
