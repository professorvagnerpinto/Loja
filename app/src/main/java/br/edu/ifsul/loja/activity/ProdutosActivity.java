package br.edu.ifsul.loja.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.adapter.ProdutosAdapter;

public class ProdutosActivity extends AppCompatActivity {

    private static final String TAG = "produtosActivity";
    private ListView lvProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        //mapeia o componente da view
        lvProdutos = findViewById(R.id.lv_produtos);

        //buscar os dados no RealTimeDataBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/produtos");
        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "dataSnapshot=" + dataSnapshot);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d(TAG, "dataSnapshot=" + ds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //faz o bindView
        //lvProdutos.setAdapter(new ProdutosAdapter());

    }
}
