package mobile_dev.project.android_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mobile_dev.project.android_project.database.CocktailsListAdapter;
import mobile_dev.project.android_project.database.CocktailsViewModel;
import mobile_dev.project.android_project.frag_Cocktail.Cocktail;
import mobile_dev.project.android_project.frag_Cocktail.CocktailDetail;

public class Favoris extends AppCompatActivity implements CocktailsListAdapter.OnCocktailListener {

    private CocktailsViewModel mCocktailViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_cocktail);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CocktailsListAdapter adapter = new CocktailsListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCocktailViewModel = new ViewModelProvider(this).get(CocktailsViewModel.class);

        mCocktailViewModel.getAll().observe(this, adapter::setCocktails);
    }

    @Override
    public void OnCocktailClick(Cocktail cocktail) {
        Intent intent = new Intent(this, CocktailDetail.class);
        intent.putExtra("idCocktail", cocktail.getIdApi());
        startActivity(intent);
    }
}
