package com.ruucm.patpat;

import java.util.HashSet;
import java.util.Set;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class CoinBlockView {
	public static String INTENT_ON_CLICK_FORMAT = "com.gueei.mario.coinBlock.id.%d.click";
	private static final int REFRESH_RATE = 60;
	public static Context Context = null;
	
	private volatile Set<IAnimatable> Children;
	private static CoinBlockView instance;
	
	private float density;
	private int cheight, cwidth;
	private long lastRedrawMillis = 0;
	public static int mWidgetId;
	
	//for evolve
	public static long second = 0;



	//static variables
	public static ICoinBlockViewState state;

	static boolean init = false;
	public static boolean lv0_1;
	public static boolean lv0_2;
	public static  boolean lv1 ;
	public static  boolean lv2 ;
	public static  boolean lv3_1 ;
	
	public static  boolean stateNum ;

	public static int CliCountInit = 0 ;
	public static int CliCount0_1 = 0 ;
	public static int CliCount0_2 = 0;
	public static int CliCount1 = 0;
	public static int CliCount2 = 0;

	public CoinBlockView(Context context, int widgetId) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);

		density = metrics.density;
		cwidth = (int) (233* metrics.density);
		cheight = (int) (197* metrics.density);
 
		Children = new HashSet<IAnimatable>();
		mWidgetId = widgetId;

		//context for toast
		Context = context;
		
		setState(new Lv3_1State(this));
		
		Log.d("addClickIntent","setState(new Lv3_1State(this));");
 
		

	}

	public static CoinBlockView getInstance() {
		return instance;
	}

	
	public static Context getContext() {
		return (PatpatWidgetApp.getApplication());
	}

	public float getDensity() {
		return density;
	}

	public int getmWidgetId() {
		return mWidgetId;
	}

	public void OnClick() {
		state.OnClick(this);
	}


	public void OnOften() {
		state.OnOften(this);
		//		/scheduleRedraw();
	}

	public void OnEvolve() {
		state.OnEvolve(this);
	}

	public void OnHeadsetConnected() {
		state.OnHeadsetConnected(this);
		Log.d("HEADSET", "Headset Connected");
	}

	public void OnHeadsetDisconnected() {
	}

	public void Redraw(Context context) {
		RemoteViews rviews = new RemoteViews(context.getPackageName(), R.layout.coin_block_widget);
		
		//animatable drawing
		/*
		IAnimatable[] child = new IAnimatable[Children.size()];
		Children.toArray(child);

		for (int i = 0; i < child.length; i++) {
			child[i].Draw(canvas);
			if (child[i].AnimationFinished())
				Children.remove(child[i]);
		}
 
		
		//state drawing
		state.Draw(this,canvas);
//		rviews.setImageViewBitmap(R.id.block, canvas);
*/		rviews.setImageViewResource(R.id.block, R.drawable.spin_animation);
//		ImageView img = (ImageView) findViewById(R.id.img);
//		AnimationDrawable frameAnimation = (AnimationDrawable) rviews.
		
		
		updateClickIntent(rviews);
		AppWidgetManager.getInstance(context).updateAppWidget(mWidgetId, rviews);

		lastRedrawMillis = SystemClock.uptimeMillis();
		

		if (state.NeedRedraw() || Children.size() > 0)
			scheduleRedraw();
	}

	void scheduleRedraw() {
		
		long nextRedraw = lastRedrawMillis + REFRESH_RATE;
		
		
		nextRedraw = nextRedraw > SystemClock.uptimeMillis() ? nextRedraw :
			SystemClock.uptimeMillis() + REFRESH_RATE;
		
		scheduleRedrawAt(nextRedraw);
	}

	private  void scheduleRedrawAt(long timeMillis) {
		(new Handler()).postAtTime(new Runnable() {
			public void run() {
				Redraw(PatpatWidgetApp.getApplication());
			}
		}, timeMillis);
	}

	
	
	public synchronized void addAnimatable(IAnimatable child)
	{
		Children.add(child);
		scheduleRedraw();
	}

	public synchronized void removeAnimatable(IAnimatable child)
	{
		Children.remove(child);
	}
	
	
	
	public  void setState(ICoinBlockViewState newState) {
		Log.d("addClickIntent","setState : "+newState);
		
		state = newState;
		scheduleRedraw();
	}
	
	public ICoinBlockViewState getState(){
		return state;
	}

	private void updateClickIntent(RemoteViews rviews)
	{
		Intent intent = new Intent(String.format(INTENT_ON_CLICK_FORMAT, mWidgetId));
		intent.setClass(getContext(), coinBlockWidgetProvider.class);
		intent.putExtra("widgetId", mWidgetId);
		PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		rviews.setOnClickPendingIntent(R.id.widget, pi);
	}
}
