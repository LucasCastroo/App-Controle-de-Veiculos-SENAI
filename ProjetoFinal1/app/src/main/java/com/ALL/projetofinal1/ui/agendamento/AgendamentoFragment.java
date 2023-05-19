package com.ALL.projetofinal1.ui.agendamento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ALL.projetofinal1.R;
import com.ALL.projetofinal1.databinding.FragmentAgendamentoBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class AgendamentoFragment extends Fragment {
    FirebaseFirestore colecao_carros = FirebaseFirestore.getInstance();

    private FragmentAgendamentoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_agendamento,container,false);

        return root;
    }
}