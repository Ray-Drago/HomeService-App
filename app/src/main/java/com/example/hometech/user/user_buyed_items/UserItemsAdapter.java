package com.example.hometech.user.user_buyed_items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserItemsAdapter extends RecyclerView.Adapter<UserItemsAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<UserItemsDataModel> dataModelArrayList;
    private Context c;

    public UserItemsAdapter(Context ctx, ArrayList<UserItemsDataModel> dataModelArrayList){
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final UserItemsDataModel product = dataModelArrayList.get(position);
        holder.tvProduct.setText(dataModelArrayList.get(position).getName());
        holder.tvQuantity.setText("Quantity: "+dataModelArrayList.get(position).getQuantity());
        holder.tvPrice.setText("Paid amount: Rs"+dataModelArrayList.get(position).getPrice());
        Picasso.get().load(product.getImage()).into(holder.imageView);

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserItemsDataModel p = dataModelArrayList.get(position);
                String name = p.getUsername();
                String prname = p.getName();
                String prqua = p.getQuantity();
                String price = p.getPrice();
                String image = p.getImage();
                String status = p.getStatus();
                String bdate = p.getBdate();
                String btime = p.getBtime();
                String loc = p.getLocation();
                String ds = p.getDatestatus();
//                Toast.makeText(c, btime+loc+ds, Toast.LENGTH_SHORT).show();



                Intent i = new Intent(c,Orders.class);
                i.putExtra("name",name);
                i.putExtra("prname",prname);
                i.putExtra("prqua",prqua);
                i.putExtra("price",price);
                i.putExtra("image",image);
                i.putExtra("status",status);
                i.putExtra("bdate",bdate);
                i.putExtra("btime",btime);
                i.putExtra("location",loc);
                i.putExtra("datestatus",ds);
//                Toast.makeText(c,loc + ds, Toast.LENGTH_SHORT).show();

                c.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
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
