package com.ALL.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class TelaHistorico extends AppCompatActivity {

    //Declarações de componentes da tela do Histórico.

    TextView tv_resultEntidadeH, tv_resultUnidadeH, tv_resultCentroH, tv_resultPassageiroH, tv_resultLocalidadeH, tv_resultServicoH, tv_resultHorarioH, tv_resultPrevistaH, tv_resultEmissaoH, tv_resultObservacoesH;
    String ruvUID;
    FirebaseFirestore banco_historico = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_historico);
        getSupportActionBar().hide();

        // Referenciação dos componentes com as declarações das variáveis que irão receber os repectivos IDs.

        tv_resultEntidadeH = findViewById(R.id.tv_resultEntidadeH);
        tv_resultUnidadeH = findViewById(R.id.tv_resultUnidadeH);
        tv_resultCentroH = findViewById(R.id.tv_resultCentroH);
        tv_resultPassageiroH = findViewById(R.id.tv_resultPassageiroH);
        tv_resultLocalidadeH = findViewById(R.id.tv_resultLocalidadeH);
        tv_resultServicoH = findViewById(R.id.tv_resultServicoH);
        tv_resultHorarioH = findViewById(R.id.tv_resultHorarioH);
        tv_resultPrevistaH = findViewById(R.id.tv_resultPrevistaH);
        tv_resultEmissaoH = findViewById(R.id.tv_resultEmissaoH);
        tv_resultObservacoesH = findViewById(R.id.tv_resultObservacoesH);
    }
    @Override
    protected void onStart(){
        super.onStart();

        // Método de puxar dados salvos inseridos na RUV para cada componente referenciado.

        ruvUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = banco_historico.collection("Ruvs").document(ruvUID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    tv_resultEntidadeH.setText(documentSnapshot.getString("entidade"));
                    tv_resultUnidadeH.setText(documentSnapshot.getString("unidade"));
                    tv_resultCentroH.setText(documentSnapshot.getString("centro"));
                    tv_resultPassageiroH.setText(documentSnapshot.getString("passageiro"));
                    tv_resultLocalidadeH.setText(documentSnapshot.getString("localidade"));
                    tv_resultServicoH.setText(documentSnapshot.getString("servico"));
                    tv_resultHorarioH.setText(documentSnapshot.getString("horario"));
                    tv_resultPrevistaH.setText(documentSnapshot.getString("data_prevista"));
                    tv_resultEmissaoH.setText(documentSnapshot.getString("data_emissao"));
                    tv_resultObservacoesH.setText(documentSnapshot.getString("observacoes"));
                }
            }
        });
    }

    // Método de chamar tela Menu, setado no botão de voltar.

    public void chamarMenu(View v){
        Intent tela_menu = new Intent(this, TelaMenu.class);
        startActivity(tela_menu);
    }
}