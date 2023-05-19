package com.ALL.projetofinal1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

//Opções de escolher carro desejado para prosseguir o agendamento

public class TelaAgendamento extends AppCompatActivity {
    //Tipo de botão utilizado para escolher usando radio(bolinha)
    RadioButton carro1, carro2, carro3;
    Button prosseguir;
    TextView tv_carro_selecionado;
    String carroUID;

    //Criação e referenciamento dos botões

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_agendamento);
        getSupportActionBar().hide();

        carro1 = findViewById(R.id.tv_carro1);
        carro2 = findViewById(R.id.tv_carro2);
        carro3 = findViewById(R.id.tv_carro3);
        prosseguir = findViewById(R.id.bt_prosseguir2);
        tv_carro_selecionado = findViewById(R.id.tv_carro_selecionado);

        //Método para o botão prosseguir só chamar proxima tela caso for escolhido algum carro

        prosseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((carro1.isChecked()) || (carro2.isChecked()) || (carro3.isChecked())) {
                    salvarCarro();
                    chamarCalendario(v);
                } else {
                    RadioButtonSelected();
                }
            }
        });
    }

    //Método de seleção de carro

    private void RadioButtonSelected() {
        if (carro1.isChecked() || carro2.isChecked() || carro3.isChecked()) {
            Toast.makeText(this, "Carro Selecionado!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Selecione um veículo para prosseguir", Toast.LENGTH_SHORT).show();
        }
    }

    //public void para chamar tela de calendario

    public void chamarCalendario(View v){
        Intent tela_calendario = new Intent(this, TelaCalendario.class);
        startActivity(tela_calendario);
    }

    // Função para armazer ou salvar carro selecionado.

    private void salvarCarro() {
        FirebaseFirestore carros_salvos = FirebaseFirestore.getInstance();

        String carro1 = "Carro 1";
        String carro2 = "Carro 2";
        String carro3 = "Carro 2";

        Map<String, Object> salvar_carros = new HashMap<>();
        salvar_carros.put("carro_selecionado", carro1);
        salvar_carros.put("carro_selecionado", carro2);
        salvar_carros.put("carro_selecionado", carro3);

        carroUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = carros_salvos.collection("Carros agendados").document();
        documentReference.set(salvar_carros).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.d("agendamento_carros", "Sucesso ao salvar o carro");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("agendamento_error", "Erro ao salvar o carro");
                    }
                });
    }

    //public void para chamar tela de menu

    public void chamaMenu(View v){
        Intent tela_menu = new Intent(this, TelaMenu.class);
        startActivity(tela_menu);
    }
}