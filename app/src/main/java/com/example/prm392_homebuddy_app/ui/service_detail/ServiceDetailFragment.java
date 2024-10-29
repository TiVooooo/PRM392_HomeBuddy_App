package com.example.prm392_homebuddy_app.ui.service_detail;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.API.ServiceService;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.model.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ServiceDetailFragment extends Fragment {

    private int serviceId; // Store the passed ID

    private ServiceDetailViewModel mViewModel;
    private TextView txtName, txtDeccription, txtDuration, txtPrice, txtHelper;
    private ServiceService serviceService;

    private ImageView ivServiceDetail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);

        txtName = view.findViewById(R.id.productTitle);
        txtDeccription = view.findViewById(R.id.productDescription);
        txtDuration = view.findViewById(R.id.txtDuration);
        txtHelper = view.findViewById(R.id.txtHelperName);
        txtPrice = view.findViewById(R.id.productPrice);
        ivServiceDetail = view.findViewById(R.id.productImage);
        serviceService = ServiceRepository.getServiceService();

        if (getArguments() != null) {
            serviceId = getArguments().getInt("serviceId");
            Call<Service> call = serviceService.getAllService(serviceId);
            call.enqueue(new Callback<Service>() {
                @Override
                public void onResponse(Call<Service> call, Response<Service> response) {
                    Service service = response.body();
                    if (service == null) {
                        return;
                    }
                    txtDeccription.setText(service.description);
                    txtDuration.setText("Duration: " + service.duration);
                    txtHelper.setText("Helper: " + service.helperName);
                    txtName.setText(service.name);
                    txtPrice.setText(service.price + "");
                    String image = service.getImage();
                    Glide.with(getContext())
                            .load(image)
                            .into(ivServiceDetail);
                }

                @Override
                public void onFailure(Call<Service> call, Throwable t) {
                    // Handle the error
                }
            });
        }

        return view;
    }

    public static ServiceDetailFragment newInstance(int serviceId) {
        ServiceDetailFragment fragment = new ServiceDetailFragment();
        Bundle args = new Bundle();
        args.putInt("serviceId", serviceId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ServiceDetailViewModel.class);
    }
}