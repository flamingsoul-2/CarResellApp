package com.kir4agi.caradvisorapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.controller.HTMLBrandParser;
import com.kir4agi.caradvisorapp.model.Brand;

import java.util.ArrayList;

public class BrandsActivity extends AppCompatActivity {

    public LoadingScreen loadingScreen;
    static ListViewBrand adapter;
    ConstraintLayout constraintLayout;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loadingScreen.setVisible(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        constraintLayout = findViewById(R.id.constrained_layout_2);
        layoutInflater.inflate(R.layout.action_bar_1,constraintLayout);
        HTMLBrandParser brandParser = new HTMLBrandParser();
        ArrayList<Brand> brands = brandParser.getAllBrandsArrayList();

        ListView listView = findViewById(R.id.list_view_2);
        adapter = new ListViewBrand(this,R.layout.brand_buble,brands,this);
        listView.setAdapter(adapter);



        loadingScreen = findViewById(R.id.loadingScreen);
        loadingScreen.setVisible(false);


        ImageView myCollection = findViewById(R.id.imageView_hearth);
        myCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingScreen.setVisible(true);
                ActivityStarter.bringCollection(BrandsActivity.this);

            }
        });
    }



    public void proceed(String URL, String brand ,Bitmap bitmap){
        Intent intent = new Intent(this, ModelsActivity.class);
        intent.putExtra("URL", URL);
        intent.putExtra("image", bitmap);
        intent.putExtra("brand", brand);
        ActivityStarter.bringActivity(this,intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.pop_menu, menu);
        ArrayList<Brand> brandsReference = adapter.getBrands();
        menu.setHeaderTitle("Choose your option for " + brandsReference.get(adapter.getSelectedItemPos()).getName());


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.delete_item){
            adapter.getBrands().remove(adapter.getSelectedItemPos());
            adapter.notifyDataSetChanged();
        }

        if(item.getItemId() == R.id.edit_item)
        {
           Intent editIntent = new Intent(this, EditView.class);
           Gson gson = new Gson();
           editIntent.putExtra("brand", gson.toJson(adapter.getSelectedBrand()));
           editIntent.putExtra("pos", adapter.getSelectedItemPos());
           ActivityStarter.bringActivity(this,editIntent);

        }

        return super.onContextItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void updateBrand(int position, String name, String url){
        adapter.updateBrand(position, name, url);
        adapter.notifyDataSetChanged();
    }


}
