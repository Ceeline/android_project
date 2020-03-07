package mobile_dev.project.android_project.frag_Cocktail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        ImageView image = findViewById(R.id.cocktail_Img);
        cocktail.getImageFromURL(this, cocktail.image, image);

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

        for (Map.Entry<String, String> entry : cocktail.ingredients.entrySet()) {
            //TODO verifier si l'objet n'existe pas déjà..
            //TODO prendre que l'int des quantités....
            Ingredients ingredient = new Ingredients(entry.getKey().trim(), 1, Constants.SHOPPING);
            mRepository.insert(ingredient);
        }
    }
}
