package com.example.hometech.Brand.Brandlist;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.Brand.Brandsession;
import com.example.hometech.Config;
import com.example.hometech.R;
import com.example.hometech.user.userShopping.ViewProductActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_Adapter1 extends RecyclerView.Adapter<Product_Adapter1.ProductViewHolder> implements Filterable {
    Context mCtx;
    private List<ProductListModel> dataModelArrayList;
    private ArrayList<ProductListModel> dataModelArrayListFiltered;
    private LayoutInflater inflater;

    public Product_Adapter1(Context mCtx, List<ProductListModel> dataModelArrayList) {
        this.mCtx = mCtx;
        this.dataModelArrayList = dataModelArrayList;
        inflater=LayoutInflater.from(mCtx);
        this.dataModelArrayListFiltered = (ArrayList<ProductListModel>) dataModelArrayList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item1, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ProductListModel product = dataModelArrayListFiltered.get(position);
        holder.tvName.setText("" + product.getName());
        holder.tvPrice.setText("Rs." + product.getPrice());
        holder.quantity.setText("Quantity:"+product.getQuantity()) ;
        Picasso.get().load(Config.imgURL+product.getImage()).into(holder.image);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProductListModel p =dataModelArrayList.get(position);
//                String name = p.getName();
//                String price = p.getPrice();
//                String quantity = p.getQuantity();
//                String image = p.getImage();
//
//                Intent i = new Intent(mCtx, ViewProductActivity.class);
//                i.putExtra("product_name",name);
//                i.putExtra("product_image",image);
//                i.putExtra("product_price",price);
//                i.putExtra("product_quantity",quantity);
//                mCtx.startActivity(i);
//            }
//        });
holder.btnRemove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//delete(product.getId());
    }
});

    }

//    private void delete(String id) {
//        final String[] status = {""};
//        final String url = Config.baseURL +"deleteplans.php";
//        HashMap<String,String> user=new Brandsession(mCtx).getUserDetails();
//        id=user.get("id");
//        StringRequest s = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Toast.makeText(mCtx, response, Toast.LENGTH_SHORT).show();
//                            JSONObject c = new JSONObject(response);
//                            status[0] = c.getString("StatusID");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        if (status[0].equals("1")) {
//                            Toast.makeText(mCtx, "Removed Successfully", Toast.LENGTH_SHORT).show();
////                            c.startActivity(new Intent(c, AgriProductListActivity.class));
////                            ((Activity)c).finish();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(mCtx, error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> m = new HashMap<>();
//                m.put("sellerid",id);
//                return m;
//            }
//        };
//        RequestQueue q = Volley.newRequestQueue(mCtx);
//        q.add(s);
//
//
//    }



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
                    dataModelArrayListFiltered = (ArrayList<ProductListModel>) dataModelArrayList;
                } else {
                    ArrayList<ProductListModel> filteredList = new ArrayList<>();
                    for (ProductListModel row : dataModelArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getPrice().toLowerCase().contains(charString.toLowerCase())) {
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPrice,quantity;
        ImageView image;
        CardView cardView;
        Button btnRemove;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.prname);
            tvPrice = itemView.findViewById(R.id.pprice);
            image = itemView.findViewById(R.id.img);
            quantity = itemView.findViewById(R.id.qua);
            cardView = itemView.findViewById(R.id.card);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}


