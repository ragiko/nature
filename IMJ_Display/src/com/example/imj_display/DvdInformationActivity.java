package com.example.imj_display;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

@SuppressLint("NewApi")
public class DvdInformationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dvd_information);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		setTitle(title); 

		actionBar.addTab(actionBar
				.newTab()
				.setText("作品情報")
				.setTabListener(
						new MainTabListener<ProductDetail>(this, "f1",
								ProductDetail.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("商品情報")
				.setTabListener(
						new MainTabListener<Information>(this, "f2",
								Information.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("評価/レビュー")
				.setTabListener(
						new MainTabListener<Measure>(this, "f3",
								Measure.class)));
	}

	@SuppressLint("NewApi")
	public static class MainTabListener<T extends Fragment> implements
			TabListener {

		private Fragment fragment;
		private final Activity activity;
		private final String tag;
		private final Class<T> cls;

		public MainTabListener(Activity activity, String tag, Class<T> cls) {
			this.activity = activity;
			this.tag = tag;
			this.cls = cls;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (fragment == null) {
				fragment = Fragment.instantiate(activity, cls.getName());
				ft.add(android.R.id.content, fragment, tag);
			} else {
				ft.attach(fragment);
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (fragment != null) {
				ft.detach(fragment);
			}
		}
	}

}
