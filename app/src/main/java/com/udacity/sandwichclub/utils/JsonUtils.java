package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
  private static final String KEY_NAME = "name";
  private static final String KEY_MAIN_NAME = "mainName";
  private static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
  private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
  private static final String KEY_DESCRIPTION = "description";
  private static final String KEY_IMAGE = "image";
  private static final String KEY_INGREDIENTS = "ingredients";

  public static Sandwich parseSandwichJson(String json) {
    try {
      JSONObject sandwichJson = new JSONObject(json);
      if (sandwichJson == null) {
        return null;
      }
      Sandwich model = new Sandwich();

      // Process "mainName" and "alsoKnownAs" components within "name" block
      if (!sandwichJson.has(KEY_NAME)) {
        return null;
      }
      JSONObject nameJson = sandwichJson.getJSONObject(KEY_NAME);
      if (nameJson == null) {
        return null;
      }
      if (!nameJson.has(KEY_MAIN_NAME) || !nameJson.has(KEY_ALSO_KNOWN_AS)) {
        return null;
      }
      model.setMainName(nameJson.getString(KEY_MAIN_NAME));
      final JSONArray elements = nameJson.getJSONArray(KEY_ALSO_KNOWN_AS);
      if (elements == null) {
        return null;
      }
      List<String> alsoKnownList = new ArrayList<String>(elements.length());
      for (int i = 0, size = elements.length(); i < size; ++i) {
        alsoKnownList.add(String.valueOf(elements.get(i)));
      }
      model.setAlsoKnownAs(alsoKnownList);

      // Process "placeOfOrigin" field.
      if (!sandwichJson.has(KEY_PLACE_OF_ORIGIN)) {
        return null;
      }
      model.setPlaceOfOrigin(String.valueOf(sandwichJson.get(KEY_PLACE_OF_ORIGIN)));

      // Process "description" field.
      if (!sandwichJson.has(KEY_DESCRIPTION)) {
        return null;
      }
      model.setDescription(String.valueOf(sandwichJson.get(KEY_DESCRIPTION)));

      // Process "image" field.
      if (!sandwichJson.has(KEY_IMAGE)) {
        return null;
      }
      model.setImage(String.valueOf(sandwichJson.get(KEY_IMAGE)));

      // Process "ingredients" field.
      if (!sandwichJson.has(KEY_INGREDIENTS)) {
        return null;
      }
      final JSONArray ingredientsJson = sandwichJson.getJSONArray(KEY_INGREDIENTS);
      if (ingredientsJson == null) {
        return null;
      }
      List<String> ingredientsList = new ArrayList<String>(ingredientsJson.length());
      for (int i = 0, size = ingredientsJson.length(); i < size; ++i) {
        ingredientsList.add(String.valueOf(ingredientsJson.get(i)));
      }
      model.setIngredients(ingredientsList);
      return model;
    } catch (JSONException e) {
      // TODO Sahak: log and dump the parsing error into stack trace later on.
      // e.printStackTrace();
      return null;
    }
  }
}
