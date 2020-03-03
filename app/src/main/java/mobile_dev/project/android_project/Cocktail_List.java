package mobile_dev.project.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class Cocktail_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail__list);


        //until we code a way to retrieve data from the api

        //--- ATTACHING THE ADAPTER TO A LIST VIEW

        // Construct the data source
        ArrayList<Cocktail> cocktailsList = new ArrayList<Cocktail>();

        // Create the adapter to convert the array to views
        CocktailsAdapter adapter = new CocktailsAdapter(this, cocktailsList);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listCocktails);
        listView.setAdapter(adapter);

        //---


        //get data from fetch
        JSONArray jsonArray = new JSONArray();        //result of fetch
        ArrayList<Cocktail> newCocktails = Cocktail.fromJson(jsonArray);
        adapter.addAll(newCocktails);
    }
}
