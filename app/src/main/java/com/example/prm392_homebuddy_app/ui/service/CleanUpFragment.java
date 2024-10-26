package com.example.prm392_homebuddy_app.ui.service;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.API.ServiceService;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.ServiceAdapter;
import com.example.prm392_homebuddy_app.model.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CleanUpFragment extends Fragment {

    private CleanUpViewModel mViewModel;
    private ListView listView; // Declare the ListView here
private ArrayList<Service> listService= new ArrayList<>();
    private ServiceService serviceService;
    public static CleanUpFragment newInstance() {
        return new CleanUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clean_up, container, false);

        listView = view.findViewById(R.id.lv);
        serviceService = ServiceRepository.getServiceService();

        ShowData();


        return view;
    }


    private void ShowData(){
        Call<Service[]> call =serviceService.getAllServices();
        call.enqueue(new Callback<Service[]>() {
            @Override
            public void onResponse(Call<Service[]> call, Response<Service[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Service[] services =response.body();
                    for (Service service: services){
                        listService.add(service);
                    }
                    ServiceAdapter adapter = new ServiceAdapter(getContext(), listService);
                    listView.setAdapter(adapter);
                } else {
                    Log.e("TraineesActivity", "Error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Service[]> call, Throwable t) {
                Log.e("Clean Up Fragement", "Network error: " + t.getMessage());

            }
        });
    }

}
