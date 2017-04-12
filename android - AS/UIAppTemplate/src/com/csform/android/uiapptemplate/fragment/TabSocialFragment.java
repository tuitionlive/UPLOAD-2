
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

import com.csform.android.uiapptemplate.CourseAdapter;
import com.csform.android.uiapptemplate.MainActivity;
import com.csform.android.uiapptemplate.Movie;
import com.csform.android.uiapptemplate.R;

import java.util.ArrayList;
import java.util.List;

public class TabSocialFragment extends Fragment   {

	private static final String ARG_POSITION = "position";


	private List<Movie> movieList1 = new ArrayList<>();
	private RecyclerView recyclerView;
	private CourseAdapter cAdapter;

	private int position;

	public static TabSocialFragment newInstance(int position) {
		TabSocialFragment f = new TabSocialFragment();
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
		final View rootView = inflater.inflate(R.layout.fragment_tab_social, container,
				false);
		recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view1);

		cAdapter = new CourseAdapter(movieList1);
		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(cAdapter);
		//recyclerView.addOnItemTouchListener();

		recyclerView.addOnItemTouchListener(
				new RecyclerItemClickListener(rootView.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
					@Override public void onItemClick(View view, int position) {
						System.out.println("Krishna"+movieList1.get(position).getUrl());
						Intent intent=new Intent(getActivity(),MainActivity.class);
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
		Movie movie = new Movie("iit jee", "Animation, Kids & Family", "2015","https://tuition.in/new/img/caipcc.jpg","Vision 40");
		movieList1.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","https://tuition.in/new/img/caipcc.jpg","Vision 40");
		movieList1.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","https://tuition.in/new/img/ssc cgl.jpg","Vision 40");
		movieList1.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);
		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);
		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);
		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);
		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);

		movie = new Movie("Inside Out", "Animation, Kids & Family", "2015","http://tuition.in","Vision 40");
		movieList1.add(movie);

		cAdapter.notifyDataSetChanged();
	}

}