package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

/**
 * author Sahak. Done changes based on the initial code provided.
 */
public class DetailActivity extends AppCompatActivity {
  public static final String EXTRA_POSITION = "extra_position";

  // TODO Sahak: I could have used 'comma' character as a separator, but I wanted to test the
  //             screen scrolling on my device.
  private static final String NEW_LINE_SEPARATOR = "\n";
  private static final int DEFAULT_POSITION = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    Intent intent = getIntent();
    if (intent == null) {
      closeOnError();
      return;
    }

    final int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
    if (position == DEFAULT_POSITION) {
      closeOnError();
      return;
    }

    String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
    String json = sandwiches[position];
    Sandwich sandwich = JsonUtils.parseSandwichJson(json);
    if (sandwich == null) {
      closeOnError();
      return;
    }
    initializeComponents(sandwich);
  }

  private void closeOnError() {
    finish();
    Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
  }

  private void initializeComponents(final Sandwich sandwich) {
    setTitle(sandwich.getMainName());

    LinearLayout imageHolder = findViewById(R.id.image_holder);
    ImageView imageIv = findViewById(R.id.image_iv);
    if (sandwich.getImage().isEmpty()) {
      imageHolder.setVisibility(View.GONE);
      imageIv.setVisibility(View.GONE);
    } else {
      Picasso.with(this).load(sandwich.getImage()).into(imageIv);
    }

    TextView placeOfOriginLabel = findViewById(R.id.place_of_origin_label);
    TextView placeOfOriginTv = findViewById(R.id.place_of_origin_tv);
    if (sandwich.getPlaceOfOrigin().isEmpty()) {
      placeOfOriginTv.setVisibility(View.GONE);
      placeOfOriginLabel.setVisibility(View.GONE);
    } else {
      placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
    }

    TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
    TextView alsoKnownAsLabel = findViewById(R.id.also_known_as_label);
    final String alsoKnownAsData = convertArrayToString(sandwich.getAlsoKnownAs());
    if (alsoKnownAsData.isEmpty()) {
      alsoKnownAsTv.setVisibility(View.GONE);
      alsoKnownAsLabel.setVisibility(View.GONE);
    } else {
      alsoKnownAsTv.setText(alsoKnownAsData);
    }

    TextView descriptionTv = findViewById(R.id.description_tv);
    descriptionTv.setText(sandwich.getDescription());

    TextView ingredientsTv = findViewById(R.id.ingredients_tv);
    ingredientsTv.setText(convertArrayToString(sandwich.getIngredients()));
  }

  private String convertArrayToString(final List<String> elements) {
    if (elements == null || elements.isEmpty()) {
      return getResources().getString(R.string.blank);
    }
    StringBuilder builder = new StringBuilder();
    builder.append(elements.get(0));
    for (int i=1, size = elements.size(); i < size; ++i) {
      builder.
        append(NEW_LINE_SEPARATOR).
        append(elements.get(i));
    }
    return builder.toString();
  }
}
