package com.jym.patpat;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.jym.helper.TextPref;
import com.jym.helper.XmlMapping;
import com.jym.service.TaskTimer;

public class PatpatState implements IPatpatViewState {

	public static int clickcount = 0;
	public static int level;
	public static boolean overlapAnimSwitch = true;

	private TextPref characterPref;
	private TextPref clickPref;
	private PatpatView mViewContext;
	private HashMap<String, Object> charterInfo;
	private ArrayList<String> howl = new ArrayList<String>();

	private String headAnimePath;
	private String bodyAnimePath;
	private String legAnimePath;
	private String evolveAnimePath;
	private String oftenAnimePath;

	private long clickedTime = 0;
	private static boolean isEvolving = false;

	public PatpatState(PatpatView viewContext) {
		mViewContext = viewContext;

		try {
			clickPref = new TextPref("mnt/sdcard/SsdamSsdam/clickpref.pref");
			characterPref = new TextPref("mnt/sdcard/SsdamSsdam/entitypref.pref");
		} catch (Exception e) {
			e.printStackTrace();
		}

		clickPref.Ready();
		clickcount = clickPref.ReadInt("ClickCount", 0);
		clickPref.EndReady();

		characterPref.Ready();
		level = characterPref.ReadInt("level", 0);
		characterPref.EndReady();

		try{
			charterInfo = XmlMapping.levelMapping("evolve_data", viewContext.Context, level);
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		if(charterInfo != null) {
			howl 			= (ArrayList<String>) charterInfo.get("howl");
			headAnimePath 	= (String) charterInfo.get("head");
			bodyAnimePath 	= (String) charterInfo.get("body");
			legAnimePath 	= (String) charterInfo.get("leg");
			evolveAnimePath = (String) charterInfo.get("evolve");
			oftenAnimePath	= (String) charterInfo.get("wait");
		}
	}

	public void Draw(PatpatView viewContext) {
		Log.e("stop_double_Draw", "Draw_setState_State_");
		viewContext.setState(new WaitState());
	}

	public boolean NeedRedraw() {
		return false;
	}

	public void OnClick(PatpatView viewContext) {

	}

	private class WaitState implements IPatpatViewState {
		private void ClickResponse() {
			String text = null;
			int textcode = (int)(Math.random()*howl.size());
			text = howl.get(textcode);

			try {
				TaskTimer.clickCount++;
				TaskTimer.ePref.Ready();
				TaskTimer.ePref.WriteInt("click_count", TaskTimer.clickCount);
				TaskTimer.ePref.CommitWrite();
			} catch(Exception e) {
				Log.d("WriteError","Couldn't write on pref");
			}

			if((int)(Math.random()*10) < 3)
				Toast.makeText(PatpatView.Context, text, Toast.LENGTH_SHORT).show();
		}

		public void Draw(PatpatView viewContext) {
			Log.d("updateClickIntent_right", "Draw");
		}

		public void OnClick(PatpatView viewContext) {
			if(!isEvolving) {
				Log.v("fix_futuretask","OnClickHead");
				ClickResponse();
				viewContext.addAnimatable(new ClickHeadAnim());
			}
		}

		public void OnClickBody(PatpatView patpatView) {
			if(!isEvolving) {
				Log.v("fix_futuretask","OnClickBody");
				ClickResponse();
				patpatView.addAnimatable(new ClickBodyAnim());
			}
		}

		public void OnClickLeg(PatpatView patpatView) {
			// TODO Auto-generated method stub
			if(!isEvolving) {
				Log.v("fix_futuretask","OnClickLeg");
				ClickResponse();
				patpatView.addAnimatable(new ClickLegAnim());
			}
		}

		public void OnEvolve(PatpatView viewContext) {
			isEvolving = true;
			Toast.makeText(PatpatView.Context, "오잉? 나무늘보의 상태가...?", Toast.LENGTH_SHORT).show();
			viewContext.setState(new PatpatState(viewContext));

			viewContext.addAnimatable(new EvolveAnim());
			Handler handler = new Handler(); 
			handler.postDelayed(new Runnable() { 
				public void run() {
					isEvolving = false;
				} 
			}, 3000);
		}

		public boolean NeedRedraw() {
			return false;
		}

		@Override
		public void OnOften(PatpatView viewContext) {
			clickedTime = SystemClock.uptimeMillis();
			viewContext.addAnimatable(new OftenAnim());
		}

		@Override
		public void OnHeadsetConnected(PatpatView viewContext) {}

		@Override
		public void OnHeadsetDisconnected(PatpatView viewContext) {}
	}

	private class Animation implements IAnimatable {
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

	private class OftenAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			int imageResource = mViewContext.Context.getResources().getIdentifier(oftenAnimePath,
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

	private class ClickHeadAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			// Draw the brick at bottom
			int imageResource = mViewContext.Context.getResources().getIdentifier(headAnimePath,
					"drawable",mViewContext.Context.getPackageName());

			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01,R.drawable.lv1_head0);
				PatpatView.rviews.setImageViewResource(R.id.patview01,imageResource);

				Log.i("refreshing_RemoteView","imageResource_atHead : "+imageResource);
				spriteVib++;
			}			
			else {
				Log.d("addClickIntent", "Remove head");
				mViewContext.removeAnimatable(this);
				Log.d("addClickIntent", "removeAnimatable: " + this);
			}
		}
	}

	private class ClickBodyAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			int imageResource = mViewContext.Context.getResources().getIdentifier(bodyAnimePath,
					"drawable",mViewContext.Context.getPackageName());

			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01,R.drawable.lv1_body0);
				PatpatView.rviews.setImageViewResource(R.id.patview01,imageResource);
				
				Log.i("refreshing_RemoteView","imageResource_atBody : "+imageResource);

				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);
			}
		}
	}

	private class ClickLegAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			int imageResource = mViewContext.Context.getResources().getIdentifier(legAnimePath,
					"drawable",mViewContext.Context.getPackageName());

			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01,R.drawable.lv1_body0);
				PatpatView.rviews.setImageViewResource(R.id.patview01,imageResource);

				Log.i("refreshing_RemoteView","imageResource_atleg : "+imageResource);
				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);
				Log.v("draw_Speeding", SystemClock.uptimeMillis()  + " (After)");
			}
		}
	}

	private class EvolveAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			int imageResource = mViewContext.Context.getResources().getIdentifier(evolveAnimePath,
					"drawable",mViewContext.Context.getPackageName());

			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01,imageResource);

				Log.i("refreshing_RemoteView","setImageViewResource");
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
