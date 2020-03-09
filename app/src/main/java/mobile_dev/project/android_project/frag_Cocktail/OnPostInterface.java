package mobile_dev.project.android_project.frag_Cocktail;

import android.view.View;

public interface OnPostInterface {
    void onPostExecute(Cocktail cocktail);
    String displayIngredients(Cocktail cocktail);
    void onAddItemsClicked(View view);
}