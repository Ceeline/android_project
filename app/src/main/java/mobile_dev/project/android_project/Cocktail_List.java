package mobile_dev.project.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class Cocktail_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail__list);

        // Creation of a ListView
        ListView listView = findViewById(R.id.listCocktails);

        //get data from fetch
        DownloaderTask downloader = new DownloaderTask(this, listView);
        //TODO: search par première lettre ou par nom. Récupérer input de l'utilisateur pour déduire l'url à utiliser dans la ligne suivante
        downloader.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?f=a");

        // Create an onItemClick function for the list view
        AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Cocktail cocktail = (Cocktail) parent.getItemAtPosition(position);
                openActivity(cocktail);
            }
        };

        // Attach the event to the listView
        listView.setOnItemClickListener(messageClickedHandler);

    }

    public void openActivity (Cocktail cocktail) {
        Intent intent = new Intent(this, CocktailDetail.class);
        intent.putExtra("cocktail", cocktail);
        Log.i("TOSEND", cocktail.toString());
        startActivity(intent);

    }

}
