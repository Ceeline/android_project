package mobile_dev.project.android_project.frag_Cocktail;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import mobile_dev.project.android_project.R;
import mobile_dev.project.android_project.database.AppRepository;
import mobile_dev.project.android_project.database.Constants;
import mobile_dev.project.android_project.database.Ingredients;

public class CocktailDetail extends AppCompatActivity implements OnPostInterface {
    Cocktail cocktail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);

        cocktail = null;

        Intent intent = getIntent();
        String idCocktail = intent.getStringExtra("idCocktail");

        //we get the details of the cocktail chosen using its idApi
        String url = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + idCocktail;
        DownloaderTask downloader = new DownloaderTask(this, url, this);
        downloader.execute();
    }

    @Override
    public void onPostExecute(Cocktail cocktail) {
        Log.i("COCKTAIL", cocktail.toString());
        this.cocktail = cocktail;

        TextView name = findViewById(R.id.NameTxt);
        name.setText(cocktail.name);

        //get image from url
        ImageView image = findViewById(R.id.cocktail_Img);
        BitmapDownloaderTask bitmapDownloader = new BitmapDownloaderTask(this, cocktail.image, image);
        bitmapDownloader.execute();

        TextView ingredients = findViewById(R.id.ingredientsTxt);
        ingredients.setText(displayIngredients(cocktail));

        TextView instructions = findViewById(R.id.instructionsTxt);
        instructions.setText(String.format("Instructions: \n%s", cocktail.instructions));
    }

    @Override
    public String displayIngredients(Cocktail cocktail) {
        StringBuilder ingredientsStructured = new StringBuilder("Ingredients: \n");

        for (Map.Entry<String, String> entry : cocktail.ingredients.entrySet()) {
            ingredientsStructured.append("-> " + entry.getKey() + ": " + entry.getValue() + "\n");
        }

        return ingredientsStructured.toString();
    }

    @Override
    public void onAddItemsClicked(View view) {
        if (cocktail != null) {
            AppRepository mRepository = new AppRepository(this.getApplication());

            AsyncTask.execute(() -> {
                for (Map.Entry<String, String> entry : cocktail.ingredients.entrySet()) {

                    // verifier si l'objet n'existe pas déjà
                    String name = entry.getKey().trim();
                    int exist = mRepository.checkifExist(name);

                    if (exist == 0) {
                        Ingredients ingredient;


                        try {
                            //Retrieve the quantity
                            String qty_text = entry.getValue().trim();
                            String[] tab_qty = qty_text.split(" ");

                            //TODO gérer les fractions
                            int qty = Integer.parseInt(tab_qty[0]);
                            String unit = null;
                            if (tab_qty.length > 1) {
                                unit = tab_qty[1];
                            }
                            ingredient = new Ingredients(name, qty, Constants.SHOPPING, unit);
                        } catch (NumberFormatException nfe) {
                            ingredient = new Ingredients(name, 1, Constants.SHOPPING, null);
                        }

                        mRepository.insert(ingredient);
                    }
                }
            });
            Toast.makeText(
                    this.getApplicationContext(),
                    "Added to Shopping List",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onFavoriteClicked(View view) {
        if (cocktail != null) {
            //TODO check qu'il n'est pas déjà dans les favoris ? si oui estce qu'on le supprime ?
            AppRepository mRepository = new AppRepository(this.getApplication());
            mRepository.insert(cocktail);
            Toast.makeText(
                    this.getApplicationContext(),
                    "Added to Favorite",
                    Toast.LENGTH_LONG).show();
        }
    }
}
