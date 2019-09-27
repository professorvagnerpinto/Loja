package br.edu.ifsul.loja.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.model.Produto;
import br.edu.ifsul.loja.setup.AppSetup;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private  TextView tvNome, tvValor, tvEstoque, tvDescricao, tvVendedor;
    private ImageView imvFoto;
    private ProgressBar pbFoto;
    private EditText etQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        //obtém o position do objeto produto a a partir da intent
        int position = getIntent().getExtras().getInt("position");
        Produto produto = AppSetup.listProdutos.get(position);

        //mapeia os componentes da view
        tvNome = findViewById(R.id.tvNomeProduto);
        imvFoto = findViewById(R.id.imvFoto);
        pbFoto = findViewById(R.id.pb_foto_produto_detalhe);
        tvValor = findViewById(R.id.tvValorProduto);
        tvEstoque = findViewById(R.id.tvQuantidadeProduto);
        tvDescricao = findViewById(R.id.tvDerscricaoProduto);
        etQuantidade = findViewById(R.id.etQuantidade);
        tvVendedor = findViewById(R.id.tvVendedor);

        //trata evento onClick do botão
        findViewById(R.id.btComprarProduto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProdutoDetalheActivity.this, "Ok", Toast.LENGTH_SHORT).show();
            }
        });

        //bind nos componentes da view
        tvNome.setText(produto.getNome());
        tvValor.setText(NumberFormat.getCurrencyInstance().format(produto.getValor()));
        tvEstoque.setText(produto.getQuantidade().toString());
        tvDescricao.setText(produto.getDescricao());
        //ativar quando tiver a navegabilidade definida
        //tvVendedor.setText(AppSetup.user.getEmail());
        if(produto.getUrl_foto().equals("")){
            pbFoto.setVisibility(View.INVISIBLE);
        }else{
            //carrega a foto aqui
        }
    }
}
