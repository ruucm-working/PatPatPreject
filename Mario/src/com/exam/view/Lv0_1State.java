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

public class Lv0_1State implements ICoinBlockViewState {
	
	// sprites
	Sprite samsungSprite = MediaAssets.getInstance().getSprite(R.drawable.samsung_test);
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.egg);
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
	Lv0_1OftenAnim lv0_1ofAnim; 
	Lv0_1DblClickAnim lv0_1dblClick;

	Lv0_1WifiOnAnim lv0_1wifiOn;
	Lv0_1WifiOffAnim lv0_1wifiOff;

	Lv0_1PowerConnectedAnim lv0_1powerOn;
	Lv0_1PowerDisconnectedAnim lv0_1powerOff;

	Lv0_1USBConnectedAnim lv0_1usbOn;
	Lv0_1USBDisconnectedAnim lv0_1usbOff;

	Lv0_1HeadsetConnectedAnim lv0_1headsetOn;
	Lv0_1HeadsetDisconnectedAnim lv0_1headsetOff;

	Lv0_1PlaneOnAnim lv0_1planeOn;
	Lv0_1PlaneOffAnim lv0_1planeOff;
	
	Lv0_1SMSAnim lv0_1sms;

	public Lv0_1State(CoinBlockView viewContext) {
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
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0,
				-(int)(heightModifier[animStage] * viewContext.getDensity()));
		
		viewContext.setState(new Lv0WaitState());
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
			
			viewContext.setState(new Lv0WaitState());
			
			viewContext.removeAnimatable(lv0_1dblClick);
			viewContext.removeAnimatable(lv0_1wifiOff);
			viewContext.removeAnimatable(lv0_1powerOff);
			viewContext.removeAnimatable(lv0_1usbOff);
			viewContext.removeAnimatable(lv0_1headsetOn);
			viewContext.removeAnimatable(lv0_1headsetOff);
			viewContext.removeAnimatable(lv0_1planeOn);
			viewContext.removeAnimatable(lv0_1planeOff);
			viewContext.removeAnimatable(lv0_1sms);
			
			viewContext.removeAnimatable(lv0_1ofAnim);
			viewContext.removeAnimatable(lv0_1powerOn);
			viewContext.removeAnimatable(lv0_1wifiOn);
			
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
		
		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			if(animeSwitch) SpriteHelper.DrawSprite(canvas, blankSprite, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
			else 			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
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
			coinBlockView.removeAnimatable(lv0_1ofAnim);
			lv0_1ofAnim = new Lv0_1OftenAnim();			
			coinBlockView.addAnimatable(lv0_1ofAnim);
		}

		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			coinBlockView.setState(new Lv0_2State(coinBlockView));

			// coinBlockIntroActivity.taskTimer1.setTextView1(R.id.time0);

			IntroActivity.taskTimer1.isCanceled = true;

			// IntroActivity.taskTimer1.onCancelled();

			// CoinBlockView.lv0_1 = false;
			// CoinBlockView.lv0_2 = true;
			//
			// Log.d("Lv0_1State","CoinBlockView");
			//
			// CoinBlockView.mPref.Ready();
			// CoinBlockView.mPref.WriteBoolean("lv0_1state",
			// CoinBlockView.lv0_1);
			// CoinBlockView.mPref.WriteBoolean("lv0_2state",
			// CoinBlockView.lv0_2);
			// CoinBlockView.mPref.CommitWrite();
			//
			// Log.d("Lv0_1State","CommitWrite");

			DeviceConditionPage.UpdateIntroView();

			Log.d("Lv0_1State", "UpdateIntroView");
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			coinBlockView.removeAnimatable(lv0_1ofAnim);
			coinBlockView.removeAnimatable(lv0_1powerOn);
			coinBlockView.removeAnimatable(lv0_1wifiOn);

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

			viewContext.removeAnimatable(lv0_1powerOn);

			lv0_1powerOn = new Lv0_1PowerConnectedAnim();	
			viewContext.addAnimatable(lv0_1powerOn);			

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
				animeSwitch = false;
				mViewContext.removeAnimatable(this);
				mViewContext.setState(new Lv0WaitState());
			}
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