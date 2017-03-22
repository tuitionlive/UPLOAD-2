package com.csform.android.uiapptemplate;

/**
 * Created by Krishna on 22/02/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csform.android.uiapptemplate.util.ImageUtil;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView image;
        public TextView categoryName;
        public TextView title;
        public TextView text;
        public TextView resume;
        public TextView share;

        public MyViewHolder(View view) {
            super(view);


            image = (ImageView) view
                    .findViewById(R.id.list_item_google_cards_travel_image);
            categoryName = (TextView) view
                    .findViewById(R.id.list_item_google_cards_travel_category_name);
            title = (TextView) view
                    .findViewById(R.id.list_item_google_cards_travel_title);
            text = (TextView) view
                    .findViewById(R.id.list_item_google_cards_travel_text);
            resume = (TextView) view
                    .findViewById(R.id.list_item_google_cards_travel_explore);
            share = (TextView) view
                    .findViewById(R.id.list_item_google_cards_travel_share);






        }
    }


    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        holder.title.setText(movie.getTitle());
        holder.categoryName.setText(movie.getGenre());
        holder.text.setText(movie.getAcademy());
        //holder.resume.setText(movie.getYear());

        ImageUtil.displayImage(holder.image,
                movie.getUrl(), null);
        System.out.println(movie.getUrl()+"movie.getUrl()");
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
