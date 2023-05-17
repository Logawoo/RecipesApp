package com.recipesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RecipeViewActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    TextView textView2;
    RecipesModel recipesModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Grabbing the image and textViews
        imageView = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView3);

        // Grabbing intent that started the activity
        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            // Getting the RecipesModel object passed from the last activity
            recipesModel = (RecipesModel) intent.getSerializableExtra("item");

            // Setting the image, name, and description
            imageView.setImageResource(recipesModel.getImage());
            textView.setText(recipesModel.getName());
            textView2.setText(recipesModel.getDescription());

        }
    }
}