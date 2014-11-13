package ji.ruucm.android.vertical_view.fragment;

import ji.ruucm.android.margin_animation.MediaAssets;
import android.R;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;

public class Chat_ExamData {

	public int type;
	public CharSequence data1;
	public CharSequence data2;
	public CharSequence emo;
	public int data3;

	public Chat_ExamData(byte b, String string, String format, int emdata3o) {

		type = b;
		data1 = string;
		data2 = format;
		
		data3 = emdata3o;
		
		
		
		

	}

}
