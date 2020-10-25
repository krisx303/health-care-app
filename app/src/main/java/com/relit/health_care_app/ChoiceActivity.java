package com.relit.health_care_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.relit.health_care_app.models.Element;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class ChoiceActivity extends AppCompatActivity {
    private TextView title;
    private TextView description;
    private Button yesbtn, nobtn;
    private ImageView imageView;
    private List<ImageView> imageViews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        findObjectsOnScene();
        Element element = ChoicesData.getNextElement();
        title.setText(element.getTitle());
        description.setText(element.getDescription());
        imageView.setImageResource(element.getImageId());
        yesbtn.setOnClickListener(e -> setElementSelectedAndOpenNextActivity(true));
        nobtn.setOnClickListener(e -> setElementSelectedAndOpenNextActivity(false));
        setActualCircleActive();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ChoicesData.setActualId(ChoicesData.getActualId()-1);
    }

    private void setActualCircleActive() {
        int id = ChoicesData.getActualId();
        for (int i = 0; i < imageViews.size(); i++) {
            if(i==id){
                imageViews.get(i).setImageResource(R.drawable.circle_big);
            }else{
                imageViews.get(i).setImageResource(R.drawable.circle_small);
            }
        }
    }

    public void setElementSelectedAndOpenNextActivity(boolean value){
        ChoicesData.setActualElementSelected(value);
        Intent intent;
        if(ChoicesData.hasNextElement()){
            intent = new Intent(this, ChoiceActivity.class);
        }else{
            ChoicesData.saveChoices(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
            intent = new Intent(this, Menu.class);
        }
        startActivity(intent);
    }
    private void findObjectsOnScene(){
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        yesbtn = findViewById(R.id.yesbtn);
        nobtn = findViewById(R.id.nobtn);
        imageView = findViewById(R.id.imageView);
        imageViews.add(findViewById(R.id.circle1));
        imageViews.add(findViewById(R.id.circle2));
        imageViews.add(findViewById(R.id.circle3));
        imageViews.add(findViewById(R.id.circle4));
        imageViews.add(findViewById(R.id.circle5));
        imageViews.add(findViewById(R.id.circle6));
        imageViews.add(findViewById(R.id.circle7));
        imageViews.add(findViewById(R.id.circle8));
        imageViews.add(findViewById(R.id.circle9));
    }
}