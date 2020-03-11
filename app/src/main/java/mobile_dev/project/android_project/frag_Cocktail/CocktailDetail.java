package mobile_dev.project.android_project.frag_Cocktail;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Map;

import mobile_dev.project.android_project.R;
import mobile_dev.project.android_project.database.AppRepository;
import mobile_dev.project.android_project.database.Constants;
import mobile_dev.project.android_project.database.Ingredients;

public class CocktailDetail extends AppCompatActivity implements OnPostInterface {
    Cocktail cocktail;
    AppRepository mRepository;

    //private  OnFavorisClickListener onFavorisClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);
        mRepository = new AppRepository(this.getApplication());

        cocktail = null;

        Intent intent = getIntent();
        //onFavorisClickListener = (OnFavorisClickListener) intent.getSerializableExtra("interface");
        String idCocktail = intent.getStringExtra("idCocktail");
        boolean mode = intent.getBooleanExtra("mode", false);

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
        new PrivateAsyncTask(output -> {
            if (output == 1) {
                Toast.makeText(
                        CocktailDetail.this,
                        "Added to Favorite",
                        Toast.LENGTH_LONG).show();
            } else if (output == 2) {
                Toast.makeText(
                        CocktailDetail.this,
                        "Deleted from Favorite",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(
                        CocktailDetail.this,
                        "Something went wrong.. try again",
                        Toast.LENGTH_LONG).show();
            }
        }).execute();
    }

    private class PrivateAsyncTask extends AsyncTask<Cocktail, Void, Integer> {
        protected Integer doInBackground(Cocktail... cocktails) {
            if (cocktail != null) {
                int exist = mRepository.checkExistCockatil(cocktail.idApi);

                if (exist == 0) {
                    mRepository.insert(cocktail);
                    return 1;
                } else {
                    mRepository.delete(cocktail);
                    return 2;
                }
            }
            return 0;
        }

        AsyncResponse delegate;

        PrivateAsyncTask(AsyncResponse delegate) {
            this.delegate = delegate;
        }

        @Override
        protected void onPostExecute(Integer result) {
            delegate.processFinish(result);
        }
    }

    public interface AsyncResponse {
        void processFinish(Integer output);
    }
}
