package br.edu.ifsul.loja.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifsul.loja.R;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btLogar;
    private TextView tvEsqueceuSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mapeia os componentes da UI
        etEmail = findViewById(R.id.etEmail_login);
        etSenha = findViewById(R.id.etSenha_login);
        btLogar = findViewById(R.id.btLogar_login);
        tvEsqueceuSenha = findViewById(R.id.tv_esqueceusenha_login);

        final String email = etEmail.getText().toString();
        final String senha = etSenha.getText().toString();

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.isEmpty() && !senha.isEmpty()){
                    signIn(email, senha);
                }else{
                    Toast.makeText(MainActivity.this, getString(R.string.toast_preencher_todos_campos), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
