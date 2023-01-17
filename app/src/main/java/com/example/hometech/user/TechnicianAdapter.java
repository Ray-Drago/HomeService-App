package com.example.hometech.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.R;

import java.util.ArrayList;
import java.util.List;

class TechnicianAdapter extends RecyclerView.Adapter<TechnicianAdapter.ProductViewHolder> implements Filterable  {

    private LayoutInflater inflater;
    private ArrayList<TechnicianDataModel> dataModelArrayList;
    private ArrayList<TechnicianDataModel> dataModelArrayListFiltered;
    Context c;

    public TechnicianAdapter(Context ctx, ArrayList<TechnicianDataModel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.dataModelArrayListFiltered = dataModelArrayList;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.technician_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final TechnicianDataModel product = dataModelArrayListFiltered.get(position);

        holder.tvName.setText("Name: " + product.getUsername());
        holder.tvPhone.setText("Phone: " + product.getPhone());
        holder.tvPlace.setText("Place: " + product.getPlace());
        holder.tvDays.setText("Days:"+product.getDays());
        holder.tvWage.setText("Wage:"+product.getWage());
        holder.tvDistance.setText("Approx. distance: " + product.getDistance() + " km");

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + product.getPhone()));
                c.startActivity(intent);
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
                    ArrayList<TechnicianDataModel> filteredList = new ArrayList<>();
                    for (TechnicianDataModel row : dataModelArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getDays().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getDays().toLowerCase().contains(charString.toLowerCase())) {
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
            protected void publishResults(CharSequence constraint, FilterResults results) {
//            dataModelArrayListFiltered.clear();
//            dataModelArrayListFiltered.addAll((ArrayList) results.values);

                notifyDataSetChanged();
            }
        };
    }

        class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone, tvDistance, tvPlace,tvWage,tvDays;
        Button btnCall;

        public ProductViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            tvWage = itemView.findViewById(R.id.tvwage);
            tvDays=itemView.findViewById(R.id.tvday);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            btnCall = itemView.findViewById(R.id.btnCall);
        }
    }
}

