package com.relit.health_care_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHelper.loadChoicesList(this);
    }

    public void setNextScene(View view){
        Intent intent;
        if(DataHelper.howManyOptionIsSelected()==0){
            ChoicesData.setActualId(-1);
            intent = new Intent(this, ChoiceActivity.class);
        }else{
            intent = new Intent(this, Menu.class);
        }
        startActivity(intent);
    }
}