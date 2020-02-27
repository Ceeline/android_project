package mobile_dev.project.android_project;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;

public class Cocktail {
    String id;
    String name;
    String category;
    Boolean alcoholic;
    Bitmap image;
    Dictionary<String, String> ingredients;     //ingredient + measure
    String instructions;

    /* Complete constructor for Cocktail class*/
    public Cocktail (String id, String name, String category, Boolean alcoholic, Bitmap image, Dictionary<String, String> ingredients, String instructions)
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
    public Cocktail (String id, String name, String category, Boolean alcoholic, Bitmap image)
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
            this.image = (Bitmap) object.get("strDrinkThumb");
            this.ingredients = getIngredients(object);
            this.instructions = object.getString("strInstructions");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* method to convert an array of JSON objects into a list of Cocktail objects */
    public static ArrayList<Cocktail> fromJson(JSONArray jsonObjects) {

        ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>();

        for (int i = 0; i < jsonObjects.length(); i++) {

            try {
                cocktails.add(new Cocktail(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cocktails;
    }

    /* Gets the list of ingredients for the cocktail */
    public Dictionary<String, String> getIngredients (JSONObject object){
        Dictionary<String, String> ingredientsList = null;

        int i = 1;

        try {
            do{

                String ingredient = object.getString("strIngredient" + i);

                if (ingredient != null){
                    String measure = object.getString("strMeasure" + i);

                    ingredientsList.put(ingredient, measure);
                }

                i++;
            }while (!ingredients.equals(null));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredientsList;
    }
}
