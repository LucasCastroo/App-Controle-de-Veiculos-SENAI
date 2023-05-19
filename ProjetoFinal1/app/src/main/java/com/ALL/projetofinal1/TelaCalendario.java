package com.ALL.projetofinal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaCalendario extends AppCompatActivity {

    //Declarações de componentes da tela do calendario.

    CalendarView calendario;
    TextView resultado_data;
    Button bt_prosseguir;
    String calendarioUID;
    String [] mensagens = {"Selecione uma data para prosseguir","Data selecionada!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_calendario);

        getSupportActionBar().hide();

        // Referenciando os IDs

        calendario = findViewById(R.id.calendario);
        resultado_data = findViewById(R.id.resultado_data);
        bt_prosseguir = findViewById(R.id.bt_prosseguir);

        // Método de puxar a data selecionada

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String data = i2 + "/" + (i1 + 1) + "/" + i;
                resultado_data.setText(data);
            }
        });

        //Setandoo ação do botão

        bt_prosseguir.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String result_data = resultado_data.getText().toString();
                if (result_data.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    salvarData();
                    chamarRUV();
                }
            }
        });
    }

    // Função de criação do banco de dados para a data do calendário ser armazenada.

    private void salvarData(){
        FirebaseFirestore calendario = FirebaseFirestore.getInstance();

        String result_data = resultado_data.getText().toString();

        Map<String,Object> datas_salvas = new HashMap<>();
        datas_salvas.put("data_requisicao",result_data);

        calendarioUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = calendario.collection("Datas").document();
        documentReference.set(datas_salvas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Log.d("calendario","Sucesso ao salvar data");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("calendario_erro","Data não salva");
                    }
                });
    }

    // Função para chmamr Tela RUV.

    public void chamarRUV(){
        Intent tela_RUV = new Intent(this, TelaRUV.class);
        startActivity(tela_RUV);
    }
}