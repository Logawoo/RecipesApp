package com.recipesapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    // Recipe image array
    int images[] = {R.drawable.taco, R.drawable.ratatoille, R.drawable.tuscan, R.drawable.skillet, R.drawable.tuna, R.drawable.scallops};
    // Recipe name array
    String names[] = {"Taco Soup", "Ratatouille", "Creamy Tuscan Chicken", "Chicken Parmesan", "Tuna Pasta", "Fancy Scallops"};
    // Recipe description array
    String description[] = {"This easy taco soup recipe is a 30 MINUTE MEAL!", "Ratatouille is a bright and chunky summer vegetable stew, rich with olive oil and fragrant with garlic and herbs", "Our recipe uses thinly sliced chicken breast, fresh spinach, flavorful sun-dried tomatoes, all swimming in a delicious creamy sauce", "This easy Chicken Parmesan recipe is restaurant quality with breaded chicken, marinara sauce, mozzarella, and parmesan cheese", "This canned tuna pasta recipe is quick, healthy, and comforting", "These pan seared scallops with garlic basil butter take less than 10 minutes and taste incredible!"};

    List<RecipesModel> listItems = new ArrayList<>();

    // Custom adapter will be used to grab data and make view for each item
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // grabbing listView component
        listView = findViewById(R.id.listView);

        // for loop to fill list based on how many recipes are in array
        for (int i = 0; i < names.length; i++) {
            RecipesModel recipesModel = new RecipesModel(names[i],description[i],images[i]);

            listItems.add(recipesModel);
        }
        customAdapter = new CustomAdapter(listItems, this );

        listView.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflating the menu objects
        getMenuInflater().inflate(R.menu.menu,menu);

        // finding search bar item in menu
        MenuItem menuItem = menu.findItem(R.id.search_view);

        // getting searchView from the item
        SearchView searchView = (SearchView) menuItem.getActionView();

        // Setting a listener for text changes in the search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Applying the filter as text is entered
                customAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_view) {
            return true;
        }
        // changing activity when favorite item is selected
        if (id == R.id.favorite_view) {
            Intent intent = new Intent(MainActivity.this, FavActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends BaseAdapter implements Filterable {
        private List<RecipesModel> recipesModelList;
        private List<RecipesModel> recipesModelListFiltered;
        private Context context;

        // Constructor for CustomAdapter
        public CustomAdapter(List<RecipesModel> recipesModelList, Context context) {
            this.recipesModelList = recipesModelList;
            // At first filtered list is the same as the regular list
            this.recipesModelListFiltered = recipesModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return recipesModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // Creates the view for each item in the ListView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Inflating the row_items layout
            View view = getLayoutInflater().inflate(R.layout.row_items, null);

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView recipeName = view.findViewById(R.id.itemName);
            TextView recipeDescription = view.findViewById(R.id.itemDescription);

            // Setting the image, name, and description for the current item
            imageView.setImageResource(recipesModelListFiltered.get(position).getImage());
            recipeName.setText(recipesModelListFiltered.get(position).getName());
            recipeDescription.setText(recipesModelListFiltered.get(position).getDescription());

            // Adding a click listener to each item to open a new activity
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, RecipeViewActivity.class).putExtra("item", recipesModelListFiltered.get(position)));
                }
            });
            return view;
        }

        // Returns the filter used for searching the recipes
        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint == null || constraint.length() == 0) {
                        // If the search query is empty, return the whole list
                        filterResults.count = recipesModelList.size();
                        filterResults.values = recipesModelList;
                    }
                    else {
                        String searchString = constraint.toString().toLowerCase();
                        List<RecipesModel> resultData = new ArrayList<>();
                        for(RecipesModel recipesModel:recipesModelList) {
                            // Filtering based on recipe name or description
                            if (recipesModel.getName().contains(searchString) || recipesModel.getDescription().contains(searchString)) {
                                resultData.add(recipesModel);
                            }
                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    // Updates the filtered list with the search results
                    recipesModelListFiltered = (List<RecipesModel>) results.values;

                    // Tells the adapter that the data set changed, causing a refresh of the ListView
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }
}