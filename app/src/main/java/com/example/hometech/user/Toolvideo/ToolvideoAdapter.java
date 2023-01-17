package com.example.hometech.user.Toolvideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.Config;
import com.example.hometech.R;
import java.util.ArrayList;

public class ToolvideoAdapter extends RecyclerView.Adapter<ToolvideoAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<VideoModel> dataModelArrayList;
    Context c;

    public ToolvideoAdapter(Context ctx, ArrayList<VideoModel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public ToolvideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tool_card, parent, false);
        ToolvideoAdapter.MyViewHolder holder = new ToolvideoAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final VideoModel product = dataModelArrayList.get(position);

        holder.tvtool.setText("Tool Name: " + product.getToolname());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoModel p = dataModelArrayList.get(position);
                String url = p.getToolurl();


                Intent i = new Intent(c, Viewvideo.class);
                i.putExtra("videos",url);
                c.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvtool;
         CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtool = itemView.findViewById(R.id.tvtool);
            cardView = itemView.findViewById(R.id.cards);
        }
    }

    }