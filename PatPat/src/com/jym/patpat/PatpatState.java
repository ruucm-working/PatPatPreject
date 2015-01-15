package com.jym.patpat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jym.helper.TextPref;
import com.jym.helper.XmlMapping;
import com.jym.service.TaskTimer;

public class PatpatState implements IPatpatViewState {

	public static int clickcount_3_1 = 0;
	private long clickedTime = 0;
	
	int level;
	TextPref characterPref;
	TextPref clickPref;

	public static boolean overlapAnimSwitch = true;
	PatpatView mViewContext;

	HashMap<String, Object> charterInfo;

	ArrayList<String> howl = new ArrayList<String>();

	String waitAnimePath;
	String clickAnimePath;

	public PatpatState(PatpatView viewContext) {
		mViewContext = viewContext;
		
		try {
			clickPref = new TextPref("mnt/sdcard/SsdamSsdam/clickpref.pref");
			characterPref = new TextPref("mnt/sdcard/SsdamSsdam/entitypref.pref");
		} catch (Exception e) {
			e.printStackTrace();
		}

		clickPref.Ready();
		clickcount_3_1 = clickPref.ReadInt("ClickCount_3_1", 0);
		clickPref.EndReady();

		characterPref.Ready();
		level = characterPref.ReadInt("level", 0);
		characterPref.EndReady();

		try{
			charterInfo = XmlMapping.levelMapping("test1", viewContext.Context, level);
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		if(charterInfo != null) {
			howl 			= (ArrayList<String>) charterInfo.get("howl");
			waitAnimePath 	= (String) charterInfo.get("wait");
			clickAnimePath	= (String) charterInfo.get("click");
		}
	}

	public void Draw(PatpatView viewContext) {
		Log.e("stop_double_Draw", "Draw_setState_Lv3_1State_");
		viewContext.setState(new Lv3_1WaitState());
	}

	public boolean NeedRedraw() {
		return false;
	}

	public void OnClick(PatpatView viewContext) {

	}

	private class Lv3_1WaitState implements IPatpatViewState {
		public PatpatView context;

		public void Draw(PatpatView viewContext) {
			Log.d("updateClickIntent_right", "Draw");
		}

		public void OnClick(PatpatView viewContext) {

			String text = null;
			int textcode = (int)(Math.random()*howl.size());
			text = howl.get(textcode);

			TaskTimer.clickCount++;
			TaskTimer.ePref.Ready();
			TaskTimer.ePref.WriteInt("click_count", TaskTimer.clickCount);
			TaskTimer.ePref.CommitWrite();

			Toast.makeText(PatpatView.Context, text, Toast.LENGTH_SHORT).show();

			Log.w("seperated_ClickCount", "OnClick");

			viewContext.addAnimatable(new Lv3ClickHeadAnim());
		}

		public void OnClickBody(PatpatView patpatView) {
			clickedTime = SystemClock.uptimeMillis();
		}

		public boolean NeedRedraw() {
			return false;
		}

		@Override
		public void OnEvolve(PatpatView coinBlockView) {
			Log.d("bugfix", "진화합니다~~");
			coinBlockView.setState(new PatpatState(coinBlockView));
		}

		@Override
		public void OnOften(PatpatView viewContext) {
			clickedTime = SystemClock.uptimeMillis();
			viewContext.addAnimatable(new Lv3OftenAnim());
		}

		@Override
		public void OnHeadsetConnected(PatpatView viewContext) {}

		@Override
		public void OnHeadsetDisconnected(PatpatView viewContext) {}

		@Override
		public void OnClickLeg(PatpatView patpatView) {
			// TODO Auto-generated method stub
			
		}
	}

	private class Lv3Animation implements IAnimatable {
		// 진동할때 올라오고, 상단에 남는 드로블
		private int flowerRaise = 4;
		private int animstage = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {}
	}

	@Override
	public void OnEvolve(PatpatView coinBlockView) {}

	@Override
	public void OnOften(PatpatView coinBlockView) {}

	private class Lv3OftenAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.beautygirl2_angry0);
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.animation_test_body);
				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);
			}
		}
	}

	private class Lv3ClickHeadAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			// Draw the brick at bottom
			int imageResource = mViewContext.Context.getResources().getIdentifier(clickAnimePath,
					"drawable",mViewContext.Context.getPackageName());

			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01,imageResource);

				Log.i("refreshing_RemoteView","setImageViewResource");
				spriteVib++;
			}			
			else {
				Log.d("addClickIntent", "Remove head");
				mViewContext.removeAnimatable(this);
				Log.d("addClickIntent", "removeAnimatable: " + this);
			}
		}
	}

	private class Lv3ClickBodyAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			Log.d("animCount","spriteVib_atChild_Draw : "+spriteVib);

			// Draw the brick at bottom
			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.beautygirl2_angry0);
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.animation_test_body);
				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);
			}
		}
	}

	private class Lv3ClickLegAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			Log.d("animCount","spriteVib_atChild_Draw : "+spriteVib);

			// Draw the brick at bottom
			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.beautygirl2_angry0);
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.animation_test_bottom);
				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);
				Log.v("draw_Speeding", SystemClock.uptimeMillis()  + " (After)");
			}
		}
	}

	@Override
	public void OnHeadsetConnected(PatpatView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnHeadsetDisconnected(PatpatView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnClickBody(PatpatView patpatView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnClickLeg(PatpatView patpatView) {
		// TODO Auto-generated method stub

	}
}
