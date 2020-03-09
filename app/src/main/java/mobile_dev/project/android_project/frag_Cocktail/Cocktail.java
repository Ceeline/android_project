package mobile_dev.project.android_project.frag_Cocktail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(tableName = "cocktails_table")
public class Cocktail implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    String id;

    @ColumnInfo(name = "nameCocktail")
    String name;

    @ColumnInfo(name = "categoryCocktail")
    String category;

    @ColumnInfo(name = "alcoholic")
    Boolean alcoholic;

    @ColumnInfo(name = "imageURL")
    String image;

    // @ColumnInfo(name = "ingredients")
    HashMap<String, String> ingredients;     //ingredient + measure

    @ColumnInfo(name = "instructions")
    String instructions;



    /* Constructor to convert JSON object into a Java class instance */
    public Cocktail(JSONObject object, int type){
        try {
            if (type == 1)
            {
                this.category = object.getString("strCategory");
                this.alcoholic = object.getString("strAlcoholic").equals("Alcoholic");
                this.ingredients = getIngredients(object);
                this.instructions = object.getString("strInstructions");
            }

            this.id = object.getString("idDrink");
            this.name = object.getString("strDrink");
            this.image = object.getString("strDrinkThumb");
            Log.i("IMAGE_URL", this.image);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* method to convert an array of JSON objects into a list of Cocktail objects */
    public static ArrayList<Cocktail> fromJson(JSONArray jsonArray, int type) {

        ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                cocktails.add(new Cocktail(jsonArray.getJSONObject(i), type));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cocktails;
    }

    /* Gets the list of ingredients for the cocktail */
    public HashMap<String, String> getIngredients (JSONObject object){
        HashMap<String, String> ingredientsList = new HashMap<String, String>();

        int i = 1;
        String ingredient = null;

        try {
            do{
                ingredient = object.getString("strIngredient" + i);

                if (!ingredient.equals("null")){
                    String measure = object.getString("strMeasure" + i);
                    if (measure.equals("null")){
                        ingredientsList.put(ingredient, "");
                    }else{
                        ingredientsList.put(ingredient, measure);
                    }
                }

                i++;
            }while (!ingredient.equals("null"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredientsList;
    }

    @Override
    public String toString(){
        return "name: " + name + "\ncategory: " + category + "\nalcolohic: " + alcoholic + "\ningredients: " + ingredients  + "\ninstructions: " + instructions + "\nimage:" + image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeBoolean(alcoholic);
        dest.writeString(image);
        dest.writeString(ingredients.toString());
        dest.writeString(instructions);
    }

    public static final Parcelable.Creator<Cocktail> CREATOR = new Parcelable.Creator<Cocktail>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public Cocktail createFromParcel(Parcel in) {
            return new Cocktail(in);
        }

        public Cocktail[] newArray(int size) {
            return new Cocktail[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Cocktail (Parcel in) {
        id = in.readString();
        name = in.readString();
        category = in.readString();
        alcoholic = in.readBoolean();
        image = in.readString();
        //Ingredients
        String ingredientsStr = in.readString();
        formatString (ingredientsStr);

        instructions = in.readString();
    }

    private HashMap formatString (String s){
        ingredients = new HashMap<>();

        //remove { }
        String newStr = s.substring(s.indexOf('{') + 1, s.length() - 1);

        //separate at , and =
        String[] arrayStr = newStr.split(",|\\=");

        int i = 0;
        while (i < arrayStr.length-1){
            ingredients.put(arrayStr[i], arrayStr[i+1]);
            i+=2;
        }
        return ingredients;
    }
}
