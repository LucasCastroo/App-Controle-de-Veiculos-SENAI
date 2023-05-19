package com.ALL.projetofinal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ALL.projetofinal1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class TelaEsqueceuSenha extends AppCompatActivity {

    // Declaração de componentes da tela que serão utilizados no código.

    private EditText email_esqueceu_senha;
    private Button botao_redefinir;

    // Criação do autenticador Firebase.

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_esqueceu_senha);
        getSupportActionBar().hide();

        //Declarações de componentes da tela de esqueceu senha.

        email_esqueceu_senha = (EditText) findViewById(R.id.email_esqueceu_senha);
        botao_redefinir = (Button) findViewById(R.id.botao_redefinir);

        auth = FirebaseAuth.getInstance();

        // Ação do botão ao ser tocado.

        botao_redefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botaoRedefinir();
            }
        });
    }

    // Função sendo criada para o botão redefinir.

    private void botaoRedefinir(){
        String email = email_esqueceu_senha.getText().toString().trim();

        if (email.isEmpty()){
            email_esqueceu_senha.setError("E-mail é necessário!");
            email_esqueceu_senha.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_esqueceu_senha.setError("Por favor, insira um email válido!");
            email_esqueceu_senha.requestFocus();
            return;
        }

        // Ação que irá ocorrer quando o usuário solicitar para redefinir senha.

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(TelaEsqueceuSenha.this, "Verifique seu email para redefinir sua senha!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(TelaEsqueceuSenha.this, "Tente novamente! Algo deu errado!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //public void para chamar tela login

    public void TelaInicial(View view) {
        Intent tela_login = new Intent(this, TelaLogin.class);
        startActivity(tela_login);
    }
}