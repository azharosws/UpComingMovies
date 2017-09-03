package com.azhar.upcomingmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhar.upcomingmovies.pojo.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by WIN 10 on 8/28/2017.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    List<Result> list;
    ArrayList<Result> temp_list;
    boolean adult = true;
    View itemView = null;

    public MoviesAdapter(Context context, List<Result> list) {
        this.context = context;
        this.list = list;
        this.temp_list = new ArrayList<Result>();
        this.temp_list.addAll(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_movie;
        TextView tv_title, tv_id, tv_release_date, tv_adult;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_movie = (ImageView) itemView.findViewById(R.id.iv_movie);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_release_date = (TextView) itemView.findViewById(R.id.tv_release_date);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            tv_adult = (TextView) itemView.findViewById(R.id.tv_adult);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);


        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_title.setText(list.get(position).getOriginalTitle());

        if (list.get(position).getAdult() != true) {
            holder.tv_adult.setText("(U/A)");

        } else {
            holder.tv_adult.setText("(A)");
        }

        holder.tv_id.setText(""+list.get(position).getId());
        holder.tv_release_date.setText(list.get(position).getReleaseDate());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w342/" + list.get(position).getPosterPath()).into(holder.iv_movie);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("id :- " + list.get(position).getId());

                Intent i = new Intent(context, MoviesDetail.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", list.get(position).getId());
                i.putExtra("title", list.get(position).getOriginalTitle());
                i.putExtra("overview", list.get(position).getOverview());
                float numStar = list.get(position).getVoteAverage() / 2;
                i.putExtra("num_star", "" + numStar);
                context.startActivity(i);
            }
        });
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(temp_list);
        } else {
            for (Result result : temp_list) {
                if (result.getOriginalTitle().toLowerCase(Locale.getDefault()).contains(charText) || result.getReleaseDate().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(result);
                }
            }
        }
        notifyDataSetChanged();
    }
}
