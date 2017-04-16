/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.csform.android.uiapptemplate;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csform.android.uiapptemplate.fragment.Classroom;
import com.csform.android.uiapptemplate.fragment.TabSocialFragment;
import com.csform.android.uiapptemplate.fragment.TabTravelFragment;
import com.csform.android.uiapptemplate.fragment.TabUniversalFragment;
import com.csform.android.uiapptemplate.view.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class TabShopActivity extends ActionBarActivity {

	private MyPagerAdapter adapter;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private ImageView image;
	public TextView activity_tab_travel_titletabs;
	public ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab_shop);

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(TabShopActivity.this));
	//	activity_tab_travel_titletabs=(TextView)findViewById(R.id.activity_tab_travel_titletabs);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.activity_tab_travel_tabs);
		pager = (ViewPager) findViewById(R.id.activity_tab_travel_pager);
		//image = (ImageView) findViewById(R.id.activity_tab_travel_image);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(getIntent().getExtras().getString("name"));
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#317EEC")));

        Bundle bundle = new Bundle();
        bundle.putString("url", getIntent().getExtras().getString("url"));
// set Fragmentclass Arguments
        System.out.println("url tab shop"+getIntent().getExtras().getString("url"));
        Classroom fragobj = new Classroom();
        fragobj.setArguments(bundle);

		//ImageUtil.displayImage(image, "https://www.tuition.in/img/hero-3.jpg", null);
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/corbel.ttf");
	//	activity_tab_travel_titletabs.setTypeface(typeFace);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setCurrentItem(3);

		tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
			@Override
			public void onTabReselected(int position) {
				Toast.makeText(TabShopActivity.this,
						"Tab reselected: " + position, Toast.LENGTH_SHORT)
						.show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}
    @Override
    public void onBackPressed()
    {
        // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it

            {
                // Standard back button implementation (for example this could close the app)
                super.onBackPressed();
            }

    }


	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final ArrayList<String> tabNames = new ArrayList<String>() {
			{
				add("Classroom");
				add("Handouts");
				add("Announcements");


			}
		};

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabNames.get(position);
		}

		@Override
		public int getCount() {
			return tabNames.size();
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {

				return Classroom.newInstance(position);
			} else if (position == 1) {
				return Classroom.newInstance(position);
			} else if (position == 2) {
				return TabSocialFragment.newInstance(position);
			} else if (position == 3) {
				return TabTravelFragment.newInstance(position);
			} else {
				return TabUniversalFragment.newInstance(position);
			}
		}
	}

}