package mobile_dev.project.android_project.frag_Cocktail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mobile_dev.project.android_project.R;
import mobile_dev.project.android_project.database.AppRepository;
import mobile_dev.project.android_project.database.Constants;
import mobile_dev.project.android_project.database.Ingredients;

public class CocktailDetail extends AppCompatActivity {
    Cocktail cocktail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);

        Intent intent = getIntent();
        cocktail = intent.getParcelableExtra("cocktail");

        TextView name = findViewById(R.id.NameTxt);
        name.setText(cocktail.name);

        //get image from url
        ImageView image = findViewById(R.id.cocktail_Img);
        BitmapDownloaderTask bitmapDownloader = new BitmapDownloaderTask(this, cocktail.image, image);
        bitmapDownloader.execute();

        TextView ingredients = findViewById(R.id.ingredientsTxt);
        ingredients.setText(displayIngredients());

        TextView instructions = findViewById(R.id.instructionsTxt);
        instructions.setText(String.format("Instructions: \n%s", cocktail.instructions));

    }

    public String displayIngredients() {
        StringBuilder ingredientsStructured = new StringBuilder("Ingredients: \n");

        for (Map.Entry<String, String> entry : cocktail.ingredients.entrySet()) {
            ingredientsStructured.append("-> " + entry.getKey() + ": " + entry.getValue() + "\n");
        }

        return ingredientsStructured.toString();
    }

    public void onAddItemsClicked(View view) {
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
                                int qty = Integer.parseInt(tab_qty[0]);
                                ingredient = new Ingredients(name, qty, Constants.SHOPPING);
                            } catch (NumberFormatException nfe) {
                                ingredient = new Ingredients(name, 1, Constants.SHOPPING);
                            }

                            mRepository.insert(ingredient);
                        }
                    }
                }

        );
    }
}
