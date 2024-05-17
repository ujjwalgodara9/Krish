package com.project.agroworldapp.ui.activity;


import static com.project.agroworldapp.utils.Constants.setAppLocale;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.project.agroworldapp.BuildConfig;
import com.project.agroworldapp.R;
//import com.project.agroworldapp.databinding.ActivityManufactureBinding;
import com.project.agroworldapp.db.PreferenceHelper;

import com.project.agroworldapp.utils.Constants;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    PreferenceHelper preferenceHelper;
    private FirebaseAuth auth;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityManufactureBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_manufacture);
        preferenceHelper = PreferenceHelper.getInstance(this);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Intent intent = getIntent();

        if (intent != null) {
            userType = intent.getStringExtra("manufacturerUser");
            if (Objects.equals(userType, "manufacturer")) {
//                binding.tvUserType.setText(getString(R.string.show_product));
//                binding.tvUserType.setText(getString(R.string.manufacture_panel));
            } else {
//                binding.tvUserType.setText(getString(R.string.show_vehicles));
//                binding.tvUserType.setText(getString(R.string.transport_panel));
            }
        }

        if (user != null) {
//            Constants.bindImage(binding.ivMfrProfile, String.valueOf(user.getPhotoUrl()), binding.ivMfrProfile);
//            binding.tvMfrName.setText(user.getDisplayName());
//            binding.tvMfrEmail.setText(user.getEmail());
//            binding.tvMfrWelcomeMsg.setText("Welcome '" + user.getDisplayName() + "' to the AgroWorld");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logoutUser();
                return true;
            case R.id.adminHindiLang:
                setAppLocale(this, "hi");
                preferenceHelper.saveData(Constants.ENGLISH_KEY, false);
                preferenceHelper.saveData(Constants.HINDI_KEY, true);
                startActivity(new Intent(this, SplashScreen.class));
                finish();
                return true;
            case R.id.adminEnglishLang:
                setAppLocale(this, "en");
                preferenceHelper.saveData(Constants.ENGLISH_KEY, true);
                preferenceHelper.saveData(Constants.HINDI_KEY, false);
                startActivity(new Intent(this, SplashScreen.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logoutUser() {
        Constants.logoutAlertMessage(UserProfileActivity.this, auth);
    }
}