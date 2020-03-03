package mobile_dev.project.android_project;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Cocktail implements Parcelable {
    String id;
    String name;
    String category;
    Boolean alcoholic;
    String image;
    Dictionary<String, String> ingredients;     //ingredient + measure
    String instructions;

    /* Complete constructor for Cocktail class*/
    public Cocktail (String id, String name, String category, Boolean alcoholic, String image, Dictionary<String, String> ingredients, String instructions)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.alcoholic = alcoholic;
        this.image = image;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    /*Constructor without ingredients and instructions*/
    public Cocktail (String id, String name, String category, Boolean alcoholic, String image)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.alcoholic = alcoholic;
        this.image = image;
    }

    /* Constructor to convert JSON object into a Java class instance */
    public Cocktail(JSONObject object){
        try {
            this.id = object.getString("idDrink");
            this.name = object.getString("strDrink");
            this.category = object.getString("strCategory");
            this.alcoholic = object.getString("strAlcoholic").equals("Alcoholic");
            this.image = object.getString("strDrinkThumb");
            this.ingredients = getIngredients(object);
            this.instructions = object.getString("strInstructions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* method to convert an array of JSON objects into a list of Cocktail objects */
    public static ArrayList<Cocktail> fromJson(JSONArray jsonArray) {

        ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                cocktails.add(new Cocktail(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cocktails;
    }

    /* Gets the list of ingredients for the cocktail */
    public Dictionary<String, String> getIngredients (JSONObject object){
        Dictionary<String, String> ingredientsList = new Hashtable<String, String>();

        int i = 1;
        String ingredient = null;

        try {
            do{
                ingredient = object.getString("strIngredient" + i);

                if (!ingredient.equals("null")){
                    String measure = object.getString("strMeasure" + i);
                    ingredientsList.put(ingredient, measure);
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
        dest.writeValue(ingredients);
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
        ingredients = in.readParcelable(Dictionary.class.getClassLoader());
        instructions = in.readString();
    }
}
