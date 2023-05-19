package com.ALL.projetofinal1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.ALL.projetofinal1.ui.agendamento.AgendamentoFragment;
import com.ALL.projetofinal1.ui.historico.HistoricoFragment;
import com.ALL.projetofinal1.ui.senai.SenaiFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ALL.projetofinal1.databinding.ActivityTelaMenuBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.Time;

public class TelaMenu extends AppCompatActivity {

    // Declaração de componentes da tela que serão utilizados no código.

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView nomeUsuario, emailUsuario;
    String usuarioID;

    // Instanciação do banco de dados.

    FirebaseFirestore banco_dados =  FirebaseFirestore.getInstance();
    private ActivityTelaMenuBinding binding;

    // Método de chamar menu de opções na actionBar.

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = ActivityTelaMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarTelaMenu.toolbar);

        // Referenciamento dos componenetes da tela com seus IDs.

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_aberto,R.string.menu_fechado);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);

                // Método de chamar telas ou fragmentos caso (SWITCH CASE) o usuário clique em alguma das opções da tela menu.

                switch (id){
                    case R.id.nav_historico_agendamento:
                        chamarHistorico();
                        break;
                    case R.id.nav_agendamento:
                        chamarAgendamento();
                        break;
                    case R.id.nav_sair_app:
                        sairUsuario();
                        chamarLogin();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        // Chamando método iniciarComponentes para puxar nome e email do usuário.

        iniciarComponentes();

        // linkando email que é no formato de String com o firebase
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Referencia tabela usuários que possui o atributo NOME
        DocumentReference documentReference = banco_dados.collection("Usuários").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    nomeUsuario.setText(documentSnapshot.getString("nome"));
                    emailUsuario.setText(email);
                }
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_tela_menu,fragment);
        fragmentTransaction.commit();
    }

    // Método para o usuário deslogar da conta pelo botão sair

    public void sairUsuario(){
        FirebaseAuth.getInstance().signOut();

    }

    // Método para chamar Tela Login

    public void chamarLogin(){
        Intent intent = new Intent(TelaMenu.this, TelaLogin.class);
        startActivity(intent);
        finish();
    }

    // Método para chamar Tela Agendamento.

    public void chamarAgendamento(){
        Intent intent = new Intent(TelaMenu.this, TelaAgendamento.class);
        startActivity(intent);
        finish();
    }

    public void chamarHistorico(){
        Intent intent = new Intent(TelaMenu.this, TelaHistorico.class);
        startActivity(intent);
    }

    // Método minimiza app no botão voltar do celular.

    public void onBackPressed(){
        moveTaskToBack(false);
    }

    // Método para linkar os componentes da tela com o nome que foi inserido pelo usuário e o email, assim, mostrando ambos na tela Menu.

    private void iniciarComponentes(){
        View nomeU = navigationView.getHeaderView(0);
        View emailU = navigationView.getHeaderView(0);
        nomeUsuario = nomeU.findViewById(R.id.tv_logado_nome);
        emailUsuario = emailU.findViewById(R.id.tv_logado_email);
    }
}