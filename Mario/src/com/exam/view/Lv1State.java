package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;
import android.widget.*;

import com.exam.*;

public class Lv1State implements ICoinBlockViewState {

	Sprite flowerSprite = MediaAssets.getInstance().getSprite(R.drawable.samsung);
	Sprite bottom = MediaAssets.getInstance().getSprite(R.drawable.egg_break);

	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	MediaPlayer snd1 = MediaAssets.getInstance().getSoundPlayer(R.raw.notify_sound);

	private int animStage = 0; 
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 6, -6, 4, -4, 2, -2, 0, 0 };	// here
	
	Lv1Animation lv1Anim;
	Lv1ClickAnim lv1clAnim;
	Lv1OftenAnim lv1ofAnim;
	Lv1DblClickAnim lv1dblClick;
	
	Lv1WifiOnAnim lv1wifiOn;
	Lv1WifiOffAnim lv1wifiOff;

	Lv1PowerConnectedAnim lv1powerOn;
	Lv1PowerDisconnectedAnim lv1powerOff;

	Lv1USBConnectedAnim lv1usbOn;
	Lv1USBDisconnectedAnim lv1usbOff;

	Lv1HeadsetConnectedAnim lv1headsetOn;
	Lv1HeadsetDisconnectedAnim lv1headsetOff;

	Lv1PlaneOnAnim lv1planeOn;
	Lv1PlaneOffAnim lv1planeOff;

	Lv1SMSAnim lv1sms;
	
	CoinBlockView context;

	public Lv1State(CoinBlockView viewContext) {
		context = viewContext; 

		//setContentView(R.drawable.background1, "레벨1s냐 아직도 ㅋㅋㅋㅋㅋㅋㅋㅄ");

		lv1Anim = new Lv1Animation();
		viewContext.addAnimatable(lv1Anim);
		snd.seekTo(0); 
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start();
			}
		});
	}
	
	private class Lv1Animation implements IAnimatable {
		//진동할때 올라오고, 상단에 남는 드로블
		private int flowerRaise = 4;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, flowerSprite, 0,
					SpriteHelper.DrawPosition.BottomCenter, 0, -(int) (flowerRaise * 4 * context.getDensity()));

			Sprite bottom2 = MediaAssets.getInstance().getSprite(R.drawable.eggs_break);
			SpriteHelper.DrawSprite(canvas, bottom2, 0, SpriteHelper.DrawPosition.BottomCenter);

			// Draw the flower
			if (flowerRaise < 8)
				flowerRaise++; 
		}
	}

	private class Lv1OftenAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);


			if (blockVib < 7)
				blockVib++;
			
			Log.v("tag4", "blockVib"+Integer.toString(blockVib));
		}	
	}

	private class Lv1ClickAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[spriteVib] * context.getDensity()), 0 );


			if (spriteVib < 7)
				spriteVib++;
		}
	}

	public void setContentView(int drawbleid, String txt) {
		coinBlockIntroActivity instance = coinBlockIntroActivity.getInstance();
		
		//set newstate's background img
		LinearLayout a = (LinearLayout)instance.findViewById(R.id.mainlinear);			
		a.setBackgroundResource(drawbleid);

		//set newstate's text
		TextView statetxt = (TextView)instance.findViewById(R.id.welcome);		
		statetxt.setText(txt);
	}
	
	private class Lv1DblClickAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 16, -16, 8, -8, 4, -4, 0, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1WifiOnAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1WifiOffAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1USBConnectedAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("USB", "Drawanim");

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1USBDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("USB", "Drawanim");

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1HeadsetConnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1HeadsetDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1PlaneOnAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1PlaneOffAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1SMSAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
			//吏꾨룞�븷�븣�쓽 �븯�떒�뱶濡쒕툝

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7) { 
				blockVib++;
			}
		}
	}

	private class Lv1PowerConnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv1PowerDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, flowerSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}
	
	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom

		//진동할때의 하단드로블
		SpriteHelper.DrawSprite(canvas, bottom, 0, SpriteHelper.DrawPosition.BottomCenter,0,
				-(int)(heightModifier[animStage] * viewContext.getDensity()));


		animStage++;
		if (animStage >= heightModifier.length)
			viewContext.setState(new Lv1WaitState(viewContext));
	}

	public boolean NeedRedraw() {
		return true;
	} 
	public void OnClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub 
	}

	private class Lv1WaitState implements ICoinBlockViewState {
		//진동후의, 하단 드로블
		CoinBlockView mViewContext;

		public Lv1WaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;

			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == Lv1WaitState.class)
					{
						//mViewContext.addAnimatable(lv1Anim);

						//mViewContext.setState(new OftenState(mViewContext, flowerSprite)); 

						//lv1Anim.Draw2(Bitmap.createBitmap(mViewContext.cwidth, mViewContext.cheight, Bitmap.Config.ARGB_8888));
						//mViewContext.scheduleRedraw();
					}
				}
			}, 5000);
		}

		public void OnClick(CoinBlockView viewContext) {
			viewContext.removeAnimatable(lv1Anim);
			viewContext.removeAnimatable(lv1ofAnim);
			viewContext.removeAnimatable(lv1clAnim);
			viewContext.removeAnimatable(lv1powerOn);
			viewContext.removeAnimatable(lv1wifiOn);

			lv1clAnim = new Lv1ClickAnim();			
			viewContext.addAnimatable(lv1clAnim);

			snd.seekTo(0);
			snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd.start();
				}
			});

			CoinBlockView.CliCount1++;
			CoinBlockView.mPref.Ready();			
			CoinBlockView.mPref.WriteInt("clicount1", CoinBlockView.CliCount1);			
			CoinBlockView.mPref.CommitWrite();
		}

		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			//SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
		}

		public boolean NeedRedraw() { 
			return false;
		}

		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			coinBlockView.setState(new Lv2State(coinBlockView));

			CoinBlockView.lv1 = false;
			CoinBlockView.lv2 = true;
			CoinBlockView.mPref.Ready();
			CoinBlockView.mPref.WriteBoolean("lv1state", CoinBlockView.lv1);
			CoinBlockView.mPref.WriteBoolean("lv2state", CoinBlockView.lv2);
			CoinBlockView.mPref.CommitWrite();

			coinBlockIntroActivity.UpdateIntroView();
		}

		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void OnDblClick(CoinBlockView viewContext) {
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
		public void OnPowerConnected(CoinBlockView viewContext) {
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
		public void OnHeadsetConnected(CoinBlockView viewContext) {
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

		@Override
		public void OnSMSReceived(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public void OnEvolve(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnOften(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnInit(CoinBlockView coinBlockView) {
		// TODO Auto-generated method stub

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