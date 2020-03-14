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

import java.util.Map;

import mobile_dev.project.android_project.R;
import mobile_dev.project.android_project.database.AppRepository;
import mobile_dev.project.android_project.database.Constants;
import mobile_dev.project.android_project.ingredient_data_ui.Ingredients;

public class CocktailDetail extends AppCompatActivity implements OnPostInterface {
    private Cocktail cocktail;
    private AppRepository mRepository;
    private ImageButton btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);
        mRepository = new AppRepository(this.getApplication());

        btnFavorite = findViewById(R.id.btnFavorite);
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

        //set image for favorite button
        AsyncTask.execute(() -> {
            int exist = mRepository.checkExistCockatil(cocktail.idApi);

            if (exist == 0) {
                btnFavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
            } else {
                btnFavorite.setImageResource(R.drawable.ic_star_blue_24dp);
            }
        });


        TextView ingredients = findViewById(R.id.ingredientsTxt);
        ingredients.setText(displayIngredients(cocktail));

        TextView instructions = findViewById(R.id.instructionsTxt);
        instructions.setText(String.format("%s", cocktail.instructions));
    }

    private String displayIngredients(Cocktail cocktail) {
        StringBuilder ingredientsStructured = new StringBuilder();

        for (Map.Entry<String, String> entry : cocktail.ingredients.entrySet()) {
            ingredientsStructured.append("\u2022 " + entry.getKey() + ": " + entry.getValue() + "\n");
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
                                if(tab_qty[1].equals("null")){
                                    unit = tab_qty[1];
                                }
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
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onFavoriteClicked(View view) {
        new PrivateAsyncTask(output -> {
            if (output == 1) {
                btnFavorite.setImageResource(R.drawable.ic_star_blue_24dp);
                Toast.makeText(
                        CocktailDetail.this,
                        "Added to Favorite",
                        Toast.LENGTH_SHORT).show();
            } else if (output == 2) {
                btnFavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
                Toast.makeText(
                        CocktailDetail.this,
                        "Deleted from Favorite",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(
                        CocktailDetail.this,
                        "Something went wrong.. try again",
                        Toast.LENGTH_SHORT).show();
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

        final AsyncResponse delegate;

        PrivateAsyncTask(AsyncResponse delegate) {
            this.delegate = delegate;
        }

        @Override
        protected void onPostExecute(Integer result) {
            delegate.processFinish(result);
        }
    }

    interface AsyncResponse {
        void processFinish(Integer output);
    }
}
