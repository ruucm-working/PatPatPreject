package com.exam.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
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
import com.exam.TextPref;
import com.exam.coinBlockWidgetProvider;
import com.exam.tab.IntroActivity;
import com.exam.tab.viewPager01;


public class InitState implements ICoinBlockViewState {
	
	Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
	//진동할때 올라오고, 상단에 남는 드로블
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	private int animStage = 0;
	private int[] heightModifier = { 8, -8, 6, -6, 4, -4, 2, -2 };	
	private int[] widthModifier = { 6, -6, 4, -4, 2, -2, 0, 0 };	// here
	initAnimation initAnim;
    InitOftenAnim initofAnim;
    InitClickAnim initclAnim;
	boolean fuck = false;
	CoinBlockView context; 
	
	

	public InitState(CoinBlockView viewContext) {
		context = viewContext;
		
		//setContentView(R.drawable.background,"상자를 열어라ㅋㅋ!!!");
		
		initAnim = new initAnimation();
		initofAnim = new InitOftenAnim();
		
		viewContext.addAnimatable(initAnim);
		//viewContext.addAnimatable(initofAnim);
		
		
		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start();
			}
		});
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
		//진동할때의 하단드로블
		SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,0,
				-(int)(heightModifier[animStage] * viewContext.getDensity()));
		
	
		animStage++; 
		
		Log.v("tag3", "animstage");
		
		if (animStage >= heightModifier.length)
			viewContext.setState(new InitWaitState(viewContext));
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
		
		coinBlockView.removeAnimatable(initAnim);
		
		
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

//		public  boolean lv0_2;
//		public static  boolean lv1 ;
//		public static  boolean lv2 ;
//		public static  boolean lv3_1 ;
		
//		public static  boolean stateNum ;

		 int CliCountInit ;
//		public static int CliCount0_1 = 0 ;
//		public static int CliCount0_2 = 0;
//		public static int CliCount1 = 0;
//		public static int CliCount2 = 0;
		

		public InitWaitState(CoinBlockView viewContext) {
			mViewContext = viewContext;
			
			
			(new Handler()).postDelayed(new Runnable(){
				public void run() {
					if (mViewContext.getState().getClass() == InitWaitState.class)
					{
						//mViewContext.addAnimatable(initAnim);
				
						Log.v("tag2", "lv0-run");
						
						/*
						if (CoinBlockView.second >= 10 && CoinBlockView.second <45)	{
							
							mViewContext.removeAnimatable(initAnim);							
							mViewContext.setState(new DisabledState(mViewContext));
							mViewContext.setState(new Lv1State(mViewContext));
							
							Log.v("tag3", "Lv0WaitState-setState"); 
							 
							
							
						}
						 */
						
						//mViewContext.setState(new OftenState(mViewContext, flowerSprite)); 
						Log.v("tag3", "mViewContext.setState(new OftenState");
						
						//initAnim.Draw2(Bitmap.createBitmap(mViewContext.cwidth, mViewContext.cheight, Bitmap.Config.ARGB_8888));
						//mViewContext.scheduleRedraw();
						
					}
				}
			}, 3000);
			
			
		}

		public void OnClick(CoinBlockView viewContext) {
			
			
			
			
			
			
//			viewContext.setState(new InitWaitState(viewContext));
			viewContext.removeAnimatable(initclAnim);
			
			initclAnim = new InitClickAnim();			
			viewContext.addAnimatable(initclAnim);
			
			
			
			
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
			
			
			CliCountInit = mPref.ReadInt("clicountinit", 0);			 
			CliCountInit++;			
			
			

						
		
			
			
			
		

//			mPref.Ready();

			init = mPref.ReadBoolean("initstate", false);	
			lv0_1 = mPref.ReadBoolean("lv0_1state", false);
//			lv0_2 = mPref.ReadBoolean("lv0_2state", false);
//			lv1 = mPref.ReadBoolean("lv1state", false);
//			lv2 = mPref.ReadBoolean("lv2state", false);
//			lv3_1 = mPref.ReadBoolean("lv3_1state", false);

//			CliCount0_1 = mPref.ReadInt("clicount0_1", 0);
//			CliCount0_2 = mPref.ReadInt("clicount0_2", 0);
//			CliCount1 = mPref.ReadInt("clicount1", 0);
//			CliCount2 = mPref.ReadInt("clicount2", 0);
////			CliCount3 = mPref.ReadInt("clicount2", 0);

			
			second = mPref.ReadInt("time", 0);
			
			
			Log.i("InitState","second "+second);
			
			
//			mPref.EndReady();
			
			
			
			
			

			
			if ( second == 0 && CliCountInit >=3 && init){
				init = false;
				lv0_1 = true;
				mPref.WriteBoolean("initstate", init);	
				mPref.WriteBoolean("lv0_1state", lv0_1);
				mPref.CommitWrite();
				
				RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);
				updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());

				
				
			}		
			else{

			mPref.WriteInt("clicountinit", CliCountInit);
			mPref.CommitWrite();
			}
			
			
			
		}

		public void Draw(CoinBlockView viewContext, Bitmap canvas) {
			SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter);
		}

		public boolean NeedRedraw() { 
			return false;
		}

		
		@Override
		public void OnOften(CoinBlockView coinBlockView) {
			
		
			 
			 
			/*
			
			//애니매이션 1 - 성공 (버섯올라오기)
			coinBlockView.removeAnimatable(initAnim);
			
			initAnim = new initAnimation();			
			coinBlockView.addAnimatable(initAnim);
			
			*/
			
			
			
			//애니매이션 2 - 성공
			
			coinBlockView.removeAnimatable(initofAnim);
			initofAnim = new InitOftenAnim();			
			coinBlockView.addAnimatable(initofAnim);
			
			 
			
			
			 
			
  

			
		}
		@Override
		public void OnEvolve(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			coinBlockView.setState(new Lv0_1State(coinBlockView));
			 
			
			CoinBlockView.init = false;	
			CoinBlockView.lv0_1 = true;	
			
			CoinBlockView.mPref.Ready();			
			CoinBlockView.mPref.WriteBoolean("initstate", CoinBlockView.init);		
			CoinBlockView.mPref.WriteBoolean("lv0_1state", CoinBlockView.lv0_1);	
			CoinBlockView.mPref.CommitWrite();
			
			
			
//			coinBlockIntroActivity.taskTimer1.setTextView1(R.id.time0);
			
			
			Log.d("InitState","setTextView1");
			
			//coinBlockIntroActivity.taskTimer1.setTime(0);
//			coinBlockIntroActivity.taskTimer1.execute("");
			Log.d("InitState","execute");
			
			
			viewPager01.UpdateIntroView();
			
			Log.d("InitState","UpdateIntroView");
			//coinBlockIntroActivity.setContentView(R.layout.main);
			
			
			/*
			 
			
			//CoinBlockView.InitState = false;	
			CoinBlockView.Lv0State = true;	
			
			CoinBlockView.mPref.Ready();			
			//CoinBlockView.mPref.WriteBoolean("initstate", CoinBlockView.InitState);	
			CoinBlockView.mPref.WriteBoolean("lv0state", CoinBlockView.Lv0State);
			CoinBlockView.mPref.CommitWrite();
			*/
			
			
//			/setContentView(R.drawable.background0, "껒여껒여껒여껒여");
			
			
		
			
			Log.d("InitState","OnEvolve");
			
			 
		}

	

		@Override
		public void OnInit(CoinBlockView coinBlockView) {
			// TODO Auto-generated method stub
			

			coinBlockView.removeAnimatable(initAnim);	
			coinBlockView.removeAnimatable(initofAnim);
			coinBlockView.removeAnimatable(initclAnim);
			
			//coinBlockView.setState(new DisabledState(coinBlockView));
			
			Log.d("InitState","OnInit");
			 
			
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
			
			
			// Draw the brick at bottom
			
			//진동할때의 하단드로블
			SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);
			

						if (blockVib < 7) { 
							blockVib++;
						}
						
			
			Log.v("tag4", "blockVib"+Integer.toString(blockVib));
			
			/*
			if (blockVib >= 7){
				context.setState(new Lv0WaitState(context));
				Log.v("tag4", "blockVib >= heightModifier.length)"+Integer.toString(blockVib));
			}
			
			*/
		}

		
	}
	
	

	private class InitClickAnim implements IAnimatable {
		

		private int blockVib = 0;
		
		
		
		public boolean AnimationFinished() {
			return false;
		}

		public void Draw(Bitmap canvas) {
			
			
			// Draw the brick at bottom
			//Sprite sp1 = MediaAssets.getInstance().getSprite(R.drawable.mushroom);
			//진동할때의 하단드로블
			
			SpriteHelper.DrawSprite(canvas, sp1, 0, SpriteHelper.DrawPosition.BottomCenter,
					-(int)(widthModifier[blockVib] * context.getDensity()),0);
			

						if (blockVib < 7) { 
							blockVib++;
						}
			
		
						
		   
						
		
			/*
			if (blockVib >= 7){
				context.setState(new Lv0WaitState(context));
				Log.v("tag4", "blockVib >= heightModifier.length)"+Integer.toString(blockVib));
			}
			
			*/
			
			
			
			
		}

		
	}
	
	
	public void setContentView(int drawbleid, String txt) {
		

		Log.d("InitState","setContentView");
		
		IntroActivity instance = IntroActivity.getInstance();	

		Log.d("InitState","IntroActivity"+instance);
		//instance.setContentView(R.layout.main);		
		
		
		/*
		
		//set time
		TextView time = (TextView)instance.findViewById(R.id.time0);
		time.setText(Long.toString(instance.second));
		
		*/
		
		//set newstate's background img
		LinearLayout a = (LinearLayout)instance.findViewById(R.id.mainlinear);
		
		Log.d("InitState","LinearLayout");
		
		a.setBackgroundResource(drawbleid);
		

		Log.d("InitState","setBackgroundResource");
		 
		//set newstate's text
		TextView statetxt = (TextView)instance.findViewById(R.id.welcome);		
		statetxt.setText(txt);
		
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
