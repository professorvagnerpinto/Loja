package br.edu.ifsul.loja.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.model.User;
import br.edu.ifsul.loja.setup.AppSetup;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "mainActivity";
    private EditText etEmail, etSenha;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //mapeia os componentes da UI
        etEmail = findViewById(R.id.etEmail_login);
        etSenha = findViewById(R.id.etSenha_login);

        //trata evento onclick do button
        findViewById(R.id.btLogar_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                if(!email.isEmpty() && !senha.isEmpty()){
                    signIn(email, senha);
                }else{
                    if(email.isEmpty()){
                        etEmail.setError(getString(R.string.msg_invalido));
                    }
                    if(senha.isEmpty()){
                        etSenha.setError(getString(R.string.msg_invalido));
                    }
                    Snackbar.make(findViewById(R.id.R_id_container_activity_login), getString(R.string.toast_preencher_todos_campos), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        //trata evento onclick do textview
        findViewById(R.id.tv_esqueceusenha_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                if(!email.isEmpty()){
                    resetarSenha(email);
                }else{
                    etEmail.setError(getString(R.string.msg_invalido));
                    Snackbar.make(findViewById(R.id.R_id_container_activity_login), getString(R.string.toast_preencher_todos_campos), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void signIn(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            setUserSessao();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Snackbar.make(findViewById(R.id.R_id_container_activity_login), getString(R.string.toast_falha_autenticacao), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void setUserSessao() {
        FirebaseDatabase.getInstance().getReference()
                .child("vendas").child("users").child("1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AppSetup.user = dataSnapshot.getValue(User.class);
                AppSetup.user.setFirebaseUser(mAuth.getCurrentUser());
                startActivity(new Intent(LoginActivity.this, ProdutosActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(findViewById(R.id.R_id_container_activity_login), getString(R.string.snack_problem_autenticacao), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void resetarSenha(final String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle("Atenção");
        builder.setMessage("Um email de recuperação de senha será enviado para: " + email);
        //add the buttons
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });

            }});
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Snackbar.make(findViewById(R.id.R_id_container_activity_login), "Operação cancelada.", Snackbar.LENGTH_LONG).show();
                }
            });

        builder.show();
    }
}
