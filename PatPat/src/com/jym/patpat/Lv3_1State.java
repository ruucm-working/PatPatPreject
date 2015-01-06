package com.jym.patpat;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jym.helper.TextPref;
import com.jym.service.TaskTimer;

public class Lv3_1State implements IPatpatViewState {

	public static int clickcount_3_1 = 0;
	TextPref clickPref;

	public static boolean overlapAnimSwitch = true;
	PatpatView mViewContext;
	
	private long clickedTime = 0;

	public Lv3_1State(PatpatView viewContext) {
		mViewContext = viewContext;

		try {
			clickPref = new TextPref("mnt/sdcard/SsdamSsdam/clickpref.pref");
		} catch (Exception e) {
			e.printStackTrace();
		}

		clickPref.Ready();
		clickcount_3_1 = clickPref.ReadInt("ClickCount_3_1", 0);
		clickPref.EndReady();
		
		InputStream is = null;
		Bitmap bmp = null;
		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish_animation);
		bmp = BitmapFactory.decodeStream(is);
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
		
		public void Draw(PatpatView patpatView) {
			Log.d("updateClickIntent_right", "Draw");
		}

		public void OnClick(PatpatView patpatView) {
			clickedTime = SystemClock.uptimeMillis();

			clickcount_3_1++;
			Log.w("seperated_ClickCount", "clickcount_3_1 : " + clickcount_3_1);

			clickPref.Ready();
			clickPref.WriteInt("ClickCount_3_1", clickcount_3_1);
			clickPref.CommitWrite();

			Log.w("seperated_ClickCount", "End_CommitWrite");

			patpatView.addAnimatable(new Lv3ClickAnim());
			
			Log.w("fix_futuretask", "addAnimatabe(start_scheduledraw)");

			int textcode = (int) (Math.random() * 6);
			String text = null;

			switch (textcode) {
			case 0:
				text = "머리";
				break;

			case 1:
				text = "두부";
				break;

			case 2:
				text = "대가리";
				break;

			case 3:
				text = "대갈빡";
				break;

			case 4:
				text = "대갈통";
				break;

			case 5:
				text = "머리통";
				break;
			}
			Toast.makeText(PatpatView.Context, text, Toast.LENGTH_SHORT).show();
		}

		public void OnClickBody(PatpatView patpatView) {
			clickedTime = SystemClock.uptimeMillis();
			
			/*
			// add textpref code here
			*/
			
			patpatView.addAnimatable(new Lv3ClickBodyAnim());
			
			int textcode = (int) (Math.random() * 2);
			String text = null;

			switch (textcode) {
			case 0:
				text = "어딜 만져!";
				break;

			case 1:
				text = "어딜 만지냐구!";
				break;
			}
			Toast.makeText(PatpatView.Context, text, Toast.LENGTH_SHORT).show();
		}
		
		public void OnClickLeg(PatpatView patpatView) {
			clickedTime = SystemClock.uptimeMillis();
			patpatView.addAnimatable(new Lv3ClickLegAnim());
			
			int textcode = (int) (Math.random() * 3);
			String text = null;

			switch (textcode) {
			case 0:
				text = "닭발";
				break;

			case 1:
				text = "족발";
				break;

			case 2:
				text = "뒷다리살";
				break;
			}
			Toast.makeText(PatpatView.Context, text, Toast.LENGTH_SHORT).show();
		}


		public boolean NeedRedraw() {
			return false;
		}

		@Override
		public void OnEvolve(PatpatView coinBlockView) {
			// coinBlockView.setState(new InitState(coinBlockView));
		}

		@Override
		public void OnOften(PatpatView coinBlockView) {

		}

		@Override
		public void OnHeadsetConnected(PatpatView viewContext) {
			// TODO Auto-generated method stub

		}

		@Override
		public void OnHeadsetDisconnected(PatpatView viewContext) {
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

		public void Draw() {
		}
	}

	@Override
	public void OnEvolve(PatpatView coinBlockView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnOften(PatpatView coinBlockView) {
		// TODO Auto-generated method stub

	}

	private class Lv3OftenAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {

		}
	}

	private class Lv3ClickAnim implements IAnimatable {
		private int spriteVib = 0;
		Lv3ClickAnim con;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			// Draw the brick at bottom
			con = this;

			Log.d("animCount","spriteVib_atChild_Draw : "+spriteVib);
			
			if (spriteVib == 0) {
				Log.w("animCount","setResource_atChild_Draw : "+spriteVib);
				
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.beautygirl2_angry0);
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.animation_baby);
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
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.fish_animation);
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
				PatpatView.rviews.setImageViewResource(R.id.patview01, R.drawable.girl_evolve);
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