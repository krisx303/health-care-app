package com.relit.health_care_app.pressure;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.util.Calendar;

public class PressureAddActivity extends AppCompatActivity {
    DatePickerDialog picker;
    EditText eText, edit_diastolic, edit_systolic;
    Button save_btn;
    TextView diastolic_error_field, date_error_field, systolic_error_field;
    PressureDataBaseHelper dataBaseHelper;
    private Date date;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_add);
        dataBaseHelper = DataHelper.getPressureDataBaseHelper();
        CreateActivityHelper.setToolbar(this, R.string.pressure);
        findViewElements();
        eText.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(PressureAddActivity.this,
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
        String diastolic = edit_diastolic.getText().toString();
        String systolic = edit_systolic.getText().toString();
        if(diastolic.equals("") || Float.parseFloat(diastolic)<50 || Float.parseFloat(diastolic)>130){
            diastolic_error_field.setText("Podano nie poprawną wartość");
            flag = true;
        }else{
            diastolic_error_field.setText("");
        }
        if(systolic.equals("") || Float.parseFloat(systolic)<60 || Float.parseFloat(systolic)>220){
            systolic_error_field.setText("Podano nie poprawną wartość");
            flag = true;
        }else{
            systolic_error_field.setText("");
        }
        if(!flag){
            PressureModel model = new PressureModel(-1, date, Integer.parseInt(systolic), Integer.parseInt(diastolic));
            boolean success = dataBaseHelper.addElement(model);
            Toast.makeText(this, success ? "Zapisano pomiar" : "Błąd podczas zapisywania", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Menu.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==16908332)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void findViewElements() {
        edit_diastolic = findViewById(R.id.edit_diastolic);
        edit_systolic = findViewById(R.id.edit_systolic);
        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        systolic_error_field = findViewById(R.id.systolic_error_field);
        diastolic_error_field = findViewById(R.id.diastolic_error_field);
        date_error_field = findViewById(R.id.date_error_field);
        save_btn = findViewById(R.id.save_btn);
    }
}