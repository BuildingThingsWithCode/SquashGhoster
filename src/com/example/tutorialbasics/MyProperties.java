package com.example.tutorialbasics;

/*
*Singleton methode met ENUM gebruiken
*/
public class MyProperties {
	private static MyProperties mInstance = new MyProperties();
	private int[] percentages = null;
	private MyProperties() {
		// Exists only to defeat instantiation.
	}

	public static synchronized MyProperties getInstance(){
		if(mInstance == null){
			mInstance = new MyProperties();
		}
		return mInstance;
	}

	public void setPercentages(int[] percentages){
		this.percentages = percentages;
	}
	public int[] getPercentages(){
		return percentages;
	}
}
