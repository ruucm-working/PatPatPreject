package com.exam.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;
<<<<<<< HEAD
import android.os.*;
import android.util.*;
import android.widget.*;
=======
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.exam.CoinBlockWidgetApp;
import com.exam.IAnimatable;
import com.exam.MediaAssets;
import com.exam.R;
import com.exam.Sprite;
import com.exam.SpriteHelper;
import com.exam.coinBlockWidgetProvider;
import com.exam.helper.TextPref;
import com.exam.tab.IntroActivity;
import com.exam.tab.DeviceConditionPage;
>>>>>>> master

import com.exam.*;
import com.exam.tab.DeviceConditionPage;
import com.exam.tab.IntroActivity;

public class InitState implements ICoinBlockViewState {
	
	Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
	Sprite blankSprite 	= MediaAssets.getInstance().getSprite(R.drawable.blankimage);
	
	//진동할때 올라오고, 상단에 남는 드로블
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 6, -6, 4, -4, 2, -2, 0, 0 };	// here
	
	boolean fuck = false;
	
	CoinBlockView mViewContext;
	
	boolean animeSwitch = false;
	
	public InitState(CoinBlockView viewContext) {
		mViewContext = viewContext;
		
		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start();
			}
		});
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
		//진동할때의 하단드로블
		
		SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,0,
				-(int)(heightModifier[animStage] * viewContext.getDensity()));
		viewContext.setState(new InitWaitState());
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
	}

	class InitWaitState implements ICoinBlockViewState {
		Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
		//진동후의, 하단 드로블
		final MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup);
		CoinBlockView mViewContext;
		
		long second = 0 ;
		
		//프레퍼런스
		TextPref mPref;	

		boolean init = false;
		boolean lv0_1;
		public String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
		public String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";

		int CliCountInit;	

		public InitWaitState() {
//			(new Handler()).postDelayed(new Runnable(){
//				public void run() {
//					if (mViewContext.getState().getClass() == InitWaitState.class)
//					{
//						Log.v("tag2", "lv0-run");
//						Log.v("tag3", "mViewContext.setState(new OftenState");						
//					}
//				}
//			}, 3000);
		}

		public void OnClick(CoinBlockView viewContext) {
			
			Log.d("bugfix", "클릭 : " + viewContext);
			if(!animeSwitch) {
				viewContext.setState(new InitWaitState());
				
				animeSwitch = true;
				viewContext.addAnimatable(new InitClickAnim());
				
				snd.seekTo(0);
				snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
					public void onSeekComplete(MediaPlayer mp) {
						snd.start();
					}
				}); //사운드 재생
							
				try {
					mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
				} catch (Exception e) { 
					e.printStackTrace();
				}
				
				mPref.Ready();			
				
				CliCountInit = mPref.ReadInt("clicountinit", 0);			 
				CliCountInit++;			
	
				init = mPref.ReadBoolean("initstate", false);	
				lv0_1 = mPref.ReadBoolean("lv0_1state", false);
				
				second = mPref.ReadInt("time", 0);
				
				Log.i("InitState","second "+second);
				
				if ( second == 0 && CliCountInit >=3 && init){
					init = false;
					lv0_1 = true;
					mPref.WriteBoolean("initstate", init);	
					mPref.WriteBoolean("lv0_1state", lv0_1);
					mPref.CommitWrite();
					
					RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);
					updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
		
				}else{
	
				mPref.WriteInt("clicountinit", CliCountInit);
				mPref.CommitWrite();
				
				}
			}
		}

		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			if(animeSwitch) SpriteHelper.DrawSprite(canvas, blankSprite, 0, SpriteHelper.DrawPosition.BottomCenter, 0, 0 );
			else 			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
		}

		public boolean NeedRedraw() {
			return false;
		}

		
		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			
		}
		
		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			Log.d("EvolveBugfix", " lv0_진화");
			
			animeSwitch = false;
			coinBlockView.setState(new Lv0_1State(coinBlockView));
			
			CoinBlockView.init = false;	
			CoinBlockView.lv0_1 = true;	
			
			CoinBlockView.mPref.Ready();			
			CoinBlockView.mPref.WriteBoolean("initstate", CoinBlockView.init);		
			CoinBlockView.mPref.WriteBoolean("lv0_1state", CoinBlockView.lv0_1);	
			CoinBlockView.mPref.CommitWrite();
			
			IntroActivity.taskTimer1.setTextView1(R.id.time0);
			
			Log.d("InitState","setTextView1");
			
			//IntroActivity.taskTimer1.setTime(0);
			IntroActivity.taskTimer1.execute("");
			Log.d("InitState","execute");
			
			DeviceConditionPage.UpdateIntroView();
			
			Log.d("InitState","UpdateIntroView");
			Log.d("InitState","OnEvolve");
			 
		}

	

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			Log.d("InitState","OnInit");
			
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

	private class initAnimation implements IAnimatable {
		//진동할때 올라오고, 상단에 남는 드로블
		
		private int flowerRaise = 4;


		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			
			
		}

		
	}
	
	private class InitOftenAnim implements IAnimatable {
		

		private int blockVib = 0;
		
		
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			//진동할때의 하단드로블
			SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * mViewContext.getDensity()),0);
		}

		
	}
	
	

	private class InitClickAnim implements IAnimatable {
		
		private int spriteVib = 0;
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,
				-(int)(widthModifier[spriteVib] * mViewContext.getDensity()),0);
			
			if (spriteVib < 7) spriteVib++;
			else			   animeRemove(this);
		}
	}
	
	private void animeRemove(IAnimatable animeObject)
	{
		if(animeSwitch){
			animeSwitch = false;
			mViewContext.removeAnimatable(animeObject);
			mViewContext.setState(new InitWaitState());
			Log.d("bugfix","wait다시호출 ");
		}else{
			mViewContext.removeAnimatable(animeObject);
		}
		Log.d("bugfix","애니메이터블 삭제");
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
