package com.tuhoc.chatapp.ui.phonebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuhoc.chatapp.R;
import com.tuhoc.chatapp.databinding.FragmentCallBinding;
import com.tuhoc.chatapp.databinding.FragmentPhoneBookBinding;

public class PhoneBookFragment extends Fragment {
    private FragmentPhoneBookBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhoneBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}