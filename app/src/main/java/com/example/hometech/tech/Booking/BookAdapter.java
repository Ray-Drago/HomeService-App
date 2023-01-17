package com.example.hometech.tech.Booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder>implements Filterable {
    private LayoutInflater inflater;
    private ArrayList<BookData> book;
    private ArrayList<BookData> bookDataArrayList;
    Context c;

    public BookAdapter(Context ctx, ArrayList<BookData> book) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.book = book;
        this.bookDataArrayList = book;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_book, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.currentdates.setVisibility(View.VISIBLE);
        final BookData product = bookDataArrayList.get(position);
        holder.usernames.setText(product.getUsername());
        holder.contacts.setText(product.getContact());
        holder.currentdates.setText(product.getDate());
        holder.currentimes.setText(product.getTime());
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookData p = bookDataArrayList.get(position);
                String name = p.getUsername();
                String phone = p.getContact();
                String date = p.getDate();
                String time = p.getTime();
               String techname=p.getTechname();


                Intent i = new Intent(c, Book_details.class);
                i.putExtra("techname",techname);
                i.putExtra("name",name);
                i.putExtra("phone",phone);
                i.putExtra("date",date);
                i.putExtra("time",time);
                c.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookDataArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    bookDataArrayList = book;
                } else {
                    ArrayList<BookData> filteredList = new ArrayList<>();
                    for (BookData row : bookDataArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getDate().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getDate().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    bookDataArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = bookDataArrayList;
                return filterResults;
            }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results){
                bookDataArrayList.clear();
           bookDataArrayList.addAll((ArrayList) results.values);

            notifyDataSetChanged();
        }
    };


}


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView usernames, contacts, currentdates, currentimes;
        Button details;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usernames= itemView.findViewById(R.id.username);
            contacts= itemView.findViewById(R.id.contact);
            currentdates= itemView.findViewById(R.id.bookingDate);
            currentimes = itemView.findViewById(R.id.bookingTime);
            details=itemView.findViewById(R.id.Details);
        }
    }
}

