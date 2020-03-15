package mobile_dev.project.android_project.frag_Cocktail;

import android.view.View;

interface OnPostInterface {
    void onPostExecute(Cocktail cocktail);      // Action to execute once we got the results from our request to the API
    void onAddItemsClicked(View view);          // Action triggered when the 'add missing ingredients' button is selected
}