package com.recipesapp;

import java.io.Serializable;

public class RecipesModel implements Serializable {

    private String name;
    private String description;
    private int image;

    public RecipesModel(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    // Method for getting the recipe name
    public String getName() {
        return name;
    }
    // method for setting the recipe name
    public void setName(String name) {
        this.name = name;
    }
    // Method for getting the recipe description
    public String getDescription() {
        return description;
    }
    // Method for setting the recipe description
    public void setDescription(String description) {
        this.description = description;
    }
    // Method for getting the recipe image
    public int getImage() {
        return image;
    }
    // Method for setting the recipe image
    public void setImage(int image) {
        this.image = image;
    }
}
