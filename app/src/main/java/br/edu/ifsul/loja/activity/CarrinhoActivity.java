package br.edu.ifsul.loja.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.setup.AppSetup;

public class CarrinhoActivity extends AppCompatActivity {

    private static final String TAG = "carrinhoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Log.d(TAG, "Carrinho=" + AppSetup.carrinho);
    }
}
