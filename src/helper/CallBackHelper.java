package helper;

import android.widget.TextView;


public class CallBackHelper {
	private TextView textView;
	ViewsHelper VH = new ViewsHelper();

	
	public final static int RAW = 0;
	public final static int FEETINCHES = 1;
	public final static int INCHES = 2;
	public final static int POUNDS = 3;
	private int callBackType = 0;
	private String val;
	private int addition = 0;
	
	
	
	public CallBackHelper(TextView v){
		this.textView = (TextView) v;
	}
	public CallBackHelper(TextView v, int type, int addition){
		this.textView = v;
		this.callBackType = type;
		this.addition = addition;
	}

	private void _updateView(String str){
		switch (this.callBackType){
			case 0: this.val = str;
				break;
			case 1:	this.val = StatsHelper.inches_to_feet(Integer.parseInt(str));
				break;
			case 2: this.val = str + "\"";
				break;
			case 3: this.val = str + " lbs.";
				break;
			case 4: if(str.equals("100")) str = "00";
					this.val = "#" + str;
				break;
		}
		VH.rViews(this.textView, this.val);
	}
	public void updateView(String str){
		_updateView(String.valueOf(this.addition + Integer.parseInt(str)));
	}
	
	public void updateView(int i){
		this._updateView(String.valueOf(i + addition));
	}
}
