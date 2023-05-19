package com.ALL.projetofinal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ALL.projetofinal1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastro extends AppCompatActivity {
    private EditText ed_nome, ed_email, ed_senha;
    private Button bt_cadastrar;
    private String[] mensagens = {"Cadastro realizado com sucesso!","Preencha todos os campos!"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        // retira a barra de apresentação do aplicativo
        getSupportActionBar().hide();

        // Inicia todos os objetos existentes no app
        iniciarComponentes();

        // método quando houver clique no bt_cadastrar
        // (irá pegar(get) as informações inseridas nas variáveis 'nome', 'email' e 'senha'
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // pega os valores de texto e aloca em strings
                String nome = ed_nome.getText().toString();
                String email = ed_email.getText().toString();
                String senha = ed_senha.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){

                    Snackbar snackbar = Snackbar.make(view,mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }else{
                    cadastrarUsuario(view);

                }
            }
        });
    }

    // não permite que esse método seja acessado em outra classe
    private void cadastrarUsuario(View v){
        // puxa os botões que permitirão o cadasro do usuário
        String email = ed_email.getText().toString();
        String senha = ed_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // caso as informações estejam todas inseridas e autenticadas corretamente, será feito o cadastro
                if(task.isSuccessful()){

                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                    // limpar os campos após o cadastro
                    ed_nome.setText("");
                    ed_email.setText("");
                    ed_senha.setText("");

                }else{
                    String erro;

                    // o try catch serve como um if else, porém o tratamento será feito caso haja algum erro que precise ser tratado especificamente no programa
                    try {
                        throw task.getException();
                        // tratamento caso a quantidade de caracteres seja insuficiente
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha com no mínimo 6 caracteres!";
                        // erro caso a conta já esteja registrada no banco de dados
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "Essa conta já existe!";
                        // erro caso o email não seja autenticado corretamente
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "Email inválido!";
                        // qualquer erro que não tenha sido inserido no try catch
                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário!";
                    }

                    // o snack bar nesse caso permitirá que as mensagens apareça devido ao 'show'
                    // onde a informação será passada e o fundo(backgroundTint) será branco
                    // e a cor do texto(textColor) será preto
                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void SalvarDadosUsuario(){
        String nome = ed_nome.getText().toString();

        FirebaseFirestore banco_dados =  FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = banco_dados.collection("Usuários").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("banco_dados","Sucesso ao salvar os dados!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("banco_dados_erro","Erro ao salvar os dados" + e.toString());
                    }
                });
    }

    // esse método irá iniciar os componentes que foram chamados inicialmente no onCreate
    // onde quando o usuário inserir as informações serão resgatadas por meio do get e serão postas nos id's alocados às variáveis
    public void iniciarComponentes(){
        ed_nome = findViewById(R.id.nome_cadastro);
        ed_email = findViewById(R.id.email_cadastro);
        ed_senha = findViewById(R.id.senha_cadastro);
        bt_cadastrar = findViewById(R.id.botao_cadastre_se);
    }

    public void TelaInicial(View view) {
        Intent tela_login = new Intent(this, TelaLogin.class);
        startActivity(tela_login);
    }
}