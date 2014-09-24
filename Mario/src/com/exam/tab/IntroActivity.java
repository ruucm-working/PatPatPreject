
package com.exam.tab;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.exam.R;
import com.exam.helper.TaskTimer;
import com.exam.helper.TextPref;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI
 * that switches between tabs and also allows the user to perform horizontal
 * flicks to move between the tabs.
 */
public class IntroActivity extends SherlockFragmentActivity {
    TabHost mTabHost;
    ViewPager  mViewPager;
    TabsAdapter mTabsAdapter; 
    
    
    private static final int[] ICONS = new int[] {
        R.drawable.perm_group_banana,
        R.drawable.perm_group_heart,
        
    };
    
    
    //penetrating timer value
    public static  TaskTimer taskTimer1 = new TaskTimer();
    
    //for method in Tasktimer 
    private static IntroActivity instance;
    public static 	IntroActivity getInstance() {
        return instance;
    }
    
    //for Activity anim
    @Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        instance = this;

        
        
        Log.d("IntroActivity","button ");
      
        
        //for Activity anim
		this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);	
        setContentView(R.layout.simple_tabs);
        
        
        //for adding listviewpager
        mTabHost = (TabHost)findViewById(android.R.id.tabhost); 
        mTabHost.setup();

        mViewPager = (ViewPager)findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter(this, /*mTabHost,*/ mViewPager);

//        mTabsAdapter.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
//                FragmentStackSupport.CountingFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("deice condition").setIndicator("Device Condition"),
        		DeviceConditionPage.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("deice state").setIndicator("Device State"),
        		DeviceStatePage.class, null);
//        mTabsAdapter.addTab(mTabHost.newTabSpec("custom").setIndicator("Custom"),
//                LoaderCustomSupport.AppListFragment.class, null);
//        mTabsAdapter.addTab(mTabHost.newTabSpec("throttle").setIndicator("Throttle"),
//                LoaderThrottleSupport.ThrottledLoaderListFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        
        
        //for tabindicator
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        
        
        
        
        
        
        
        
        
//        
//        //Read Preference (presetting for )  
//  		File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SsdamSsdam"); // dir : 생성하고자 하는 경로
//  		
//  		Log.d("coinBlockIntroActivity", "saveDir(saveDir)fbPref.Ready();");
//  		
//  		if(!saveDir.exists()) 
//  		{
//  			saveDir.mkdirs();
//  		}
//
//
//  		try {
//  			mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
//  			Log.d("coinBlockIntroActivity", "TextPref(intent)fbPref.Ready();");
//  			fbPref = new TextPref("mnt/sdcard/SsdamSsdam/facebookprofile.txt");
//  			Log.d("coinBlockIntroActivity", "fbPref(intent)fbPref.Ready();");
//  			
//
//  		} catch (Exception e) { 
//  			e.printStackTrace();
//  		}       
//  		mPref.Ready();
//  		Log.d("coinBlockIntroActivity", "mPref.Ready();(intent)fbPref.Ready();");
//		fbPref.Ready();
//		Log.d("coinBlockIntroActivity", "fbPref.Ready();(intent)fbPref.Ready();");
//		
//
//		//set Main Background Image & Text
//		
//		
//		userId = fbPref.ReadString("userId", "");
//  		String userFirstName = fbPref.ReadString("userFirstName", "");
//  		String userLastName = fbPref.ReadString("userLastName", "");
//		
//  		
//  		
//		init = mPref.ReadBoolean("initstate", false);	
//		lv0_1 = mPref.ReadBoolean("lv0_1state", false);
//		lv0_2 = mPref.ReadBoolean("lv0_2state", false);
//		lv1 = mPref.ReadBoolean("lv1state", false);
//		lv2 = mPref.ReadBoolean("lv2state", false);
//        
        
        
        
        
        
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

    /**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentPagerAdapter
            implements /*TabHost.OnTabChangeListener, */ViewPager.OnPageChangeListener, IconPagerAdapter {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, /*TabHost tabHost,*/ ViewPager pager) {
        	super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = null;
            mViewPager = pager;
//            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
//            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
//            tabSpec.setContent(new DummyTabFactory(mContext));
//            String tag = tabSpec.getTag();

			TabInfo info = new TabInfo("tag", clss, args);
			mTabs.add(info);
//			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
//        return null;
        }

      /*  @Override
        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
        }*/

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

	
		@Override
		public int getIconResId(int index) {
			return ICONS[index];
		}
    }
    
    
    
  
    
    
    
}




