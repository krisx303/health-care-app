package com.relit.health_care_app.temperature;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.relit.health_care_app.DataHelper;
import com.relit.health_care_app.Menu;
import com.relit.health_care_app.R;
import com.relit.health_care_app.models.Date;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TemperatureAddActivity extends AppCompatActivity {
    private DatePickerDialog picker;
    private EditText eText, edit_temp;
    private Button save_btn;
    private Date date;
    private TextView temp_error_field, date_error_field;
    private TemperatureDataBaseHelper dataBaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_add);
        dataBaseHelper = DataHelper.getTemperatureDataBaseHelper();
        setActionBar();
        findViewElements();
        eText.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(TemperatureAddActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                        date = new Date(dayOfMonth, monthOfYear, year1);
                        }, year, month, day);
            picker.show();
        });
        save_btn.setOnClickListener(e -> onSaveButtonClick());
    }

    private void onSaveButtonClick() {
        boolean flag = false;
        if(date==null){
            date_error_field.setText("To pole nie może pozostać puste!!");
            flag = true;
        }
        else {
            date_error_field.setText("");
        }
        String temp = edit_temp.getText().toString();
        if(temp.equals("") || Float.parseFloat(temp)<35 || Float.parseFloat(temp)>43){
            temp_error_field.setText("Podano nie poprawną wartość");
            flag = true;
        }else{
            temp_error_field.setText("");
        }
        if(!flag){
            TemperatureModel model = new TemperatureModel(-1, date, Float.parseFloat(temp));
            boolean success = dataBaseHelper.addElement(model);
            Toast.makeText(this, success ? "Zapisano pomiar" : "Błąd podczas zapisywania", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Menu.class));
        }
    }

    private void findViewElements() {
        edit_temp = findViewById(R.id.edit_temp);
        eText=(EditText) findViewById(R.id.editText1);

        temp_error_field = findViewById(R.id.temp_error_field);
        date_error_field = findViewById(R.id.date_error_field);
        save_btn = findViewById(R.id.save_btn);
    }

    private void setActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.temperature);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==16908332)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}