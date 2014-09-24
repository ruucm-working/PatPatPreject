package com.exam.view;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.IAnimatable;
import com.exam.MediaAssets;
import com.exam.R;
import com.exam.Sprite;
import com.exam.SpriteHelper;
import com.exam.helper.TaskTimer;
import com.exam.helper.TextPref;
import com.exam.tab.DeviceConditionPage;
import com.exam.tab.IntroActivity;


public class Lv1State implements ICoinBlockViewState {
	
	// sprites
	Sprite sp2 = MediaAssets.getInstance().getSprite(R.drawable.samsung);
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
	Sprite flowerSprite = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
	Sprite bottom = MediaAssets.getInstance().getSprite(R.drawable.egg_break);

	// sounds
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	MediaPlayer snd1 = MediaAssets.getInstance().getSoundPlayer(R.raw.notify_sound);

	// vibration power controller
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 6, -6, 4, -4, 2, -2, 0, 0 };	// here

	
	
	Lv1Animation lv1Anim;
	Lv1ClickAnim lv1clAnim;
	Lv0_2OftenAnim lv0_2ofAnim;
	Lv0_2DblClickAnim lv0_2dblClick;
	
	Lv0_2WifiOnAnim lv0_2wifiOn;
	Lv0_2WifiOffAnim lv0_2wifiOff;

	Lv0_2PowerConnectedAnim lv0_2powerOn;
	Lv0_2PowerDisconnectedAnim lv0_2powerOff;

	Lv0_2USBConnectedAnim lv0_2usbOn;
	Lv0_2USBDisconnectedAnim lv0_2usbOff;

	Lv0_2HeadsetConnectedAnim lv0_2headsetOn;
	Lv0_2HeadsetDisconnectedAnim lv0_2headsetOff;

	Lv0_2PlaneOnAnim lv0_2planeOn;
	Lv0_2PlaneOffAnim lv0_2planeOff;

	Lv0_2SMSAnim lv0_2sms;
	
	private int animStage = 0;
	private int flowerRaise = 4;
	
	boolean fuck = false;   
	CoinBlockView context;

	public Lv1State(CoinBlockView viewContext) {
		context = viewContext;
//		lv1Anim = new Lv1Animation();
//		viewContext.addAnimatable(lv1Anim);

		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start(); 
			}
		});
	}
	
	
	
	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);

		
		
		//진동할때의 하단드로블
//				SpriteHelper.DrawSprite(canvas, bottom, 0, SpriteHelper.DrawPosition.BottomCenter,0,
//						-(int)(heightModifier[animStage] * viewContext.getDensity()));
//
//
//				animStage++;
//				if (animStage >= heightModifier.length){
//					viewContext.OnInit();
//					viewContext.setState(new Lv1WaitState(viewContext));
//					Log.d("Lv1State","setState(new Lv1WaitState");
//				}
//				
				
				
				SpriteHelper.DrawSprite(canvas, sp2, 0,
						SpriteHelper.DrawPosition.BottomCenter, 0, -(int) (flowerRaise * 4 * context.getDensity()));

				Sprite bottom2 = MediaAssets.getInstance().getSprite(R.drawable.eggs_break);
				SpriteHelper.DrawSprite(canvas, bottom2, 0, SpriteHelper.DrawPosition.BottomCenter);

				// Draw the flower
				if (flowerRaise < 8)
					flowerRaise++; 
				else{
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					viewContext.setState(new Lv1WaitState(viewContext));
					Log.d("Lv1State","setState(new Lv1WaitState");
				}
				
		
	}


	
	private class Lv1Animation implements IAnimatable {
		//진동할때 올라오고, 상단에 남는 드로블
		private int flowerRaise = 4;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp2, 0,
					SpriteHelper.DrawPosition.BottomCenter, 0, -(int) (flowerRaise * 4 * context.getDensity()));

			Sprite bottom2 = MediaAssets.getInstance().getSprite(R.drawable.eggs_break);
			SpriteHelper.DrawSprite(canvas, bottom2, 0, SpriteHelper.DrawPosition.BottomCenter);

			// Draw the flower
			if (flowerRaise < 8)
				flowerRaise++; 
		}
	}
	
	private class Lv0_2DblClickAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 16, -16, 8, -8, 4, -4, 0, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv0_2WifiOnAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv0_2WifiOffAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv0_2USBConnectedAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("USB", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv0_2USBDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("USB", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv0_2HeadsetConnectedAnim implements IAnimatable {
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

	private class Lv0_2HeadsetDisconnectedAnim implements IAnimatable {
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

	private class Lv0_2PlaneOnAnim implements IAnimatable {
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

	private class Lv0_2PlaneOffAnim implements IAnimatable {
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

	private class Lv0_2SMSAnim implements IAnimatable {
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

	private class Lv0_2PowerConnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}

	private class Lv0_2PowerDisconnectedAnim implements IAnimatable {
		private int blockVib = 0;	
		private int[] widthModifier = { 24, -24, 16, -16, 8, -8, 4, 0 };	// here

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			Log.v("WIFI", "Drawanim");

			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++;
		}
	}


	public boolean NeedRedraw() {
		return true;
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
		coinBlockView.removeAnimatable(lv0_2ofAnim);
		Log.d("Lv0_2State","OnInit2");
	}

	class Lv1WaitState implements ICoinBlockViewState {
		final MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;
		private int spriteVib = 0;
		
		
		

		long second = 0 ;
		
		//프레퍼런스 
		TextPref mPref;	

		boolean init = false;
		boolean lv0_1;
		public String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
		public String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";

//		public  boolean lv0_2;
//		public static  boolean lv1 ;
//		public static  boolean lv2 ;
//		public static  boolean lv3_1 ;
		
//		public static  boolean stateNum ;

		 int CliCount1 ;
		

		public Lv1WaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;
			
//			mViewContext.removeAnimatable(lv1Anim);	
//			lv1clAnim = new Lv1ClickAnim();
//			viewContext.addAnimatable(lv1clAnim);
//			
			/*
			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == Lv0WaitState.class)
					{
						Log.v("tag2", "lv0-run");

						
						mViewContext.addAnimat	able(lv0Anim);

						if (CoinBlockView.second >= 10 && CoinBlockView.second <45)	{
							mViewContext.removeAnimatable(lv0Anim);							
							mViewContext.setState(new DisabledState(mViewContext));
							mViewContext.setState(new Lv1State(mViewContext));

							Log.v("tag3", "Lv0WaitState-setState"); 
						}

						mViewContext.setState(new OftenState(mViewContext, flowerSprite)); 
						Log.v("tag3", "mViewContext.setState(new OftenState");

						v0Anim.Draw2(Bitmap.createBitmap(mViewContext.cwidth, mViewContext.cheight, Bitmap.Config.ARGB_8888));
						mViewContext.scheduleRedraw();
						 
					}
				}
			}, 3000);*/
		} 

		public void OnClick(CoinBlockView viewContext) {

			
			
			viewContext.removeAnimatable(lv1Anim);
			viewContext.removeAnimatable(lv1clAnim);
			
			lv1clAnim = new Lv1ClickAnim();			
			viewContext.addAnimatable(lv1clAnim);
			
			
			
			
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
			
			
			CliCount1 = mPref.ReadInt("clicount1", 0);			 
			CliCount1++;		

						
		
			
			
//	
//			init = mPref.ReadBoolean("initstate", false);	
//			lv0_1 = mPref.ReadBoolean("lv0_1state", false);
//			lv0_2 = mPref.ReadBoolean("lv0_2state", false);
//			lv1 = mPref.ReadBoolean("lv1state", false);
//			lv2 = mPref.ReadBoolean("lv2state", false);
//			lv3_1 = mPref.ReadBoolean("lv3_1state", false);

//			CliCount0_1 = mPref.ReadInt("clicount0_1", 0);
//			CliCount0_2 = mPref.ReadInt("clicount0_2", 0);
//			CliCount1 = mPref.ReadInt("clicount1", 0);
//			CliCount2 = mPref.ReadInt("clicount2", 0);
////			CliCount3 = mPref.ReadInt("clicount2", 0);

			
//			second = mPref.ReadInt("time", 0);
			
			
			Log.i("InitState","second "+second);
			
			
//			mPref.EndReady();
			
			
			
			
			

//			
//			if ( second == 0 && CliCountInit >=3 && init){
//				init = false;
//				lv0_1 = true;
//				mPref.WriteBoolean("initstate", init);	
//				mPref.WriteBoolean("lv0_1state", lv0_1);
//				mPref.CommitWrite();
//				
//				RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);
//				updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
//
//				
//				
//			}		
//			else{
//
			mPref.WriteInt("clicount1", CliCount1);
			mPref.CommitWrite();
//			}
		
		
		}
		

		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
 

		
			

			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, sp2, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[spriteVib] * context.getDensity()), 0 );


			if (spriteVib < 7) 
				spriteVib++;
		}

		public boolean NeedRedraw() { 
			return false;
		}

		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			coinBlockView.removeAnimatable(lv0_2ofAnim);
			lv0_2ofAnim = new Lv0_2OftenAnim();			
			coinBlockView.addAnimatable(lv0_2ofAnim);
		}
		
		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			
			IntroActivity.taskTimer1.isCanceled = false;
			TaskTimer taskTimer1 = new TaskTimer();
			taskTimer1.setTextView1(R.id.time0);
			taskTimer1.execute("");
			
//			CoinBlockView.lv1 = false;	
//			CoinBlockView.lv2 = true;	
//			CoinBlockView.mPref.Ready();			
//			CoinBlockView.mPref.WriteBoolean("lv1state", CoinBlockView.lv1);		
//			CoinBlockView.mPref.WriteBoolean("lv2state", CoinBlockView.lv2);	
//			CoinBlockView.mPref.CommitWrite();


			DeviceConditionPage.UpdateIntroView();
			
			coinBlockView.setState(new Lv2State(coinBlockView));


			//setContentView(R.drawable.background1, "레벨1임이제 ㅋㅋㅋㅋㅄ");


			Log.d("Lv0_2State","OnEvolve");
		}

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			coinBlockView.removeAnimatable(lv0_2ofAnim);
			coinBlockView.removeAnimatable(lv1clAnim);
			coinBlockView.removeAnimatable(lv0_2powerOn);
			coinBlockView.removeAnimatable(lv0_2wifiOn);
			coinBlockView.removeAnimatable(lv1Anim);

			
			coinBlockView.removeAnimatable(lv0_2dblClick);
			coinBlockView.removeAnimatable(lv0_2wifiOff);
			coinBlockView.removeAnimatable(lv0_2powerOff);
			coinBlockView.removeAnimatable(lv0_2usbOff);
			coinBlockView.removeAnimatable(lv0_2headsetOn);
			coinBlockView.removeAnimatable(lv0_2headsetOff);
			coinBlockView.removeAnimatable(lv0_2planeOn);
			coinBlockView.removeAnimatable(lv0_2planeOff);
			coinBlockView.removeAnimatable(lv0_2sms);
			
			

			
			
			Log.d("Lv0_2State","OnInit");
			
			
			
			
			
			Log.d("tag3","OnInit");
		}
/*
		@Override
		public void OnDblClick(CoinBlockView viewContext) {
			// TODO Auto-generated method stub
			viewContext.removeAnimatable(lv0_2dblClick);

			lv0_2dblClick = new Lv0_2DblClickAnim();			
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
			lv0_2planeOn = new Lv0_2PlaneOnAnim();
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

			viewContext.removeAnimatable(lv0_2clAnim);
			viewContext.removeAnimatable(lv0_2wifiOn);

			lv0_2wifiOn = new Lv0_2WifiOnAnim();	
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

			viewContext.removeAnimatable(lv0_2clAnim);
			viewContext.removeAnimatable(lv0_2wifiOff);

			lv0_2wifiOff = new Lv0_2WifiOffAnim();	
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

			lv0_2powerOn = new Lv0_2PowerConnectedAnim();	
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

			lv0_2powerOff = new Lv0_2PowerDisconnectedAnim();	
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
			lv0_2usbOn = new Lv0_2USBConnectedAnim();
			viewContext.addAnimatable(lv0_2usbOn);

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
			lv0_2usbOff = new Lv0_2USBDisconnectedAnim();
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
			lv0_2headsetOn = new Lv0_2HeadsetConnectedAnim();
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
			lv0_2headsetOff = new Lv0_2HeadsetDisconnectedAnim();
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
			lv0_2planeOn = new Lv0_2PlaneOnAnim();
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
			lv0_2planeOff = new Lv0_2PlaneOffAnim();
			viewContext.addAnimatable(lv0_2planeOff);

			snd1.seekTo(0);
			snd1.setOnSeekCompleteListener(new OnSeekCompleteListener() {
				public void onSeekComplete(MediaPlayer mp) {
					snd1.start();
				}
			});
		}
		
		*/

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
	
	private class Lv0_2OftenAnim implements IAnimatable {
		private int blockVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			/*
			SpriteHelper.DrawSprite(canvas, sp2, sp2.NextFrame(), SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);

			if (blockVib < 7)
				blockVib++; 

			Log.v("tag4", "blockVib"+Integer.toString(blockVib));
			*/
		}
	}

	private class Lv1ClickAnim implements IAnimatable {
		private int spriteVib = 0;

		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			// Draw the brick at bottom
			SpriteHelper.DrawSprite(canvas, sp2, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[spriteVib] * context.getDensity()), 0 );


			if (spriteVib < 7)
				spriteVib++;
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