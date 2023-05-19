package com.ALL.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaConcluido extends AppCompatActivity {

    // Declaração de componentes da tela que serão utilizados no código.
    private Button bt_voltarInicio;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_concluido);
        getSupportActionBar().hide();

        // Referenciação dos componentes com as declarações das variáveis que irão receber os repectivos IDs.
        bt_voltarInicio = findViewById(R.id.bt_voltarMenu);

        // Ação para chamar outra tela quando o botão for pressionado.

        bt_voltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamarMenu();
            }
        });
    }
    // Criação da função de chamar tela Menu.

    public void chamarMenu(){
        Intent intent = new Intent(this, TelaMenu.class);
        startActivity(intent);
    }
}
