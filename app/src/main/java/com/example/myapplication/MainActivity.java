package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements Fragment2.OnContinueListener,
        Fragment3.OnAgreementChangedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, Fragment1.newInstance(null, null))
                    .commit();
        }

        // Button of the Activity: Fragment1 -> Fragment2
        binding.buttonContinue.setOnClickListener(view ->
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Fragment2())
                        .addToBackStack(null)
                        .commit());

        // Hide the Activity button after leaving Fragment1
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            binding.buttonContinue.setVisibility(count == 0 ? View.VISIBLE : View.GONE);
        });
    }

    // Interface from Fragment2: receives the name and opens Fragment3
    @Override
    public void onContinueClicked(String name) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment3.newInstance(name))
                .addToBackStack(null)
                .commit();
    }

    // Interface from Fragment3: checkbox state changed
    @Override
    public void onAgreementChanged(boolean isChecked) {
        Fragment current = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (current instanceof Fragment3) {
            ((Fragment3) current).updateButton(isChecked);
        }
    }
}