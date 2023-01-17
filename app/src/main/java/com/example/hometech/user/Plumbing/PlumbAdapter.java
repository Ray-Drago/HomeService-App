package com.example.hometech.user.Plumbing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.R;
import com.example.hometech.user.electrician.ViewDetails;

import java.util.ArrayList;

public class PlumbAdapter extends RecyclerView.Adapter<PlumbAdapter.MyViewHolder>implements Filterable {
    private LayoutInflater inflater;
    private ArrayList<PlumbData> dataModelArrayList;
    private ArrayList<PlumbData> dataModelArrayListFiltered;
    Context c;

    public PlumbAdapter(Context ctx, ArrayList<PlumbData> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.dataModelArrayListFiltered = dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.subs.setVisibility(View.VISIBLE);
        final PlumbData product = dataModelArrayListFiltered.get(position);

        holder.tvName.setText("Name: " + product.getUsername());
        holder.tvPhone.setText("Phone: " + product.getPhone());
        holder.tvPlace.setText("Place: " + product.getPlace());
        holder.subs.setText("Subcategory:"+product.getSubcategory());
        holder.cats.setText("Category:"+product.getCategory());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlumbData p = dataModelArrayList.get(position);
                String name = p.getUsername();
                String phone = p.getPhone();
                String place = p.getPlace();
                String category = p.getCategory();
                String subcategory = p.getSubcategory();
                String wage = p.getWage();
                String days = p.getDays();


                Intent i = new Intent(c, ViewDetails.class);
                i.putExtra("name",name);
                i.putExtra("phone",phone);
                i.putExtra("place",place);
                i.putExtra("days",days);
                i.putExtra("wage",wage);
                i.putExtra("category",category);
                i.putExtra("subcategory",subcategory);
                c.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelArrayListFiltered.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataModelArrayListFiltered = dataModelArrayList;
                } else {
                    ArrayList<PlumbData> filteredList = new ArrayList<>();
                    for (PlumbData row : dataModelArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSubcategory().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getSubcategory().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataModelArrayListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataModelArrayListFiltered;
                return filterResults;
            }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results){
//            dataModelArrayListFiltered.clear();
//            dataModelArrayListFiltered.addAll((ArrayList) results.values);

            notifyDataSetChanged();
        }
    };


}


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvDistance, tvPlace,tvWage,tvDays,cats,subs;
        Button view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.Name);
            tvPhone = itemView.findViewById(R.id.Phone);
            // tvDistance = itemView.findViewById(R.id.Distance);
            tvPlace = itemView.findViewById(R.id.Place);
//            tvWage = itemView.findViewById(R.id.wage);
//            tvDays=itemView.findViewById(R.id.day);
            tvPlace = itemView.findViewById(R.id.Place);
            cats = itemView.findViewById(R.id.cat);
            subs = itemView.findViewById(R.id.sub);
            view = itemView.findViewById(R.id.details);



        }
    }
}

