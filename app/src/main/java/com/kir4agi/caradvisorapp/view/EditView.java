package com.kir4agi.caradvisorapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kir4agi.caradvisorapp.R;
import com.kir4agi.caradvisorapp.model.Brand;
import com.kir4agi.caradvisorapp.model.Model;

public class EditView extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;
    private int position;
    Brand brand;
    Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        Bundle info = getIntent().getExtras();
        Gson gson = new Gson();
        String jsonBrand = null;
        String jsonModel = null;
        if(info.get("brand") != null) {
            jsonBrand = info.get("brand").toString();
        }else
            jsonModel = info.get("model").toString();

        if(jsonBrand != null) {
            brand = gson.fromJson(info.get("brand").toString(), Brand.class);
        }else{
            model = gson.fromJson(jsonModel, Model.class);
        }
        position = info.getInt("pos",-1);
        final EditText urlEditText = findViewById(R.id.url);
        if(brand != null) {
            editText = findViewById(R.id.editText);
            editText.setText(brand.getName());
            urlEditText.setText(brand.getModelsURL());
        }else{
            editText = findViewById(R.id.editText);
            editText.setText(model.getModelName());
            urlEditText.setText(model.getCarURL());
        }



        Button discard = findViewById(R.id.discard);

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditView.this.onBackPressed();
            }
        });


        Button save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(brand != null) {
                    BrandsActivity.updateBrand(position, editText.getText().toString(), urlEditText.getText().toString());
                }else{
                    ModelsActivity.updateBrand(position,editText.getText().toString(), urlEditText.getText().toString());
                }
                EditView.this.onBackPressed();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 7:
                if(resultCode == RESULT_OK){
                    String pathHolder = data.getData().getPath();
                    Toast.makeText(EditView.this, pathHolder, Toast.LENGTH_SHORT);
                }
        }
    }
}
