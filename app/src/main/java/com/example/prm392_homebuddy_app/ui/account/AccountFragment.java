package com.example.prm392_homebuddy_app.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.API.UserRepository;
import com.example.prm392_homebuddy_app.EditUserActivity;
import com.example.prm392_homebuddy_app.LoginActivity;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.utils.PreferenceUtils;

import java.util.Map;

public class AccountFragment extends Fragment {

    private AccountViewModel mViewModel;
    private TextView textViewName, textViewEmail, textViewPhone, textViewAddress, textViewRole, textViewCreatedAt;
    private ImageView imageViewAvatar;
    private UserRepository userRepository;
    private Button btnEditProfile, btnSignOut;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);

        // Initialize views
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        textViewName = view.findViewById(R.id.textViewName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewPhone = view.findViewById(R.id.textViewPhone);
        textViewAddress = view.findViewById(R.id.textViewAddress);
        textViewRole = view.findViewById(R.id.textViewRole);
        textViewCreatedAt = view.findViewById(R.id.textViewCreatedAt);
        btnEditProfile = view.findViewById(R.id.buttonEditProfile);
        btnSignOut = view.findViewById(R.id.buttonSignOut);

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), EditUserActivity.class);
            startActivity(intent);
        });

        btnSignOut.setOnClickListener(v -> signOut());

        userRepository = new UserRepository();
        String userId = PreferenceUtils.getUserId(requireActivity());
        fetchUserProfile(Integer.parseInt(userId));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
    }

    private void fetchUserProfile(int userId) {
        userRepository.getUserById(userId, new UserRepository.UserDataCallback() {
            @Override
            public void onSuccess(Map<String, Object> userData) {
                // Display data on the UI
                displayUserData(userData);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(requireActivity(), "Failed to load profile: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayUserData(Map<String, Object> userData) {
        textViewName.setText((String) userData.get("name"));
        textViewEmail.setText((String) userData.get("email"));
        textViewPhone.setText((String) userData.get("phone"));
        textViewAddress.setText((String) userData.get("address"));
        textViewRole.setText((String) userData.get("role"));
        textViewCreatedAt.setText((String) userData.get("createdAt"));

        String avatarUrl = (String) userData.get("avatar");
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            Glide.with(this)
                    .load(avatarUrl)
                    .placeholder(R.drawable.ic_account)
                    .error(R.drawable.ic_account)
                    .into(imageViewAvatar);
        } else {
            imageViewAvatar.setImageResource(R.drawable.ic_account);
        }
    }

    private void signOut() {
        PreferenceUtils.setLoggedIn(requireActivity(), false);
        PreferenceUtils.saveToken(requireActivity(), null, 0, null, null);

        Intent loginIntent = new Intent(requireActivity(), LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        requireActivity().finish();
    }
}
