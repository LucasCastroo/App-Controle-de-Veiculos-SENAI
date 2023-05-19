package com.ALL.projetofinal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.type.Date;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TelaRUV extends AppCompatActivity {

    // Criação de variáveis que serão utilizadas na classe.

    private String [] mensagens = {"Sucesso ao salvar os dados da RUV","Preencha todos os campos"};
    EditText ed_entidade,ed_observacao, ed_unidade, ed_centro, ed_data_emissao, ed_data_prevista, ed_horario, ed_nome_passageiro, ed_localidade, ed_servico_executar;
    Button bt_avancar;
    String banco_ruvUID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_ruv);

        getSupportActionBar().hide();

        // Referenciação dos componentes com as declarações das variáveis que irão receber os repectivos IDs.

        ed_observacao = findViewById(R.id.ed_observacao);
        ed_data_prevista = findViewById(R.id.ed_data_prev);
        ed_horario = findViewById(R.id.ed_horario);
        ed_data_emissao = findViewById(R.id.ed_data_emissao);
        ed_entidade = findViewById(R.id.ed_entidade);
        ed_unidade = findViewById(R.id.ed_unidade);
        ed_centro = findViewById(R.id.ed_centro);
        ed_nome_passageiro= findViewById(R.id.ed_nome_passageiro);
        ed_localidade = findViewById(R.id.ed_localidade);
        ed_servico_executar = findViewById(R.id.ed_servico);
        bt_avancar = findViewById(R.id.bt_avancar);

        // Setando ação do botão para avançar

        bt_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data_prev = ed_data_prevista.getText().toString();
                String horario = ed_horario.getText().toString();
                String data_emissao = ed_data_emissao.getText().toString();
                String entidade = ed_entidade.getText().toString();
                String unidade = ed_unidade.getText().toString();
                String centro = ed_centro.getText().toString();
                String passageiro = ed_nome_passageiro.getText().toString();
                String localidade = ed_localidade.getText().toString();
                String servico = ed_servico_executar.getText().toString();

                if(data_prev.isEmpty() || horario.isEmpty() ||  data_emissao.isEmpty() || entidade.isEmpty() ||  unidade.isEmpty() || centro.isEmpty() || passageiro.isEmpty() || localidade.isEmpty() || servico.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{

                    // Chamando funções criadas.

                    agendarRUV();
                    chamarConcluido();
                }
            }
        });
    }

    // Função para ser aramzenada no banco os dados inseridos, em seus devidos campos.

    private void agendarRUV(){

        FirebaseFirestore banco_ruv = FirebaseFirestore.getInstance();

        String centro = ed_centro.getText().toString();
        String unidade = ed_unidade.getText().toString();
        String entidade = ed_entidade.getText().toString();
        String data_emissao = ed_data_emissao.getText().toString();
        String data_prev = ed_data_prevista.getText().toString();
        String passageiro = ed_nome_passageiro.getText().toString();
        String horario = ed_horario.getText().toString();
        String servico = ed_servico_executar.getText().toString();
        String localidade = ed_localidade.getText().toString();
        String observacoes = ed_observacao.getText().toString();

        Map<String,Object> ruvs = new HashMap<>();
        ruvs.put("centro",centro);
        ruvs.put("unidade",unidade);
        ruvs.put("entidade",entidade);
        ruvs.put("data_emissao",data_emissao);
        ruvs.put("data_prevista",data_prev);
        ruvs.put("servico",servico);
        ruvs.put("horario",horario);
        ruvs.put("passageiro",passageiro);
        ruvs.put("localidade",localidade);
        ruvs.put("observacoes",observacoes);

        banco_ruvUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = banco_ruv.collection("Ruvs").document(banco_ruvUID);
        documentReference.set(ruvs).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("banco_ruv","Sucesso ao salvar os dados da RUV!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("banco_ruv_erro","Erro ao salvar dados da RUV");
                    }
                });
    }

    // Função para chamar Tela Concluido.

    public void chamarConcluido(){
        Intent intent = new Intent(this, TelaConcluido.class);
        startActivity(intent);
    }
}