package com.example.hometech.FeedbackList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class fbAdapter extends RecyclerView.Adapter<fbAdapter.MyViewHolder> implements Filterable {

    private LayoutInflater inflater;
    private ArrayList<fbModel> dataModelArrayList;
    private Context c;

    public fbAdapter(Context ctx, ArrayList<fbModel> dataModelArrayList){
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.flist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final fbModel product = dataModelArrayList.get(position);

        holder.fname.setText("Username : "+product.getUsername());
        holder.frating.setText("Rating Value : "+product.getRatingvalue());
        holder.fdes.setText("Feedback : "+product.getFeedback());

    }




    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fname,fdes,frating;
        ImageView imageView;
        Button remove;

        public MyViewHolder(View itemView) {
            super(itemView);
            fname=itemView.findViewById(R.id.fname);
            fdes = itemView.findViewById(R.id.fdes);
            frating = itemView.findViewById(R.id.frating);
        }
    }
}
