package com.example.agventgroceryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agventgroceryapp.Models.PopularProductsModel;
import com.example.agventgroceryapp.R;

import java.util.ArrayList;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PopularProductsModel> popularProductsData;

    public PopularProductsAdapter(Context context, ArrayList<PopularProductsModel> popularProductsData) {
        this.context = context;
        this.popularProductsData = popularProductsData;
    }

    @NonNull
    @Override
    public PopularProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductsAdapter.ViewHolder holder, int position) {


        final PopularProductsModel popularProductsModel = popularProductsData.get(position);

        holder.name.setText(popularProductsModel.getName());
        holder.description.setText(popularProductsModel.getDescription());
        Glide.with(context).load(popularProductsData.get(position).getImg_url()).into(holder.popularImg);
        holder.name.setText(popularProductsData.get(position).getName());
        holder.description.setText(popularProductsData.get(position).getDescription());
        holder.rating.setText(popularProductsData.get(position).getRating());
        holder.discount.setText(popularProductsData.get(position).getDiscount());


    }

    @Override
    public int getItemCount() {
        return popularProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView popularImg;
        TextView name, description, rating, discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popularImg = itemView.findViewById(R.id.popularProduct_img);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_description);
            rating = itemView.findViewById(R.id.pop_rating);
            discount = itemView.findViewById(R.id.pop_discount);

        }
    }
}
