package com.csform.android.uiapptemplate;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.csform.android.uiapptemplate.adapter.DragAndDropShopAdapter;
import com.csform.android.uiapptemplate.adapter.DrawerMediaAdapter;
import com.csform.android.uiapptemplate.fragment.CheckAndRadioBoxesFragment;
import com.csform.android.uiapptemplate.fragment.DialogFragment;
import com.csform.android.uiapptemplate.fragment.ImageGalleryFragment;
import com.csform.android.uiapptemplate.fragment.LeftMenusFragment;
import com.csform.android.uiapptemplate.fragment.ListViewsFragment;
import com.csform.android.uiapptemplate.fragment.LogInPageFragment;
import com.csform.android.uiapptemplate.fragment.ParallaxEffectsFragment;
import com.csform.android.uiapptemplate.fragment.ProgressBarsFragment;
import com.csform.android.uiapptemplate.fragment.SearchBarsFragment;
import com.csform.android.uiapptemplate.fragment.ShapeImageViewsFragment;
import com.csform.android.uiapptemplate.fragment.SplashScreensFragment;
import com.csform.android.uiapptemplate.fragment.TabsFragment;
import com.csform.android.uiapptemplate.fragment.TextViewsFragment;
import com.csform.android.uiapptemplate.fragment.WizardFragment;
import com.csform.android.uiapptemplate.model.DrawerItem;
import com.csform.android.uiapptemplate.util.DummyContent;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.csform.android.uiapptemplate.view.FullscreenVideo;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity   {

	private ListView mDrawerList;
	private List<DrawerItem> mDrawerItems;
    private List<Integer> mDraweint;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private DynamicListView mDynamicListView;
	private GoogleApiClient client;
    private Image img;

	private Handler mHandler;

	private boolean mShouldFinish = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageLoader imageLoader = ImageLoader.getInstance();
		if (!imageLoader.isInited()) {
			imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		}

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
				R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mTitle = mDrawerTitle = getTitle();
		mDrawerList = (ListView) findViewById(R.id.list_view);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		prepareNavigationDrawerItems();
        View headerView = getLayoutInflater().inflate(
                R.layout.header_navigation_drawer_media, mDrawerList, false);

        ImageView iv = (ImageView) headerView
                .findViewById(R.id.header_navigation_drawer_media_image);
        ImageUtil
                .displayRoundImage(
                        iv,
                        "http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg",
                        null);

        mDrawerList.addHeaderView(headerView);
		mDrawerList.setAdapter(new DrawerMediaAdapter(this));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mHandler = new Handler();

		if (savedInstanceState == null) {
			int position = 0;
			selectItem(position, mDrawerItems.get(position).getTag());
			mDrawerLayout.openDrawer(mDrawerList);
		}

		VideoView videoView = (VideoView) findViewById(R.id.videoView1);

		//Creating MediaController
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);

		//specify the location of media file
		Uri uri = Uri.parse("rtmps://587fca38a320b.streamlock.net:443/vod/mp4:686321303.mp4");

		//Setting MediaController and URI, then starting the videoView
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(uri);
		videoView.requestFocus();
		videoView.start();
		videoView.setOnTouchListener(new View.OnTouchListener() {
										 @Override
										 public boolean onTouch(View v, MotionEvent event) {
											 System.out.println("Krishna");
											 // mDynamicListView.setVisibility(View.GONE);

											 Intent intent = new Intent(getBaseContext(),FullscreenVideo.class);

											 startActivity(intent);
											 return false;
										 }
									 }
		);


		//proceed.setOnClickListener(this);

		mDynamicListView = (DynamicListView) findViewById(R.id.activity_list_view_drag_and_drop_dynamic_listview);
		mDynamicListView.setDividerHeight(0);

		setUpDragAndDrop();
		Toast.makeText(this, "Long press an item to start dragging",
				Toast.LENGTH_SHORT).show();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Drag and Drop Shop");
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	@Override
	public void onBackPressed() {
		if (!mShouldFinish && !mDrawerLayout.isDrawerOpen(mDrawerList)) {
			Toast.makeText(getApplicationContext(), R.string.confirm_exit,
					Toast.LENGTH_SHORT).show();
			mShouldFinish = true;
			mDrawerLayout.openDrawer(mDrawerList);
		} else if (!mShouldFinish && mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			super.onBackPressed();
		}
	}

	private void prepareNavigationDrawerItems() {
		mDrawerItems = new ArrayList<>();
        mDraweint =new ArrayList<>();

        mDraweint.add(new Integer(R.drawable.background_media));

		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_list_views,
				R.string.drawer_title_list_views,
				DrawerItem.DRAWER_ITEM_TAG_LIST_VIEWS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_parallax,
				R.string.drawer_title_parallax,
				DrawerItem.DRAWER_ITEM_TAG_PARALLAX));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_left_menus,
				R.string.drawer_title_left_menus,
				DrawerItem.DRAWER_ITEM_TAG_LEFT_MENUS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_login_page,
				R.string.drawer_title_login_page,
				DrawerItem.DRAWER_ITEM_TAG_LOGIN_PAGE_AND_LOADERS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_image_gallery,
				R.string.drawer_title_image_gallery,
				DrawerItem.DRAWER_ITEM_TAG_IMAGE_GALLERY));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_shape_image_views,
				R.string.drawer_title_shape_image_views,
				DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_progress_bars,
				R.string.drawer_title_progress_bars,
				DrawerItem.DRAWER_ITEM_TAG_PROGRESS_BARS));
		mDrawerItems.add(new DrawerItem(
				R.string.drawer_icon_check_and_radio_buttons,
				R.string.drawer_title_check_and_radio_buttons,
				DrawerItem.DRAWER_ITEM_TAG_CHECK_AND_RADIO_BOXES));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_splash_screens,
				R.string.drawer_title_splash_screens,
				DrawerItem.DRAWER_ITEM_TAG_SPLASH_SCREENS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_search_bars,
				R.string.drawer_title_search_bars,
				DrawerItem.DRAWER_ITEM_TAG_SEARCH_BARS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_text_views,
				R.string.drawer_title_text_views,
				DrawerItem.DRAWER_ITEM_TAG_TEXT_VIEWS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_dialogs,
				R.string.drawer_title_dialogs,
				DrawerItem.DRAWER_ITEM_TAG_DIALOGS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_tabs,
				R.string.drawer_title_tabs,
				DrawerItem.DRAWER_ITEM_TAG_TABS));
		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_wizards,
				R.string.drawer_title_wizards,
				DrawerItem.DRAWER_ITEM_TAG_WIZARDS));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position, mDrawerItems.get(position).getTag());
		}
	}

	private void selectItem(int position, int drawerTag) {
		Fragment fragment = getFragmentByDrawerTag(drawerTag);
		commitFragment(fragment);

		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerItems.get(position).getTitle());
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	private Fragment getFragmentByDrawerTag(int drawerTag) {
		Fragment fragment;
		if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_SPLASH_SCREENS) {
			fragment = SplashScreensFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_PROGRESS_BARS) {
			fragment = ProgressBarsFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS) {
			fragment = ShapeImageViewsFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_TEXT_VIEWS) {
			fragment = TextViewsFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_SEARCH_BARS) {
			fragment = SearchBarsFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_LOGIN_PAGE_AND_LOADERS) {
			fragment = LogInPageFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_IMAGE_GALLERY) {
			fragment = ImageGalleryFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_CHECK_AND_RADIO_BOXES) {
			fragment = CheckAndRadioBoxesFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_LEFT_MENUS) {
			fragment = LeftMenusFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_LIST_VIEWS) {
			fragment = ListViewsFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_PARALLAX) {
			fragment = ParallaxEffectsFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_DIALOGS) {
			fragment = DialogFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_WIZARDS) {
			fragment = WizardFragment.newInstance();
		} else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_TABS) {
			fragment = TabsFragment.newInstance();
		} else {
			fragment = new Fragment();
		}
		mShouldFinish = false;
		return fragment;
	}

	private class CommitFragmentRunnable implements Runnable {

		private Fragment fragment;

		public CommitFragmentRunnable(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void run() {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
		}
	}

	public void commitFragment(Fragment fragment) {
		// Using Handler class to avoid lagging while
		// committing fragment in same time as closing
		// navigation drawer
		mHandler.post(new CommitFragmentRunnable(fragment));
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/

	private void setUpDragAndDrop() {
		final DragAndDropShopAdapter adapter = new DragAndDropShopAdapter(this,
				DummyContent.getDummyModelDragAndDropShopList());
		mDynamicListView.setAdapter(adapter);
		mDynamicListView.enableDragAndDrop();
		TouchViewDraggableManager tvdm = new TouchViewDraggableManager(
				R.id.icon);
		mDynamicListView.setDraggableManager(tvdm);

	}


	public void onClick(VideoView videoView1) {
		Toast.makeText(this, "Proceed...", Toast.LENGTH_SHORT).show();
	}

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("DragAndDropShop Page") // TODO: Define a title for the content shown.
				// TODO: Make sure this auto-generated URL is correct.
				.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
				.build();
		return new Action.Builder(Action.TYPE_VIEW)
				.setObject(object)
				.setActionStatus(Action.STATUS_TYPE_COMPLETED)
				.build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}
}