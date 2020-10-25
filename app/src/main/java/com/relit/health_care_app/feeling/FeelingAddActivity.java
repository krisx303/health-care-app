package com.relit.health_care_app.feeling;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.relit.health_care_app.CreateActivityHelper;
import com.relit.health_care_app.Menu;
import com.relit.health_care_app.R;
import com.relit.health_care_app.models.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FeelingAddActivity extends AppCompatActivity {

    private SeekBar feeling_bar, stress_bar;
    private TextView feeling_text, stress_text;
    private Date date;
    private List<Element> elements = new ArrayList<>();
    private EditText commentsField;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_add);
        findViewElements();
        CreateActivityHelper.setToolbar(this, R.string.feeling);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        date = new Date(format.format(new java.util.Date()));
        feeling_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                feeling_text.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        stress_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                stress_text.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        initListOfElements();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initListOfElements() {
        elements.add(new Element("headache", findViewById(R.id.headache)));
        elements.add(new Element("stomach", findViewById(R.id.stomach)));
        elements.add(new Element("migraine", findViewById(R.id.migraine)));
        elements.add(new Element("cough", findViewById(R.id.cough)));
        elements.add(new Element("runny_nose", findViewById(R.id.runnyNose)));
        elements.add(new Element("muscle_aches", findViewById(R.id.muscleAches)));
        elements.add(new Element("throat", findViewById(R.id.throat)));
        elements.add(new Element("dream", findViewById(R.id.dream)));
        elements.add(new Element("back_aches", findViewById(R.id.backAches)));
    }

    @SuppressLint("SetTextI18n")
    private void findViewElements() {
        feeling_bar = findViewById(R.id.feelingSeekBar);
        stress_bar = findViewById(R.id.stressSeekBar);
        feeling_text = findViewById(R.id.feelingTextView);
        stress_text = findViewById(R.id.stressTextView);
        feeling_text.setText(feeling_bar.getProgress() + "");
        stress_text.setText(stress_bar.getProgress() + "");
        commentsField = findViewById(R.id.editTextTextMultiLine);
        findViewById(R.id.save_btn).setOnClickListener(e -> onSaveButtonClick());
    }

    private void onSaveButtonClick() {
        int feeling_value = feeling_bar.getProgress();
        int stress_value = stress_bar.getProgress();
        elements.forEach(Element::update);
        HashMap<String, Boolean> aches = new HashMap<>();
        elements.forEach(element -> aches.put(element.getName(), element.isSelected()));
        FeelingModel model = new FeelingModel(-1, date, aches, feeling_value, stress_value, commentsField.getText().toString());
        startActivity(new Intent(this, Menu.class));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==16908332)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    private static class Element{
        private boolean selected;
        private String name;
        private CheckBox checkBox;

        public Element(String name, CheckBox checkBox) {
            this.name = name;
            this.checkBox = checkBox;
        }

        public void update(){
            setSelected(checkBox.isChecked());
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }
    }
}