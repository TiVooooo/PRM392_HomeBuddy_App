package com.example.prm392_homebuddy_app.ui.service;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.ServiceAdapter;
import com.example.prm392_homebuddy_app.model.Service;

import java.util.ArrayList;
import java.util.List;

public class CleanUpFragment extends Fragment {

    private CleanUpViewModel mViewModel;
    private ListView listView; // Declare the ListView here

    public static CleanUpFragment newInstance() {
        return new CleanUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_clean_up, container, false);

        // Find the ListView in the inflated layout
        listView = view.findViewById(R.id.item_list); // Correctly find the ListView

        // Prepare data for the ListView
        List<Service> cleaningSupplies = new ArrayList<>();
        cleaningSupplies.add(new Service("Dishwasher Detergent", 25.00, R.drawable.qua_tao));
        cleaningSupplies.add(new Service("Surface Cleaner", 18.00, R.drawable.qua_tao));
        // Add more items as needed...

        // Set up the adapter for the ListView
        ServiceAdapter adapter = new ServiceAdapter(getContext(), cleaningSupplies);
        listView.setAdapter(adapter);

        return view; // Return the inflated view
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CleanUpViewModel.class);
        // TODO: Use the ViewModel if needed
    }
}
