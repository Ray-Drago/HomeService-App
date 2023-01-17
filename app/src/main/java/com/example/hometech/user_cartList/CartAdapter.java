package com.example.hometech.user_cartList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.hometech.Config;
import com.example.hometech.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<CartDataModel> dataModelArrayList;
    private Context c;

    public CartAdapter(Context ctx, ArrayList<CartDataModel> dataModelArrayList){
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_cart_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tvName.setText(dataModelArrayList.get(position).getName());
        holder.tvPrice.setText("Price: "+dataModelArrayList.get(position).getPrice());

        Picasso.get().load(dataModelArrayList.get(position).getImage()).into(holder.iv);

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "Removing item...", Toast.LENGTH_SHORT).show();
                removeItem(dataModelArrayList.get(position).getId());
            }
        });

    }



    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvPrice, tvQuantity;
        ImageView iv;
        CardView rootLayout;
        Button btnRemove;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            iv = itemView.findViewById(R.id.ivImage);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }

    }


    private void removeItem(final String id) {
        final String[] status = {""};
        final String url = Config.baseURL+ "new/delete_cart.php";

        StringRequest s = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Toast.makeText(c, response, Toast.LENGTH_SHORT).show();
                            JSONObject c = new JSONObject(response);
                            status[0] = c.getString("StatusID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (status[0].equals("1")) {
                            Toast.makeText(c, "Removed Successfully", Toast.LENGTH_SHORT).show();
                            c.startActivity(new Intent(c,ListCartActivity.class));
                            ((Activity)c).finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("id",id);
                return m;
            }
        };

        RequestQueue q = Volley.newRequestQueue(c);
        q.add(s);

    }

}
