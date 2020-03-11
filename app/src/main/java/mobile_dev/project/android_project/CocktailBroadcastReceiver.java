package mobile_dev.project.android_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import mobile_dev.project.android_project.database.CocktailsViewModel;

public class CocktailBroadcastReceiver extends BroadcastReceiver {

    CocktailsViewModel cocktailsViewModel;

    CocktailBroadcastReceiver(CocktailsViewModel cocktailsViewModel){
        this.cocktailsViewModel = cocktailsViewModel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("celia", "favorite clicked and received");
        Toast.makeText(context, "Broadcast message received ! (dynamic)",
                Toast.LENGTH_SHORT).show();
    }
}
