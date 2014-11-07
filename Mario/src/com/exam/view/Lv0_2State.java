package com.exam.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.util.Log;
import android.widget.RemoteViews;

import com.exam.CoinBlockWidgetApp;
import com.exam.IAnimatable;
import com.exam.MediaAssets;
import com.exam.R;
import com.exam.Sprite;
import com.exam.SpriteHelper;
import com.exam.coinBlockWidgetProvider;
import com.exam.helper.TaskTimer;
import com.exam.helper.TextPref;
import com.exam.tab.DeviceConditionPage;
import com.exam.tab.Service_TaskTimer;
import com.exam.tab.Setting;

public class Lv0_2State implements ICoinBlockViewState {
	
	// sprites
	Sprite samsungSprite = MediaAssets.getInstance().getSprite(R.drawable.samsung_test);
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.eggsbreak_sprites_4);
	Sprite blankSprite 	= MediaAssets.getInstance().getSprite(R.drawable.blankimage);
	
	// sound
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	MediaPlayer snd1 = MediaAssets.getInstance().getSoundPlayer(R.raw.notify_sound);

	// sprite vibration controller 
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 3, -3, 2, -2, 1, -1, 0, -0 };
	
	boolean animeSwitch = false;
	
	boolean fuck = false;   
	CoinBlockView mViewContext;

	public Lv0_2State(CoinBlockView viewContext) {
		mViewContext = viewContext;

		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start(); 
			}
		});
	}
	
	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		animStage++;  

		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0,
				-(int)(heightModifier[animStage] * viewContext.getDensity()));
		Log.v("tag3", "animstage");

//		if (animStage >= heightModifier.length)
			viewContext.setState(new Lv0_2WaitState());
	}

	public boolean NeedRedraw() {
		return true;
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

			if (blockVib < 7) blockVib++;
			else 			  animeRemove(this);
		}
	}

	private class Lv0_1WifiOnAnim implements IAnimatable {
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

	class Lv0_2WaitState implements ICoinBlockViewState {
		
		//for count clicknum	
		TextPref mPref;			
		
		int CliCount0_2 ;
		boolean lv0_2;
		boolean lv1;
		
		public String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
		public String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";

		final MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;
		
		
		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			Log.d("bugfix", "드로우 작동중");
			if(animeSwitch) SpriteHelper.DrawSprite(canvas, blankSprite, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
			else 			SpriteHelper.DrawSprite(canvas, sp, sp.NextFrame(), SpriteHelper.DrawPosition.BottomCenter);
		}
		
		public void OnClick(CoinBlockView viewContext) {
			if(!animeSwitch){
				/*
				viewContext.setState(new Lv0_2WaitState());
				
				animeSwitch = true;
				
				mViewContext.addAnimatable(new Lv0ClickAnim());
				*/
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
				
				CliCount0_2 = mPref.ReadInt("clicount0_2", 0);			 
				CliCount0_2++;			
				
				lv0_2 = mPref.ReadBoolean("lv0_2state", false);	
				lv1 = mPref.ReadBoolean("lv1state", false);
				
				if ( CliCount0_2 >= 3  && lv0_2){
					lv0_2 = false;
					lv1 = true;
					mPref.WriteBoolean("lv0_2state", lv0_2);	
					mPref.WriteBoolean("lv1state", lv1);
					mPref.CommitWrite();
					
					RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);
					updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
					
				}		
				else{
	
				mPref.WriteInt("clicount0_2", CliCount0_2);
				mPref.CommitWrite();
				}
			}
		}
		
		private  void updateEvolveIntent(RemoteViews rviews, Context context) {
			// TODO Auto-generated method stub				
			 
			
//			Log.d("CoinBlockView","state " + init+" "+lv0_1+" "+lv0_2+" "+lv1+" "+lv2 );
			
			int mWidgetId = CoinBlockView.mWidgetId;
//			
			Intent intent = new Intent(String.format(INTENT_INIT_FORMAT, mWidgetId));
			intent.putExtra("widgetId11", mWidgetId);		

			context.sendBroadcast(intent);

			Intent intent2 = new Intent(String.format(INTENT_EVOLVE_FORMAT, mWidgetId));
			intent2.putExtra("widgetId10", mWidgetId);				

			context.sendBroadcast(intent2);

			Log.d(coinBlockWidgetProvider.TAG," updateEvolveIntent(Remo(rviews);");
		}
		

	

		public boolean NeedRedraw() {
			return false;
		}

		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			//coinBlockView.removeAnimatable(lv0ofAnim);
			//lv0ofAnim = new Lv0OftenAnim();			
			//coinBlockView.addAnimatable(lv0ofAnim);
		}

		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			Log.d("EvolveBugfix", " lv0_2진화");
			animeSwitch = false;
			coinBlockView.setState(new Lv1State(coinBlockView));

			TaskTimer taskTimer1 = new TaskTimer();
			taskTimer1.onRestartset();
			
			Service_TaskTimer.taskTimer2.isCanceled = false;
//			taskTimer1.setTextView1(R.id.time0);
			
			taskTimer1.execute("");

			DeviceConditionPage.UpdateIntroView();
			
			Log.d("tag3","OnEvolve");
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			Log.d("tag3","OnInit");
		}

		@Override
		public void OnDblClick(CoinBlockView viewContext) {
			// TODO Auto-generated method stub			
			viewContext.addAnimatable(new Lv0_1DblClickAnim());

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});

			Log.v("DOUBLECLICK", "Entering Doubleclick");

			Setting.DblClickCount++;
			Setting.mPref.Ready();
			Setting.mPref.WriteInt("dblclick", Setting.DblClickCount);			
			Setting.mPref.CommitWrite();
		}

		@Override
		public void OnSMSReceived(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.addAnimatable(new Lv0_1PlaneOnAnim());

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnWifiConnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			Log.v("WIFI", "OnWifiConnected");	
			viewContext.addAnimatable(new Lv0_1WifiOnAnim());

			Log.v("WIFI", "addAnimatable");			

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnWifiDisconnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			Log.v("WIFI", "OnWifiDisconnected");	
			viewContext.addAnimatable(new Lv0_1WifiOffAnim());

			Log.v("WIFI", "addAnimatable");			

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
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
			viewContext.addAnimatable(new Lv0_1PowerDisconnectedAnim());			

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnUSBConnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.addAnimatable(new Lv0_1USBConnectedAnim());

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});

			Log.v("USB", "USBOn lv0-1");
		}

		@Override
		public void OnUSBDisconnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.addAnimatable(new Lv0_1USBDisconnectedAnim());

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});

			Log.v("USB", "USBOff lv0-1");
		}

		@Override
		public void OnHeadsetConnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.addAnimatable(new Lv0_1HeadsetConnectedAnim());

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});

			Log.v("HEADSET", "Headset lv0-1");
		}

		@Override
		public void OnHeadsetDisconnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.addAnimatable(new Lv0_1HeadsetDisconnectedAnim());

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});

			Log.v("HEADSET", "HeadsetOff lv0-1");
		}

		@Override
		public void OnPlaneModeOn(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.addAnimatable(new Lv0_1PlaneOnAnim());

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnPlaneModeOff(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.addAnimatable(new Lv0_1PlaneOffAnim());

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}
	}

	private class Lv0OftenAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {

			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7)
				blockVib++; 

			Log.v("tag4", "blockVib"+Integer.toString(blockVib));
		}
	}

	private class Lv0ClickAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[spriteVib] * mViewContext.getDensity()), 0);

			if (spriteVib < 7) spriteVib++;
			else			   animeRemove(this);
		}
	}
	
	private void animeRemove(IAnimatable animeObject)
	{
		if(animeSwitch){
			animeSwitch = false;
			mViewContext.removeAnimatable(animeObject);
			mViewContext.setState(new Lv0_2WaitState());
		}else{
			mViewContext.removeAnimatable(animeObject);
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