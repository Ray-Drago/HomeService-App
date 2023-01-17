package com.example.hometech.tech.techShopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TechProduct_Adapter extends RecyclerView.Adapter<TechProduct_Adapter.ProductViewHolder> implements Filterable {
    Context mCtx;
    private List<techProductListModel> dataModelArrayList;
    private List<techProductListModel> dataModelArrayListFiltered;
    private LayoutInflater inflater;

    public TechProduct_Adapter(Context mCtx, List<techProductListModel> dataModelArrayList) {
        this.mCtx = mCtx;
        this.dataModelArrayList = dataModelArrayList;
        inflater = LayoutInflater.from(mCtx);
        this.dataModelArrayListFiltered = dataModelArrayList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final techProductListModel product = dataModelArrayListFiltered.get(position);
        holder.tvName.setText("" + product.getName());
        holder.tvPrice.setText("Rs." + product.getPrice());
        holder.quantity.setText("Quantity:" + product.getQuantity());
        Picasso.get().load(product.getImage()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techProductListModel p = dataModelArrayList.get(position);
                String name = p.getName();
                String price = p.getPrice();
                String quantity = p.getQuantity();
                String image = p.getImage();

                Intent i = new Intent(mCtx, techViewProductActivity.class);
                i.putExtra("product_name", name);
                i.putExtra("product_image", image);
                i.putExtra("product_price", price);
                i.putExtra("product_quantity", quantity);
                mCtx.startActivity(i);
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
                    List<techProductListModel> filteredList = new ArrayList<>();
                    for (techProductListModel row : dataModelArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPrice().contains(charSequence)) {
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
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPrice,quantity;
        ImageView image;
        CardView cardView;
        Button btnOrder,btncart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.prname);
            tvPrice = itemView.findViewById(R.id.pprice);
            image = itemView.findViewById(R.id.img);
           quantity = itemView.findViewById(R.id.qua);
           cardView = itemView.findViewById(R.id.card);
            //btncart = itemView.findViewById(R.id.cart);
        }
    }
}
