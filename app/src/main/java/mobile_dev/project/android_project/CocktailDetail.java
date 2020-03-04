package mobile_dev.project.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Dictionary;
import java.util.Map;

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

    public String displayIngredients (){
        String ingredientsStructured = "Ingredients: \n";

        for (Map.Entry<String, String> entry : cocktail.ingredients.entrySet()) {
            ingredientsStructured += "-> " + entry.getKey() + ": " + entry.getValue() + "\n";
        }

        return ingredientsStructured;
    }
}
