package br.com.opm.anxietyoff.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.CloudFirestore;
import br.com.opm.anxietyoff.model.Answers;
import br.com.opm.anxietyoff.ui.adapter.AdapterCreator;
import br.com.opm.anxietyoff.ui.adapter.RecyclerViewBeckAnxietyInventoryAdapter;

public class BeckAnxietyInventoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CloudFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beck_anxiety_inventory);
        firestore=new CloudFirestore(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getViews();
        setViews();
    }

    private void getViews() {
        recyclerView=findViewById(R.id.activity_beck_test_recyclerView);
    }

    private void setViews(){
        RecyclerViewBeckAnxietyInventoryAdapter adapter = new AdapterCreator(this).beckTestAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void onClickSubmit(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação de submissão");
        builder.setMessage("Deseja mesmo submeter o teste? Ele ficará salvo no seu perfil." +
                "Terá a opção de apaga-lo se assim desejar.");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                RecyclerViewBeckAnxietyInventoryAdapter adapter = (RecyclerViewBeckAnxietyInventoryAdapter) recyclerView.getAdapter();
                Answers answers = adapter.getAnswers();
                firestore.addBeckTestAnswers(answers);
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        AlertDialog alerta = builder.create();
        alerta.show();

    }

    private void verifyExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação de saída");
        builder.setMessage("Tem certeza que deseja sair do teste? As informações do teste atual serão descartadas.");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        AlertDialog alerta = builder.create();
        alerta.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            verifyExit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        verifyExit();
    }
}
