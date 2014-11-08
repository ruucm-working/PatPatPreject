package com.exam.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.util.Log;
import android.widget.RemoteViews;

import com.exam.IAnimatable;
import com.exam.MediaAssets;
import com.exam.R;
import com.exam.Sprite;
import com.exam.SpriteHelper;
import com.exam.coinBlockWidgetProvider;
import com.exam.helper.TextPref;
import com.exam.tab.DeviceConditionPage;
import com.exam.tab.Service_TaskTimer;
import com.exam.view.Lv1State.Lv1WaitState;

public class Lv0_1State implements ICoinBlockViewState {
	
	
	
	public static boolean overlapAnimSwitch  = true;
	
	// sprites
	Sprite samsungSprite = MediaAssets.getInstance().getSprite(R.drawable.samsung_test);
	Sprite eggsp = MediaAssets.getInstance().getSprite(R.drawable.egg);
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
	Sprite blankSprite 	= MediaAssets.getInstance().getSprite(R.drawable.blankimage);
	
	// sound
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	MediaPlayer snd1 = MediaAssets.getInstance().getSoundPlayer(R.raw.notify_sound);

	// sprite vibration controller 
	private int animStage = 0;
	private int[] heightModifier = { 12, 8, 4, 2};
	private int[] widthModifier = { 3, -3, 2, -2, 1, -1, 0, -0 };
	
	boolean animeSwitch = false;
	
	boolean fuck = false;   
	static CoinBlockView mViewContext;

	public Lv0_1State(CoinBlockView viewContext) {
		mViewContext = viewContext;

		mViewContext.addAnimatable(new Lv0_1Animation(viewContext.getDensity()));
		
		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start(); 
			}
		});
	}
	

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		/*Log.d("bugfix", "lv0_1 draw");
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0,
				-(int)(heightModifier[animStage] * viewContext.getDensity()));
		
		viewContext.setState(new Lv0WaitState());
		
		*/
		
		/*// Draw the brick at bottom
        SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0,
                                        - (int)(heightModifier[animStage] * viewContext.getDensity()));
        animStage++;
        if (animStage >= heightModifier.length) {
                viewContext.setState(new Lv0WaitState());
        }
		*/
	}
	
	public boolean NeedRedraw() {
		return false;
	}
	
	public class Lv0_1Animation implements IAnimatable {
		
		
		//진동할때 올라오고, 상단에 남는 드로블
		private int flowerRaise = 4;
		
        private int upstep/*, movestep*/;
        private float density;
        private boolean touchtop = false;
        
        public Lv0_1Animation(float Density)
        {
                upstep = 10;
//                movestep = 10;
                density = Density;
        }

        public boolean AnimationFinished()
        {
        	 Log.d("lv0_1anim","AnimationFinished : "+(upstep>10));
                return upstep>10;
        }
        
		public void Draw(Bitmap canvas) {

			SpriteHelper.DrawSprite(canvas, eggsp, 0,
					SpriteHelper.DrawPosition.BottomCenter, 0,
					-(int) (flowerRaise * 4 * mViewContext.getDensity()));

			Sprite bottom2 = MediaAssets.getInstance().getSprite(
					R.drawable.brick_disabled);
			SpriteHelper.DrawSprite(canvas, bottom2, 0,
					SpriteHelper.DrawPosition.BottomCenter);

			if (flowerRaise < 8)
				flowerRaise++;
			else {
				animeRemove(this);

				mViewContext.setState(new Lv0WaitState());
			}
			
			
			Log.d("lv0_1anim","flowerRaise : "+flowerRaise);
			
			
			/*
			int top = -(int) ((16 - upstep) * 2 * density);
			// int right = (int)((10-movestep) * 2 * density);

			if (!touchtop) {
				

				// Two stage
				if (upstep > 0) {
					// Draw sprite

					upstep--;
				} else touchtop = true;
				Log.d("lv0_1anim", " !touchtop upstep : " + upstep);
				
			} else {
				SpriteHelper.DrawSprite(canvas, eggsp, 0,
						SpriteHelper.DrawPosition.BottomCenter, 0, top);
				if (upstep <= 10) {
					upstep++;
				} else mViewContext.setState(new Lv0WaitState());
				Log.d("lv0_1anim", " touchtop upstep : " + upstep);
				
			}*/
			

		}
}
	private class Lv0_1DblClickAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 16, -16, 8, -8, 4, -4, 0, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);
		}
	}
	
	private void animeRemove(IAnimatable animeObject)
	{
		if(animeSwitch){
			animeSwitch = false;
			mViewContext.removeAnimatable(animeObject);
			mViewContext.setState(new Lv0WaitState());
		}else{
			mViewContext.removeAnimatable(animeObject);
		}
	}
	
	private class Lv0_1WifiOnAnim implements IAnimatable {
		private int blockVib = 0;	

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
			
		}
	}

	private class Lv0_1WifiOffAnim implements IAnimatable {
		private int blockVib = 0;	
		//private int[] widthModifier = { 12, -12, 8, -8, 4, -4, 0, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1USBConnectedAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("USB", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1USBDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("USB", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1HeadsetConnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1HeadsetDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1PlaneOnAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom

			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1PlaneOffAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom

			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1SMSAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
			//吏꾨룞�븷�븣�쓽 �븯�떒�뱶濡쒕툝

			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1PowerConnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1PowerDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}




	public void OnClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub 

	}

	@Override
	public void OnOften(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnEvolve(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnInit(CoinBlockView coinBlockView) {
		//coinBlockView.removeAnimatable(lv0Anim);
	}

	class Lv0WaitState implements ICoinBlockViewState {

		final MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		
		long second = 0 ;
		
		//프레퍼런스 
		TextPref mPref;	

		boolean lv0_1;
		boolean lv0_2;
		public String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
		public String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";
		
		int CliCount0_1 ;

		public void OnClick(CoinBlockView viewContext) {
			
			Log.d("lv0_1anim","OnClick, animeSwitch : "+animeSwitch);
			
			if(!animeSwitch){
				viewContext.setState(new Lv0WaitState());
				animeSwitch = true;
				mViewContext.addAnimatable(new Lv0ClickAnim());
				
				snd.seekTo(0);
				snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
					public void onSeekComplete(MediaPlayer mp) {
						snd.start();
					}
				});
				
				try {
					mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
				} catch (Exception e) { 
					e.printStackTrace();
				}
				
				mPref.Ready();
				
				CliCount0_1 = mPref.ReadInt("clicount0_1", 0);			 
				CliCount0_1++;
				
				mPref.WriteInt("clicount0_1", CliCount0_1);
				mPref.CommitWrite();
			}
		}
		
		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			
			Log.d("lv0_1anim","Draw, animeSwitch : "+animeSwitch);
			
			if(animeSwitch) SpriteHelper.DrawSprite(canvas, blankSprite, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
			else 			SpriteHelper.DrawSprite(canvas, eggsp, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
		}

		public boolean NeedRedraw() {
			return false;
		}
		
		private  void updateEvolveIntent(RemoteViews rviews, Context context) {
			// TODO Auto-generated method stub							
			int mWidgetId = CoinBlockView.mWidgetId;
			
			Intent intent = new Intent(String.format(INTENT_INIT_FORMAT, mWidgetId));
			intent.putExtra("widgetId11", mWidgetId);		

			context.sendBroadcast(intent);

			Intent intent2 = new Intent(String.format(INTENT_EVOLVE_FORMAT, mWidgetId));
			intent2.putExtra("widgetId10", mWidgetId);				

			context.sendBroadcast(intent2);

			Log.d(coinBlockWidgetProvider.TAG," updateEvolveIntent(Remo(rviews);");
		}

		@Override
		public void OnOften(CoinBlockView coinBlockView) {		
			
			Log.d("prevent_Overlapping","OnOften - overlapAnimSwitch : "+overlapAnimSwitch);
			
			Log.d("lv0_1anim","OnOften");
			
			if(overlapAnimSwitch){
				overlapAnimSwitch = false;
				coinBlockView.addAnimatable(new Lv0_1OftenAnim());
			}
		}

		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
			Log.d("prevent_Overlapping","OnEvolve - overlapAnimSwitch : "+overlapAnimSwitch);
			
			if(overlapAnimSwitch){
				overlapAnimSwitch = false;
				
				animeSwitch = false;
				//coinBlockView.setState(new Lv0_2State(coinBlockView));
				coinBlockView.setState(new Lv0_2State(coinBlockView));
				Service_TaskTimer.taskTimer2.isCanceled = true;
				DeviceConditionPage.UpdateIntroView();
				
			
			}
			Log.d("Lv0_1State", "UpdateIntroView");
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			Log.d("tag3","OnInit");
		}

		@Override
		public void OnDblClick(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
//			viewContext.removeAnimatable(lv0_1dblClick);
//
//			lv0_1dblClick = new Lv0_1DblClickAnim();			
//			viewContext.addAnimatable(lv0_1dblClick);
//
//			snd1.seekTo(0);
//			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
//				public void onSeekComplete(MediaPlayer mp) {
//					snd1.start();
//				}
//			});
//
//			Log.v("DOUBLECLICK", "Entering Doubleclick");
//
//			Setting.DblClickCount++;
//			Setting.mPref.Ready();
//			Setting.mPref.WriteInt("dblclick", Setting.DblClickCount);			
//			Setting.mPref.CommitWrite();
		}

		@Override
		public void OnSMSReceived(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
//			lv0_1planeOn = new Lv0_1PlaneOnAnim();
//			viewContext.addAnimatable(lv0_1planeOn);
//
//			snd1.seekTo(0);
//			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
//				public void onSeekComplete(MediaPlayer mp) {
//					snd1.start();
//				}
//			});
		}

		@Override
		public void OnWifiConnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			Log.v("WIFI", "OnWifiConnected");
		}

		@Override
		public void OnWifiDisconnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			Log.v("WIFI", "OnWifiDisconnected");
		}
		
		@Override
		public void OnPowerConnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			Log.v("POWER", "OnPower");

			viewContext.addAnimatable(new Lv0_1PowerConnectedAnim());			

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnPowerDisconnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			Log.v("POWER", "OffPower");
		}

		@Override
		public void OnUSBConnected(CoinBlockView viewContext) {
			Log.v("USB", "USBOn lv0-1");
		}

		@Override
		public void OnUSBDisconnected(CoinBlockView viewContext) {
			Log.v("USB", "USBOff lv0-1");
		}

		@Override
		public void OnHeadsetConnected(CoinBlockView viewContext) {
			Log.v("HEADSET", "Headset lv0-1");
		}

		@Override
		public void OnHeadsetDisconnected(CoinBlockView viewContext) {
			Log.v("HEADSET", "HeadsetOff lv0-1");
		}

		@Override
		public void OnPlaneModeOn(CoinBlockView viewContext) {
			Log.v("PlaneModeOn", "HeadsetOff lv0-1");
		}

		@Override
		public void OnPlaneModeOff(CoinBlockView viewContext) {
			Log.v("PlaneModeOff", "HeadsetOff lv0-1");
		}
	}

	private class Lv0_1OftenAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {

			

			if (blockVib < 7){
				// Draw the brick at bottom
				
				Log.v("prevent_Overlapping","blockVib :"+blockVib);
				Log.v("prevent_Overlapping","overlapAnimSwitch :"+overlapAnimSwitch);
				
				SpriteHelper.DrawSprite(canvas, eggsp, 0, SpriteHelper.DrawPosition.BottomCenter,
						-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);
				blockVib++; 
				
				
				
			}
			else{

				mViewContext.removeAnimatable(this);

				overlapAnimSwitch = true;

			}
			Log.v("stop_unknownOverlapping", "Lv0_1OftenAnim"+Integer.toString(blockVib));
		}
	}

	private class Lv0ClickAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, eggsp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[spriteVib] * mViewContext.getDensity()), 0);

			if (spriteVib < 7) spriteVib++;
			else 			   animeRemove(this);
		}
	}
	
	@Override
	public void OnDblClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnPowerConnected(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnHeadsetConnected(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnSMSReceived(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnWifiConnected(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnWifiDisconnected(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnPowerDisconnected(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnUSBConnected(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnUSBDisconnected(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnHeadsetDisconnected(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnPlaneModeOn(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnPlaneModeOff(CoinBlockView viewContext) {
		// TODO Auto-generated method stub

	}
}