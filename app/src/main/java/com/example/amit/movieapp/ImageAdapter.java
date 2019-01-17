package com.example.amit.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final String[] mMovieURLs;
    private final String[] mTitles;
    private Context mContext;
    private Integer[] mMovieIds;

    public ImageAdapter(Context context, String[] movieUrls, String[] mTitles, Integer[] mMovieIds) {
        mMovieURLs = movieUrls;
        this.mTitles = mTitles;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.mMovieIds = mMovieIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = mLayoutInflater.inflate(R.layout.row_image,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String movieURL = mMovieURLs[i];
        loadImage(viewHolder.imageView,movieURL);
        viewHolder.titleText.setText(mTitles[i]);

        viewHolder.setOnItenClickListener(new OnItenClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(mContext,"Long Click: " + mTitles[position],Toast.LENGTH_LONG ).show();
                else
                    Toast.makeText(mContext,"Short Click: " + mTitles[position],Toast.LENGTH_SHORT ).show();

                startDetail(view,i);
            }
        });

    }

    private void startDetail(View view,int position){
        Bundle bundle = new Bundle();
        bundle.putInt("id",mMovieIds[position]);
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }

    private void loadImage(ImageView imageView, String movieURL) {
        Picasso.with(imageView.getContext()).load(movieURL).fit().into(imageView);

    }

    @Override
    public int getItemCount() {
        return mMovieURLs.length;
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.titleText)
        TextView titleText;

        private OnItenClickListener onItenClickListener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void setOnItenClickListener(OnItenClickListener onItenClickListener) {
            this.onItenClickListener = onItenClickListener;
        }

        @Override
        public void onClick(View v) {
            onItenClickListener.onClick(v,getAdapterPosition(),false);

        }

        @Override
        public boolean onLongClick(View v) {
            onItenClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
}
