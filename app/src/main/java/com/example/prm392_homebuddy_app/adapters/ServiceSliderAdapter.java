package com.example.prm392_homebuddy_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.model.ServiceRelatives;

import java.util.List;

public class ServiceSliderAdapter extends RecyclerView.Adapter<ServiceSliderAdapter.ProductViewHolder> {
    private List<ServiceRelatives> serviceList;
    private Context context;

    public ServiceSliderAdapter(List<ServiceRelatives> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceSliderAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggest_service, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ServiceRelatives product = serviceList.get(position);

        Glide.with(context).load(product.getImage()).into(holder.serviceImage);
        holder.serviceName.setText(product.getName());
        holder.servicePrice.setText(product.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView serviceImage;
        TextView serviceName, servicePrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceImage = itemView.findViewById(R.id.imageView);
            serviceName = itemView.findViewById(R.id.productName);
            servicePrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
