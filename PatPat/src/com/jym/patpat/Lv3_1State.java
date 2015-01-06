package com.jym.patpat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jym.helper.TextPref;
import com.jym.helper.XmlMapping;
import com.jym.service.TaskTimer;

public class Lv3_1State implements IPatpatViewState {

	public static int clickcount_3_1 = 0;
	
	int level;
	TextPref characterPref;

	public static boolean overlapAnimSwitch = true;
	PatpatView mViewContext;
	
	HashMap<String, Object> charterInfo;
	
	ArrayList<String> howl = new ArrayList<String>();
	
	String waitAnimePath;
	String clickAnimePath;

	public Lv3_1State(PatpatView viewContext) {
		mViewContext = viewContext;
		
		try {
			characterPref = new TextPref("mnt/sdcard/SsdamSsdam/entitypref.pref");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

		// int spriteVib = 0;

		public PatpatView context;

		public void Draw(PatpatView viewContext) {
			Log.d("updateClickIntent_right", "Draw");
		}

		public void OnClick(PatpatView viewContext) {

			String text = null;
			int textcode = (int)(Math.random()*howl.size());
			text = howl.get(textcode);
			
			Toast.makeText(PatpatView.Context, text, Toast.LENGTH_SHORT).show();
			
			Log.w("seperated_ClickCount", "OnClick");

			viewContext.addAnimatable(new Lv3ClickAnim());
		}

		public void OnClick_right(PatpatView viewContext) {

			// write clickcount_3_1_left (using textpref)
			TaskTimer.CliCount3_1_right++;
			TaskTimer.ePref.Ready();
			TaskTimer.ePref.WriteInt("clicount3_1_right",
					TaskTimer.CliCount3_1_right);

			// TaskTimer.temp_Count2++;
			TaskTimer.ePref.CommitWrite();

			viewContext.addAnimatable(new Lv3ClickAnim_right());
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

			// RemoteViews rviews = new
			// RemoteViews(mViewContext.getPackageName(),
			// R.layout.coin_block_widget);

			// Log.e("addClickIntent","mViewContext.getPackageName() : "+mViewContext.getPackageName());

			int imageResource = mViewContext.Context.getResources().getIdentifier(clickAnimePath,
																				 "drawable",
																				 mViewContext.Context.getPackageName());
			
			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01,
						imageResource);
				
				Log.i("refreshing_RemoteView","setImageViewResource");
				
				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);

				// recycle for 200 clicks
				if (clickcount_3_1 % 100 == 0) {

					
//					recycleAnimDrawabsle();
				}

				Log.d("addClickIntent", "removeAnimatable: " + this);

			}

		}

	}

	
	public static void recycleAnimDrawable () {
		
		
		Log.d("memory_pb", "mViewContext_atDraw( : "
				+ PatpatView.Context.getApplicationContext());


		InputStream is = null;
		Bitmap bmp = null;
		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish01);
		bmp = BitmapFactory.decodeStream(is);
		bmp.recycle();

		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish02);
		bmp = BitmapFactory.decodeStream(is);
		bmp.recycle();

		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish03);
		bmp = BitmapFactory.decodeStream(is);
		bmp.recycle();

		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish04);
		bmp = BitmapFactory.decodeStream(is);
		bmp.recycle();

		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish05);
		bmp = BitmapFactory.decodeStream(is);
		bmp.recycle();

		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish06);
		bmp = BitmapFactory.decodeStream(is);
		bmp.recycle();

		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish07);
		bmp = BitmapFactory.decodeStream(is);
		bmp.recycle();

		is = PatpatView.Context.getApplicationContext()
				.getResources().openRawResource(R.drawable.fish08);
		bmp = BitmapFactory.decodeStream(is);
		bmp.recycle();
		
	}
	
	private class Lv3ClickAnim_right implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw() {
			// Draw the brick at bottom

			Log.d("addClickIntent", "Draw_Lv3ClickAnim");
			Log.d("addClickIntent", "spriteVib : " + spriteVib);

			// RemoteViews rviews = new
			// RemoteViews(mViewContext.getPackageName(),
			// R.layout.coin_block_widget);

			// Log.e("addClickIntent","mViewContext.getPackageName() : "+mViewContext.getPackageName());

			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01,
						R.drawable.fish_animation_left);
				/*
				 * PatpatView.rviews.setImageViewResource(R.id.patview02,
				 * R.drawable.fish_right08);
				 */
				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);
				Log.d("addClickIntent", "removeAnimatable: " + this);

			}

		}
	}

	/*
	 * private void animeRemove(IAnimatable animeObject) { if(animeSwitch){
	 * animeSwitch = false; mViewContext.removeAnimatable(animeObject);
	 * mViewContext.setState(new Lv3_1WaitState()); }else{
	 * mViewContext.removeAnimatable(animeObject); } }
	 */

	@Override
	public void OnHeadsetConnected(PatpatView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnHeadsetDisconnected(PatpatView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnClick_right(PatpatView patpatView) {
		// TODO Auto-generated method stub

	}

}
