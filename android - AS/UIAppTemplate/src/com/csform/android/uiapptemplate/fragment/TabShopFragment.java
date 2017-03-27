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

package com.csform.android.uiapptemplate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.csform.android.uiapptemplate.Movie;
import com.csform.android.uiapptemplate.MoviesAdapter;
import com.csform.android.uiapptemplate.ParallaxActivity;
import com.csform.android.uiapptemplate.R;

import java.util.ArrayList;
import java.util.List;

public class TabShopFragment extends Fragment   {

	private static final String ARG_POSITION = "position";


	private List<Movie> movieList = new ArrayList<>();
	private RecyclerView recyclerView;
	private MoviesAdapter mAdapter;

	private int position;

	public static TabShopFragment newInstance(int position) {
		TabShopFragment f = new TabShopFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_tab_shop, container,
				false);
		recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

		mAdapter = new MoviesAdapter(movieList);
		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(mAdapter);
		//recyclerView.addOnItemTouchListener();

		recyclerView.addOnItemTouchListener(
				new RecyclerItemClickListener(rootView.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
					@Override public void onItemClick(View view, int position) {
						System.out.println("Krishna"+movieList.get(position).getUrl());
						Intent intent=new Intent(getActivity(),ParallaxActivity.class);
						startActivity(intent);

					}

					@Override public void onLongItemClick(View view, int position) {
						// do whatever
					}
				})
		);
		prepareMovieData();

		return rootView;
	}



	public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
		private OnItemClickListener mListener;

		public interface OnItemClickListener {
			public void onItemClick(View view, int position);

			public void onLongItemClick(View view, int position);
		}

		GestureDetector mGestureDetector;

		public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
			mListener = listener;
			mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					return true;
				}

				@Override
				public void onLongPress(MotionEvent e) {
					View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
					if (child != null && mListener != null) {
						mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
					}
				}
			});
		}

		@Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
			View childView = view.findChildViewUnder(e.getX(), e.getY());
			if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
				mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
				return true;
			}
			return false;
		}

		@Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

		@Override
		public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
	}



	private void prepareMovieData() {
		Movie movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","https://tuition.in/new/img/caipcc.jpg","Vision 40");
		movieList.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","https://tuition.in/new/img/caipcc.jpg","Vision 40");
		movieList.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","https://tuition.in/new/img/ssc cgl.jpg","Vision 40");
		movieList.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);
		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);
		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);
		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);
		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList.add(movie);

		mAdapter.notifyDataSetChanged();
	}

}