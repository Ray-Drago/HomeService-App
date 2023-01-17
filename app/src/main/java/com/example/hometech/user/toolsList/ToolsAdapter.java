package com.example.hometech.user.toolsList;

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
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.Config;
import com.example.hometech.ConfirmBuyActivity;
import com.example.hometech.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ProductViewHolder> implements Filterable {

    private Context mCtx;
    private List<ToolsDataModel> productList;
    private List<ToolsDataModel>dataModelArrayListFiltered;
    public ToolsAdapter(Context mCtx, List<ToolsDataModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.dataModelArrayListFiltered = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.tool_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final ToolsDataModel product = dataModelArrayListFiltered.get(position);

        holder.tvName.setText("" + product.getName());
        holder.tvPhone.setText("Phone: " + product.getPhone());
        holder.tvPrice.setText("Rs." + product.getPrice());
        holder.tvQ.setText("No: of items available." + product.getQuantity());
        Picasso.get().load(product.getImage()).into(holder.iv);

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + product.getPhone()));
                mCtx.startActivity(intent);
            }
        });
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ConfirmBuyActivity.class);
                intent.putExtra("product_price",product.getPrice());
                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_image", product.getImage());
                intent.putExtra("product_quantity", product.getQuantity());
                mCtx.startActivity(intent);
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
                    dataModelArrayListFiltered = productList;
                } else {
                    ArrayList<ToolsDataModel> filteredList = new ArrayList<>();
                    for (ToolsDataModel row : productList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getName().toLowerCase().contains(charString.toLowerCase())) {
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


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone, tvPrice,tvQ;
        ImageView iv;
        Button btnCall,pay;

        public ProductViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            iv = itemView.findViewById(R.id.image);
            btnCall = itemView.findViewById(R.id.btnCall);
            pay = itemView.findViewById(R.id.btnpay);
            tvQ = itemView.findViewById(R.id.tvQ);
        }
    }
}
