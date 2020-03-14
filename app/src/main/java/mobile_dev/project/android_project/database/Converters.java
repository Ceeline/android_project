package mobile_dev.project.android_project.database;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.HashMap;
import java.util.Map;

public class Converters {

    @TypeConverter
    public static HashMap<String, String> StringToMap(String value) {
        if(value.equals("")){
            return null;
        }

        String[] ingredients = value.split("-");
        HashMap<String, String> ingredientsList = new HashMap<>();

        for (int i = 1; i < ingredients.length; i++) {
            if(!ingredients[i].equals("")){
                ingredientsList.put(ingredients[i-1], ingredients[i]);
            }else{
                ingredientsList.put(ingredients[i-1], "");
            }
        }
        return ingredientsList;
    }

    @TypeConverter
    public static String MapToString(HashMap<String, String> ingredients) {
        if(ingredients == null){
            return "";
        }
        StringBuilder str = new StringBuilder();

        for (Map.Entry<String, String> entry : ingredients.entrySet()) {
            str.append(entry.getKey() + '-' + entry.getKey() + '-');
        }

        return str.toString();
    }
}
