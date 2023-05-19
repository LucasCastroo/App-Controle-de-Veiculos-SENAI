package com.ALL.projetofinal1.ui.senai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ALL.projetofinal1.R;
import com.ALL.projetofinal1.databinding.FragmentSenaiBinding;

public class SenaiFragment extends Fragment {

    private FragmentSenaiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_senai,container,false);

        return root;
    }
}