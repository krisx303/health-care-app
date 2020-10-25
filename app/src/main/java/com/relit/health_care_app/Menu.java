package com.relit.health_care_app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.relit.health_care_app.models.Choice;
import com.relit.health_care_app.pressure.PressureAddActivity;
import com.relit.health_care_app.pressure.PressureChartActivity;
import com.relit.health_care_app.sugar.SugarAddActivity;
import com.relit.health_care_app.sugar.SugarChartActivity;
import com.relit.health_care_app.temperature.TemperatureAddActivity;
import com.relit.health_care_app.temperature.TemperatureChartActivity;
import com.relit.health_care_app.weight.WeightAddActivity;
import com.relit.health_care_app.weight.WeightChartActivity;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
@RequiresApi(api = Build.VERSION_CODES.N)
public class Menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refreshAdapter(){
        //reloading choices list
        List<Choice> choices = DataHelper.loadChoicesList(this);
        ArrayAdapter<Choice> adapter = new MenuElementsArrayAdapter(this, choices.stream().filter(Choice::isSelected).filter(x -> !x.getName().equals("notifications")).collect(Collectors.toList()), this);
        ((ListView)findViewById(R.id.listview)).setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }
    public void openActivity(Choice choice){
        Intent intent = null;
        switch (choice.getName()){
            case DataHelper.TEMPERATURE:
                intent = new Intent(this, choice.isHasData() ? TemperatureChartActivity.class : TemperatureAddActivity.class);
                break;
            case DataHelper.COVID:
                intent = new Intent(this, Coronavirus.class);
                break;
            case DataHelper.PRESSURE:
                intent = new Intent(this, choice.isHasData() ? PressureChartActivity.class : PressureAddActivity.class);
                break;
            case DataHelper.SUGAR_LEVEL:
                intent = new Intent(this, choice.isHasData() ? SugarChartActivity.class : SugarAddActivity.class);
                break;
            case DataHelper.WEIGHT:
                intent = new Intent(this, choice.isHasData() ? WeightChartActivity.class : WeightAddActivity.class);
                break;
        }
        if(intent!=null)
            startActivity(intent);
    }
}