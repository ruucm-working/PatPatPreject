package com.exam.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;
import android.widget.*;

import com.exam.*;
import com.exam.tab.DeviceConditionPage;
import com.exam.tab.IntroActivity;
import com.exam.tab.Setting;
import com.exam.tab.TaskTimer;
import com.exam.view.Lv0_1State.Lv0WaitState;

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
  
	// Animations instances
	Lv0OftenAnim lv0_2ofAnim; 
	Lv0_1DblClickAnim lv0_2dblClick;

	Lv0_1WifiOnAnim lv0_2wifiOn;
	Lv0_1WifiOffAnim lv0_2wifiOff;

	Lv0_1PowerConnectedAnim lv0_2powerOn;
	Lv0_1PowerDisconnectedAnim lv0_2powerOff;

	Lv0_1USBConnectedAnim lv0_1usbOn;
	Lv0_1USBDisconnectedAnim lv0_2usbOff;

	Lv0_1HeadsetConnectedAnim lv0_2headsetOn;
	Lv0_1HeadsetDisconnectedAnim lv0_2headsetOff;

	Lv0_1PlaneOnAnim lv0_2planeOn;
	Lv0_1PlaneOffAnim lv0_2planeOff;
	
	Lv0_1SMSAnim lv0_2sms;

	public Lv0_2State(CoinBlockView viewContext) {
		mViewContext = viewContext;

		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start(); 
			}
		});
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7) { 
				blockVib++;
			}
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

			if (blockVib < 7)
				blockVib++;
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

			if (blockVib < 7)
				blockVib++;
		}
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		animStage++;  

		Log.v("tag3", "animstage");

		if (animStage >= heightModifier.length)
			viewContext.setState(new Lv0_2WaitState());
	}

	public boolean NeedRedraw() {
		return false;
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
		boolean lv0_2 ;
		boolean lv1;
		
		public String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
		public String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";

		
		final MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;
		
		public void OnClick(CoinBlockView viewContext) {
			
			viewContext.setState(new Lv0_2WaitState());
			
			viewContext.removeAnimatable(lv0_2dblClick);
			viewContext.removeAnimatable(lv0_2wifiOff);
			viewContext.removeAnimatable(lv0_2powerOff);
			viewContext.removeAnimatable(lv0_2usbOff);
			viewContext.removeAnimatable(lv0_2headsetOn);
			viewContext.removeAnimatable(lv0_2headsetOff);
			viewContext.removeAnimatable(lv0_2planeOn);
			viewContext.removeAnimatable(lv0_2planeOff);
			viewContext.removeAnimatable(lv0_2sms);
			
			viewContext.removeAnimatable(lv0_2ofAnim);
			viewContext.removeAnimatable(lv0_2powerOn);
			viewContext.removeAnimatable(lv0_2wifiOn);
			
			
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
		

		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			if(animeSwitch) SpriteHelper.DrawSprite(canvas, blankSprite, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
			else 			SpriteHelper.DrawSprite(canvas, sp, sp.NextFrame(), SpriteHelper.DrawPosition.BottomCenter);
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
			
			IntroActivity.taskTimer1.isCanceled = false;
			taskTimer1.setTextView1(R.id.time0);
			
			taskTimer1.execute("");

			DeviceConditionPage.UpdateIntroView();
			
			Log.d("tag3","OnEvolve");
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			coinBlockView.removeAnimatable(lv0_2ofAnim);
			coinBlockView.removeAnimatable(lv0_2powerOn);
			coinBlockView.removeAnimatable(lv0_2wifiOn);

			Log.d("tag3","OnInit");
		}

		@Override
		public void OnDblClick(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.removeAnimatable(lv0_2dblClick);

			lv0_2dblClick = new Lv0_1DblClickAnim();			
			viewContext.addAnimatable(lv0_2dblClick);

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
			lv0_2planeOn = new Lv0_1PlaneOnAnim();
			viewContext.addAnimatable(lv0_2planeOn);

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

			viewContext.removeAnimatable(lv0_2wifiOn);

			lv0_2wifiOn = new Lv0_1WifiOnAnim();	
			viewContext.addAnimatable(lv0_2wifiOn);

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
			
			viewContext.removeAnimatable(lv0_2wifiOff);

			lv0_2wifiOff = new Lv0_1WifiOffAnim();	
			viewContext.addAnimatable(lv0_2wifiOff);

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

			viewContext.removeAnimatable(lv0_2powerOn);

			lv0_2powerOn = new Lv0_1PowerConnectedAnim();	
			viewContext.addAnimatable(lv0_2powerOn);			

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

			viewContext.removeAnimatable(lv0_2powerOff);

			lv0_2powerOff = new Lv0_1PowerDisconnectedAnim();	
			viewContext.addAnimatable(lv0_2powerOff);			

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
			lv0_1usbOn = new Lv0_1USBConnectedAnim();
			viewContext.addAnimatable(lv0_1usbOn);

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
			lv0_2usbOff = new Lv0_1USBDisconnectedAnim();
			viewContext.addAnimatable(lv0_2usbOff);

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
			lv0_2headsetOn = new Lv0_1HeadsetConnectedAnim();
			viewContext.addAnimatable(lv0_2headsetOn);

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
			lv0_2headsetOff = new Lv0_1HeadsetDisconnectedAnim();
			viewContext.addAnimatable(lv0_2headsetOff);

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
			lv0_2planeOn = new Lv0_1PlaneOnAnim();
			viewContext.addAnimatable(lv0_2planeOn);

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
			lv0_2planeOff = new Lv0_1PlaneOffAnim();
			viewContext.addAnimatable(lv0_2planeOff);

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

			if (spriteVib < 7){
				spriteVib++;
			}else{
				animeRemove(this);
			}
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