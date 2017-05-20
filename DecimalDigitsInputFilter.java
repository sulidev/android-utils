package com.sulidev.utils;

import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;

public class DecimalDigitsInputFilter extends DigitsKeyListener {

	private int beforeDecimal, afterDecimal;

	public DecimalDigitsInputFilter(int beforeDecimal, int afterDecimal) {
		// signed: false, decimal point: true
		super(false, true);
		this.beforeDecimal = beforeDecimal;
		this.afterDecimal = afterDecimal;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		String destStr = dest.toString();

		Log.d("text", "dest: " + dest.toString() + " | start,end:" + dstart + "," + dend);
		Log.d("text", "dot_index: " + destStr.indexOf("."));
		Log.d("text", "src: " + source.toString() + " | start,end:" + start + "," + end);

		if (destStr.isEmpty() && source.equals(".")) {
			// input decimal point on empty field
			return "0.";
		} else if (!destStr.contains(".")) {
			// no decimal point
			if (destStr.length() >= beforeDecimal && !source.equals("."))
				return "";
		} else if (source.toString().isEmpty() && destStr.substring(dstart, dend).equals(".")){
			// try to delete decimal point
			if (destStr.substring(0, destStr.indexOf(".")).length() +
					destStr.substring(destStr.indexOf(".")+1, destStr.length()).length() > beforeDecimal) {
				return ".";
			}
		} else if (dstart <= destStr.indexOf(".") && destStr.substring(0, destStr.indexOf(".")).length() >= beforeDecimal){
			// left side of decimal point
			return "";
		} else if(dstart > destStr.indexOf(".") && destStr.substring(destStr.indexOf(".")+1, destStr.length()).length() >= afterDecimal) {
			// right side of decimal point
			return "";
		}

		return super.filter(source, start, end, dest, dstart, dend);
	}

}
