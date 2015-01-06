package com.jym.patpat;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

		public void Draw(PatpatView viewContext) {

			Log.d("updateClickIntent_right", "Draw");
		}

		@Override
		public void OnClickHead(PatpatView patpatView) {
			patpatView.addAnimatable(new Lv3ClickAnim());
			Toast.makeText(PatpatView.Context, "뀨?",Toast.LENGTH_SHORT).show();
		}

		@Override
		public void OnClickNeck(PatpatView patpatView) {
			// TODO Auto-generated method stub
			patpatView.addAnimatable(new Lv3ClickAnim());
			Toast.makeText(PatpatView.Context, "켁",Toast.LENGTH_SHORT).show();
		}

		@Override
		public void OnClickBody(PatpatView patpatView) {
			// TODO Auto-generated method stub
			patpatView.addAnimatable(new Lv3ClickAnim());
			Toast.makeText(PatpatView.Context, "어딜 만져! 어딜 만지냐고!",Toast.LENGTH_SHORT).show();
		}

		@Override
		public void OnClickShoes(PatpatView patpatView) {
			// TODO Auto-generated method stub
			patpatView.addAnimatable(new Lv3ClickAnim());
			Toast.makeText(PatpatView.Context, "나 버스타고 오다가 똥밟았다",Toast.LENGTH_SHORT).show();
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
			/*
			 * SpriteHelper.DrawSprite(canvas, flowerSprite,
			 * flowerSprite.NextFrame(), SpriteHelper.DrawPosition.BottomCenter,
			 * 0, -(int) (flowerRaise * 4 * context.getDensity()));
			 * 
			 * // Draw the flower if (flowerRaise < 8) flowerRaise++;
			 * 
			 * // Draw the brick at bottom SpriteHelper.DrawSprite(canvas,
			 * flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter, 0, -
			 * (int)(heightModifier2[animStage] * context.getDensity()));
			 * 
			 * if (animstage < 3) animstage++;
			 * 
			 * if (animStage >= heightModifier.length) context.setState(new
			 * DisabledState(context));
			 */
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

			/*
			if (spriteVib == 0) {


				// PatpatView.rviews = new RemoteViews(PatpatView.Context.getPackageName(), R.layout.patpat_widget);

				PatpatView.rviews.setImageViewResource(R.id.patview01,
						R.drawable.fish_animation);

				Log.i("refreshing_RemoteView","setImageViewResource");



				// PatpatView.rviews.setImageViewResource(R.id.patview02,
				// R.drawable.fish_animation_right);


				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);

				// recycle for 200 clicks
				if (clickcount_3_1 % 100 == 0) {


					//					recycleAnimDrawabsle();
				}

				Log.d("addClickIntent", "removeAnimatable: " + this);

			}
			 */
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

			/*
			if (spriteVib == 0) {
				PatpatView.rviews.setImageViewResource(R.id.patview01,
						R.drawable.fish_animation_left);

				// PatpatView.rviews.setImageViewResource(R.id.patview02,
				// R.drawable.fish_right08);

				spriteVib++;
			} else {
				mViewContext.removeAnimatable(this);
				Log.d("addClickIntent", "removeAnimatable: " + this);

			}
			 */
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
	public void OnClickHead(PatpatView patpatView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnClickNeck(PatpatView patpatView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnClickBody(PatpatView patpatView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnClickShoes(PatpatView patpatView) {
		// TODO Auto-generated method stub

	}

}
