package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.*;
import android.util.*;
import android.widget.*;

import com.exam.*;
import com.exam.tab.DeviceConditionPage;
import com.exam.view.Lv1State.Lv1WaitState;

public class Lv2State implements ICoinBlockViewState {

	Sprite samsungSprite = MediaAssets.getInstance().getSprite(R.drawable.samsung_test);
	Sprite evolve 		= MediaAssets.getInstance().getSprite(R.drawable.samsungevolve_sprites_4);
	Sprite blankSprite 	= MediaAssets.getInstance().getSprite(R.drawable.blankimage);
	
	MediaPlayer snd  = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	MediaPlayer snd1 = MediaAssets.getInstance().getSoundPlayer(R.raw.dingding);
	MediaPlayer snd2 = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_1_up);
	
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };		// here
	private int[] heightModifier2 = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private int[] widthModifier = {4, -4, 4, -4, 3, -3, 2, -2, 1, -1, 0, -0, 0, 0 };	// here
	
	boolean animeSwitch = false;
	
	Lv2OftenAnim lv2ofAnim;
	Lv2DblClickAnim lv2dblClick;

	Lv2WifiOnAnim lv2wifiOn;
	Lv2WifiOffAnim lv2wifiOff;

	Lv2PowerConnectedAnim lv2powerOn;
	Lv2PowerDisconnectedAnim lv2powerOff;

	Lv2USBConnectedAnim lv2usbOn;
	Lv2USBDisconnectedAnim lv2usbOff;

	Lv2HeadsetConnectedAnim lv2headsetOn;
	Lv2HeadsetDisconnectedAnim lv2headsetOff;

	Lv2PlaneOnAnim lv2planeOn;
	Lv2PlaneOffAnim lv2planeOff;

	Lv2SMSAnim lv2sms;

	CoinBlockView mViewContext;

	public Lv2State(CoinBlockView viewContext) {
		mViewContext = viewContext;
		
		snd2.seekTo(0);
		snd2.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd2.start(); 
			} 
		}); 
	}
	
	private class Lv2DblClickAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 16, -16, 8, -8, 4, -4, 0, 0 };	// here
		
		public boolean AnimationFinished() { return false; }
		
		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 7) blockVib++;
			else			  mViewContext.removeAnimatable(this);			  
		}
	}

	private class Lv2WifiOnAnim implements IAnimatable {
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
			else			  mViewContext.removeAnimatable(this);
		}
	}

	private class Lv2WifiOffAnim implements IAnimatable {
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

	private class Lv2HeadsetConnectedAnim implements IAnimatable {
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

	private class Lv2HeadsetDisconnectedAnim implements IAnimatable {
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
	
	private class Lv2USBConnectedAnim implements IAnimatable {
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
	
	private class Lv2USBDisconnectedAnim implements IAnimatable {
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

	private class Lv2PlaneOnAnim implements IAnimatable {
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
	private class Lv2PlaneOffAnim implements IAnimatable {
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

	private class Lv2SMSAnim implements IAnimatable {
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

	private class Lv2PowerConnectedAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("Lv2State", "Drawanim");

			if (blockVib < 7) blockVib++;
			else			  mViewContext.removeAnimatable(this);
		}
	}

	private class Lv2PowerDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("Lv2State", "Drawanim");

			if (blockVib < 7) blockVib++;
			else			  mViewContext.removeAnimatable(this);
		}
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		SpriteHelper.DrawSprite(canvas, evolve, evolve.NextFrame(), SpriteHelper.DrawPosition.BottomCenter,0,
				-(int)(heightModifier2[animStage%8] * viewContext.getDensity()));
		viewContext.setState(new Lv2WaitState());
	}

	public boolean NeedRedraw() {
		return false;
	}

	public void OnClick(CoinBlockView viewContext) {
		// TODO Auto-generated method stub 
	}

	private class Lv2WaitState implements ICoinBlockViewState {
		
		TextPref mPref;
		int CliCount2;
		
		MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		
		public void OnClick(CoinBlockView viewContext) {
			Log.v("Lv2State", "OnClick3");
			Log.d("bugfix", "클릭 : " + viewContext);
			
			viewContext.setState(new Lv2WaitState());
			
			viewContext.removeAnimatable(lv2ofAnim);
			viewContext.removeAnimatable(lv2powerOn);
			viewContext.removeAnimatable(lv2wifiOn);
			
			animeSwitch = true;
			viewContext.addAnimatable(new Lv2ClickAnim());
			
			snd.seekTo(0);
			snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd.start();
				}
			});

			int textcode = (int)(Math.random()*9);
			String text = null;

			switch(textcode) {
			case 0:
				text = "?";
				break;

			case 1:
				text = "....";
				break;

			case 2:
				text = "..";
				break;

			case 3:
				text = "뀨잉?";
				break;

			case 4:
				text = "낑낑!";
				break;

			case 5:
				text = "뀨으";
				break;

			case 6:
				text = "뀨웅!!";
				break;

			case 7:
				text = "뀨!";
				break;

			case 8:
				text = "뀨우...";
				break;
			}

			Toast.makeText(CoinBlockView.Context, text, Toast.LENGTH_SHORT).show();

			try {
				mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
			} catch (Exception e) { 
				e.printStackTrace();
			}
			
			mPref.Ready();
			
			CliCount2 = mPref.ReadInt("clicount2", 0);			 
			CliCount2++;	

			mPref.WriteInt("clicount2", CliCount2);
			mPref.CommitWrite();
		}
		
		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			if(animeSwitch) SpriteHelper.DrawSprite(canvas, blankSprite, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
			else 			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
		}

		public boolean NeedRedraw() {
			return false;
		}

		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			Log.d("EvolveBugfix", " lv2진화");
			animeSwitch = false;
			DeviceConditionPage.UpdateIntroView();
			coinBlockView.setState(new Lv3_1State(coinBlockView));	
		}

		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			coinBlockView.removeAnimatable(lv2ofAnim);
			lv2ofAnim = new Lv2OftenAnim();			
			coinBlockView.addAnimatable(lv2ofAnim);
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			//coinBlockView.removeAnimatable(lv2Anim);
			coinBlockView.removeAnimatable(lv2ofAnim);
			//coinBlockView.removeAnimatable(lv2clAnim);
			coinBlockView.removeAnimatable(lv2powerOn);
			coinBlockView.removeAnimatable(lv2wifiOn);
		}

		@Override
		public void OnDblClick(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			lv2dblClick = new Lv2DblClickAnim();
			viewContext.addAnimatable(lv2dblClick);

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnSMSReceived(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			lv2planeOn = new Lv2PlaneOnAnim();
			viewContext.addAnimatable(lv2planeOn);

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
			lv2wifiOn = new Lv2WifiOnAnim();
			viewContext.addAnimatable(lv2wifiOn);

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
			lv2wifiOff = new Lv2WifiOffAnim();
			viewContext.addAnimatable(lv2wifiOff);

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

			viewContext.removeAnimatable(lv2powerOn);

			lv2powerOn = new Lv2PowerConnectedAnim();	
			viewContext.addAnimatable(lv2powerOn);

			Log.v("Lv2State", "addAnimatable");			

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
			Log.v("POWER", "OnPower");

			viewContext.removeAnimatable(lv2powerOff);

			lv2powerOff = new Lv2PowerDisconnectedAnim();	
			viewContext.addAnimatable(lv2powerOff);

			Log.v("Lv2State", "addAnimatable");			

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
			viewContext.removeAnimatable(lv2usbOn);

			lv2usbOn = new Lv2USBConnectedAnim();	
			viewContext.addAnimatable(lv2usbOn);

			Log.v("Lv2State", "addAnimatable");			

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnUSBDisconnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.removeAnimatable(lv2usbOff);

			lv2usbOff = new Lv2USBDisconnectedAnim();	
			viewContext.addAnimatable(lv2usbOff);

			Log.v("Lv2State", "addAnimatable");			

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}
		
		@Override
		public void OnHeadsetConnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			lv2headsetOn = new Lv2HeadsetConnectedAnim();
			viewContext.addAnimatable(lv2headsetOn);

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnHeadsetDisconnected(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			lv2headsetOff = new Lv2HeadsetDisconnectedAnim();
			viewContext.addAnimatable(lv2headsetOff);

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}

		@Override
		public void OnPlaneModeOn(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			lv2planeOn = new Lv2PlaneOnAnim();
			viewContext.addAnimatable(lv2planeOn);

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
			lv2planeOff = new Lv2PlaneOffAnim();
			viewContext.addAnimatable(lv2planeOff);

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}
	}

	private class Lv2Animation implements IAnimatable {
		//진동할때 올라오고, 상단에 남는 드로블
		private int flowerRaise = 4;
		private int animstage = 0;
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			mViewContext.removeAnimatable(this);
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

	private class Lv2OftenAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);

			if (blockVib < 13) blockVib++;
			else			   mViewContext.removeAnimatable(this);

			Log.v("tag4", "blockVib"+Integer.toString(blockVib));
		}
	}

	private class Lv2ClickAnim implements IAnimatable {
		private int spriteVib = 0; 
		
		public boolean AnimationFinished() {
			return false;
		}
		
		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, samsungSprite, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[spriteVib] * mViewContext.getDensity()), 0 );
			
			Log.d("bugfix","클릭 애니메이션");
			
			if (spriteVib < 13){
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
			mViewContext.setState(new Lv2WaitState());
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
