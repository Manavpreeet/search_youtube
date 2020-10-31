package com.example.videosearcher;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context mContext;
    private List<VideoItem> mVideoList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView thumbnail;
        public TextView video_title, video_id, video_description;
        public RelativeLayout video_view;

        public MyViewHolder(View view) {

            super(view);

            thumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);
            video_title = (TextView) view.findViewById(R.id.video_title);
            video_id = (TextView) view.findViewById(R.id.video_id);
            video_description = (TextView) view.findViewById(R.id.video_description);
            video_view = (RelativeLayout) view.findViewById(R.id.video_view);
        }
    }

    public CustomAdapter(Context mContext, List<VideoItem> mVideoList) {
        this.mContext = mContext;
        this.mVideoList = mVideoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final VideoItem singleVideo = mVideoList.get(position);

        holder.video_id.setText("Video ID : "+singleVideo.getId()+" ");
        holder.video_title.setText(singleVideo.getTitle());
        holder.video_description.setText(singleVideo.getDescription());

        Picasso.get()
                .load(singleVideo.getThumbnailURL())
                .resize(480,270)
                .centerCrop()
                .into(holder.thumbnail);

        holder.video_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.e("tag", "clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}