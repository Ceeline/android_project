package mobile_dev.project.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Dictionary;

public class CocktailDetail extends AppCompatActivity {
    Cocktail cocktail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);

        //in the next activity:
        Intent intent = getIntent();
        cocktail = intent.getParcelableExtra("cocktail");

        TextView name = findViewById(R.id.cocktail_Name);
        name.setText(cocktail.name);

        ImageView image = findViewById(R.id.cocktail_Img);
        // image.setImageBitmap(cocktail.image);

        TextView ingredients = findViewById(R.id.ingredients_Txt);
        ingredients.setText(displayIngredients());

        TextView instructions = findViewById(R.id.instructionsTxt);
        instructions.setText(cocktail.instructions);

    }

    public String displayIngredients (){
        return cocktail.ingredients.toString();
    }
}
