package com.relit.health_care_app.sugar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.relit.health_care_app.CreateActivityHelper;
import com.relit.health_care_app.DataHelper;
import com.relit.health_care_app.Menu;
import com.relit.health_care_app.R;
import com.relit.health_care_app.models.Date;
import com.relit.health_care_app.pressure.PressureAddActivity;
import com.relit.health_care_app.pressure.PressureDataBaseHelper;
import com.relit.health_care_app.pressure.PressureModel;

import java.util.Calendar;

public class SugarAddActivity extends AppCompatActivity {
    DatePickerDialog picker;
    EditText eText, edit_sugar;
    Button save_btn;
    TextView sugar_error_field, date_error_field;
    SugarDataBaseHelper dataBaseHelper;
    private Date date;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar_add);
        dataBaseHelper = DataHelper.getSugarDataBaseHelper();
        CreateActivityHelper.setToolbar(this, R.string.sugar_level);
        findViewElements();
        eText.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(SugarAddActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                        date = new Date(dayOfMonth, monthOfYear, year1);
                    }, year, month, day);
            picker.show();
        });
        save_btn.setOnClickListener(e -> {
            if(date==null && eText.getText().toString().length()>6){
                Log.e("Temperature", "Error");
            }
            boolean flag = false;
            if(date==null){
                date_error_field.setText("To pole nie może pozostać puste!!");
                flag = true;
            }
            else {
                date_error_field.setText("");
            }
            String sugar = edit_sugar.getText().toString();
            if(sugar.equals("") || Float.parseFloat(sugar)<20 || Float.parseFloat(sugar)>220){
                sugar_error_field.setText("Podano nie poprawną wartość");
                flag = true;
            }else{
                sugar_error_field.setText("");
            }
            if(!flag){
                SugarModel model = new SugarModel(-1, date, Integer.parseInt(sugar));
                boolean success = dataBaseHelper.addElement(model);
                Toast.makeText(this, success ? "Zapisano pomiar" : "Błąd podczas zapisywania", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, Menu.class));
            }
        });
    }

    private void findViewElements() {
        edit_sugar = findViewById(R.id.edit_sugar);
        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        sugar_error_field = findViewById(R.id.sugar_error_field);
        date_error_field = findViewById(R.id.date_error_field);
        save_btn = findViewById(R.id.save_btn);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==16908332)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}