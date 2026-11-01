package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

public class Fragment3 extends Fragment {

    public interface OnAgreementChangedListener {
        void onAgreementChanged(boolean isChecked);
    }

    private static final String ARG_NAME = "name";

    private Fragment3Binding binding;
    private OnAgreementChangedListener listener;
    private String name;

    public Fragment3() {
    }

    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAgreementChangedListener) {
            listener = (OnAgreementChangedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnAgreementChangedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = Fragment3Binding.inflate(inflater, container, false);

        binding.tvWelcome.setText("Welcome, " + name);

        binding.cbAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onAgreementChanged(isChecked);
            }
        });

        return binding.getRoot();
    }

    // The Activity calls this method after receiving the checkbox event
    public void updateButton(boolean isChecked) {
        if (binding == null) return;
        binding.btnAction.setEnabled(isChecked);
        binding.btnAction.setText(isChecked ? "Finish" : "Continue");
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