package com.example.lab_7_sas_houda.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab_7_sas_houda.R;
import com.example.lab_7_sas_houda.adapter.VedetteAdapter;
import com.example.lab_7_sas_houda.service.VedetteService;

public class GalerieActivity extends AppCompatActivity {

    private VedetteAdapter vedetteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galerie);

        RecyclerView recycler = findViewById(R.id.recyclerVedettes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        vedetteAdapter = new VedetteAdapter(this,
                VedetteService.getInstance().findAll());
        recycler.setAdapter(vedetteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_galerie, menu);

        MenuItem itemRecherche = menu.findItem(R.id.menu_recherche);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemRecherche);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return true; }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (vedetteAdapter != null)
                    vedetteAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_partager) {
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setChooserTitle("Partager la galerie")
                    .setText("Découvre l'application Galerie Vedettes !")
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }
}