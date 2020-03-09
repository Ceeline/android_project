package mobile_dev.project.android_project.frag_Cocktail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import mobile_dev.project.android_project.R;
import mobile_dev.project.android_project.database.AppRepository;
import mobile_dev.project.android_project.database.Constants;
import mobile_dev.project.android_project.database.Ingredients;

public class CocktailDetail extends AppCompatActivity implements OnPostInterface{
    Cocktail cocktail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);

        cocktail = null;

        Intent intent = getIntent();
        String idCocktail = intent.getExtras().getString("idCocktail");

        //we get the details of the cocktail chosen using its id
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
        if (cocktail != null){
            AppRepository mRepository = new AppRepository(this.getApplication());

            for (Map.Entry<String, String> entry : cocktail.ingredients.entrySet()) {
                //TODO verifier si l'objet n'existe pas déjà..
                //TODO prendre que l'int des quantités....
                Ingredients ingredient = new Ingredients(entry.getKey().trim(), 1, Constants.SHOPPING);
                mRepository.insert(ingredient);
            }
        }
    }
}
