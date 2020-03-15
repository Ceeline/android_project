package mobile_dev.project.android_project.frag_Cocktail;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
        // we get the id of the cocktail to display
        String idCocktail = intent.getStringExtra("idCocktail");

        //we get the details of the cocktail chosen using its id
        String url = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + idCocktail;
        DownloaderTask downloader = new DownloaderTask(this, url, this);
        downloader.execute();

    }

    @Override
    public void onPostExecute(Cocktail cocktail) {
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

        // Format the cocktail Ingredient
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

                    // verify that the object doesn't exist yet
                    String name = entry.getKey().trim();
                    int exist = mRepository.checkifExist(name);

                    if (exist == 0) {
                        Ingredients ingredient;

                        try {
                            //Retrieve the quantity
                            String qty_text = entry.getValue().trim();
                            String unit = null;


                            // Separate the quantities from units

                            int i = 0;
                            while(i < qty_text.length() && checkChar(qty_text.charAt(i))){
                                i++;
                            }

                            String full_qty = qty_text.substring(0, i-1);
                            if(i < qty_text.length()){
                                unit = qty_text.substring(i);
                            }

                            // Check if the quantity has the shape 1 1/2 for example
                            int sum = 0;
                            String[] tab_qty = full_qty.trim().split(" ");


                            String[] tab_qty_frac = tab_qty[tab_qty.length - 1].split("/");
                            int qty = Integer.parseInt(tab_qty_frac[0]);
                            if(tab_qty_frac.length > 1){
                                int qty2 = Integer.parseInt(tab_qty_frac[1]);
                                qty = (int) Math.ceil((double) qty / (double) qty2);
                            }

                            if(tab_qty.length > 1){
                                sum += Integer.parseInt(tab_qty[0]);
                            }
                            sum += qty;

                            // Create the new ingredient
                            ingredient = new Ingredients(name, sum, Constants.SHOPPING, unit);
                        } catch (NumberFormatException nfe) {
                            ingredient = new Ingredients(name, 1, Constants.SHOPPING, null);
                        }

                        // Insert in the BDD
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

    public static boolean checkChar(char ch){
        // Check if the char is a number or space or '/'
        return (ch == 32 || ch == 47 || (ch < 58 && ch > 48));
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
