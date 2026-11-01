package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

public class Fragment2 extends Fragment {

    public interface OnContinueListener {
        void onContinueClicked(String name);
    }

    private Fragment2Binding binding;
    private OnContinueListener listener;

    public Fragment2() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnContinueListener) {
            listener = (OnContinueListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnContinueListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = Fragment2Binding.inflate(inflater, container, false);

        binding.btnContinueTo3.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                binding.etName.setError("Enter name!");
                return;
            }
            if (TextUtils.isEmpty(email)
                    || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.setError("Enter a valid email");
                return;
            }
            listener.onContinueClicked(name);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}