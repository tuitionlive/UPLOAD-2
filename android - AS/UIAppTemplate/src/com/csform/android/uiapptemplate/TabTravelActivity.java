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

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csform.android.uiapptemplate.activity.LoginActivity;
import com.csform.android.uiapptemplate.fragment.EnrolledCoursesFragment;
import com.csform.android.uiapptemplate.fragment.PurchasesCoursesFragment;
import com.csform.android.uiapptemplate.fragment.TabTravelFragment;
import com.csform.android.uiapptemplate.fragment.TabUniversalFragment;
import com.csform.android.uiapptemplate.helper.SQLiteHandler;
import com.csform.android.uiapptemplate.helper.SessionManager;
import com.csform.android.uiapptemplate.view.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

public class TabTravelActivity extends ActionBarActivity {

	private MyPagerAdapter adapter;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private ImageView image;
	public TextView activity_tab_travel_titletabs;
	public ImageLoader imageLoader = ImageLoader.getInstance();
    private SQLiteHandler db;
    private SessionManager session;
    com.csform.android.uiapptemplate.font.RobotoTextView txtName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab_travel);
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(TabTravelActivity.this));
		activity_tab_travel_titletabs=(TextView)findViewById(R.id.activity_tab_travel_titletabs);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.activity_tab_travel_tabs);
		pager = (ViewPager) findViewById(R.id.activity_tab_travel_pager);
		//image = (ImageView) findViewById(R.id.activity_tab_travel_image);
txtName=(com.csform.android.uiapptemplate.font.RobotoTextView)findViewById(R.id.activity_tab_travel_label);
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
       // txtEmail.setText(email);

		//ImageUtil.displayImage(image, "https://www.tuition.in/img/hero-3.jpg", null);
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/corbel.ttf");
		activity_tab_travel_titletabs.setTypeface(typeFace);
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
				Toast.makeText(TabTravelActivity.this,
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
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(com.csform.android.uiapptemplate.TabTravelActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final ArrayList<String> tabNames = new ArrayList<String>() {
			{    add("Search Courses");
				add("Purchased");
				add("Enrolled");


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

				return TabTravelFragment.newInstance(position);
			} else if (position == 1) {
				return PurchasesCoursesFragment.newInstance(position);
			} else if (position == 2) {
				return EnrolledCoursesFragment.newInstance(position);
			} else if (position == 3) {
				return TabTravelFragment.newInstance(position);
			} else {
				return TabUniversalFragment.newInstance(position);
			}
		}
	}
}