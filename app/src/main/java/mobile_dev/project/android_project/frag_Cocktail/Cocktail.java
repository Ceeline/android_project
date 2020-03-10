package mobile_dev.project.android_project.frag_Cocktail;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(tableName = "cocktails_table")
public class Cocktail {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "idApi")
    public String idApi;

    @ColumnInfo(name = "nameCocktail")
    public String name;

    @ColumnInfo(name = "categoryCocktail")
    public String category;

    @ColumnInfo(name = "alcoholic")
    public Boolean alcoholic;

    @ColumnInfo(name = "imageURL")
    public String image;

    @ColumnInfo(name = "ingredients")
    public HashMap<String, String> ingredients;     //ingredient + measure

    @ColumnInfo(name = "instructions")
    public String instructions;


    public Cocktail (@NonNull String name, boolean alcoholic, String instructions, String image){
        this.name = name;
        this.alcoholic = alcoholic;
        this.instructions = instructions;
        this.image = image;
    }


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

            this.idApi = object.getString("idDrink");
            this.name = object.getString("strDrink");
            this.image = object.getString("strDrinkThumb");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* method to convert an array of JSON objects into a list of Cocktail objects */
    public static ArrayList<Cocktail> fromJson(JSONArray jsonArray) {

        ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                cocktails.add(new Cocktail(jsonArray.getJSONObject(i), 2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cocktails;
    }

    public static Cocktail fromJson(JSONObject jsonObject) {
        Cocktail cocktail = new Cocktail(jsonObject, 1);
        return cocktail;
    }

    /* Gets the list of ingredients for the cocktail */
    public HashMap<String, String> getIngredients (JSONObject object){
        HashMap<String, String> ingredientsList = new HashMap<String, String>();

        int i = 1;
        String ingredient;

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

    @NonNull
    @Override
    public String toString(){
        return "name: " + name + "\ncategory: " + category + "\nalcolohic: " + alcoholic + "\ningredients: " + ingredients  + "\ninstructions: " + instructions + "\nimage:" + image;
    }

    public String getIdApi(){
        return idApi;
    }
}
