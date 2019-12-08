package br.com.opm.anxietyoff.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.ui.adapter.AdapterCreator;

public class RecyclerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_generic);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getViews();
        setRecyclerView();
    }

    private void getViews() {
        recyclerView = findViewById(R.id.generic_recyclerView);
    }

    private void setRecyclerView() {
        AdapterCreator adapterCreator = new AdapterCreator(this);
        RecyclerView.Adapter adapter = adapterCreator.adapterChooser(Objects.requireNonNull(getIntent().getStringArrayExtra("adapter")));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
