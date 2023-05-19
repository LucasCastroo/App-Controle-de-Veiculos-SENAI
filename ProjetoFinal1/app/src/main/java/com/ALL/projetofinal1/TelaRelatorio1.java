package com.ALL.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TelaRelatorio1 extends AppCompatActivity {
    TextView tv_resultCarroH, tv_resultDiaH, tv_resultEntidadeH, tv_resultUnidadeH, tv_resultCentroH, tv_resultPassageiroH, tv_resultLocalidadeH, tv_resultServicoH, tv_resultPrevistaH, tv_resultEmissaoH, tv_resultObservacoesH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_relatorio);
    }
}