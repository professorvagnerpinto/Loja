package br.edu.ifsul.loja.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifsul.loja.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mainActivity";
    private EditText etEmail, etSenha;
    private Button btLogar;
    private TextView tvEsqueceuSenha;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //mapeia os componentes da UI
        etEmail = findViewById(R.id.etEmail_login);
        etSenha = findViewById(R.id.etSenha_login);
        btLogar = findViewById(R.id.btLogar_login);
        tvEsqueceuSenha = findViewById(R.id.tv_esqueceusenha_login);

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                Log.d(TAG, "email=" + email + " senha=" + senha);
                if(!email.isEmpty() && !senha.isEmpty()){
                    signIn(email, senha);
                }else{
                    Toast.makeText(MainActivity.this, getString(R.string.toast_preencher_todos_campos), Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        tvEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                if(!email.isEmpty() && !senha.isEmpty()){
                    resetarSenha(email, senha);
                }else{
                    Toast.makeText(MainActivity.this, getString(R.string.toast_preencher_todos_campos), Toast.LENGTH_SHORT).show();
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, getString(R.string.toast_falha_autenticacao),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void resetarSenha(String email, String senha) {
    }
}
