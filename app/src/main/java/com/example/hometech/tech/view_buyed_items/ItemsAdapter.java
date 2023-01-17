package com.example.hometech.tech.view_buyed_items;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.R;
import com.example.hometech.user.userShopping.ProductListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> implements Filterable {

    private LayoutInflater inflater;
    private ArrayList<ItemsDataModel> dataModelArrayList;
    private Context c;

    public ItemsAdapter(Context ctx, ArrayList<ItemsDataModel> dataModelArrayList){
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_buy, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ItemsDataModel product = dataModelArrayList.get(position);
        holder.tvProduct.setText(dataModelArrayList.get(position).getName());
        holder.tvQuantity.setText("Quantity: "+dataModelArrayList.get(position).getQuantity());
        holder.tvPrice.setText("Paid amount: Rs"+dataModelArrayList.get(position).getPrice());
        Picasso.get().load(product.getImage()).into(holder.imageView);

//        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ItemsDataModel p = dataModelArrayList.get(position);
//                String id = p.getId();
//                String name = p.getName();
//                String image = p.getImage();
//                String quantity = p.getQuantity();
//                String price = p.getPrice();
//                String username = p.getUsername();
//
//            }
//        });
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

        TextView tvProduct, tvQuantity, tvPrice;
        ImageView imageView;
        CardView rootLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ivImage);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvProduct = itemView.findViewById(R.id.tvName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }

}
