package com.relit.health_care_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.relit.health_care_app.models.Choice;
import com.relit.health_care_app.pressure.PressureModel;
import com.relit.health_care_app.sugar.SugarDataBaseHelper;
import com.relit.health_care_app.sugar.SugarModel;
import com.relit.health_care_app.temperature.TemperatureModel;
import com.relit.health_care_app.pressure.PressureDataBaseHelper;
import com.relit.health_care_app.temperature.TemperatureDataBaseHelper;
import com.relit.health_care_app.weight.WeightDataBaseHelper;
import com.relit.health_care_app.weight.WeightModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.preference.PreferenceManager;
@RequiresApi(api = Build.VERSION_CODES.N)
public class DataHelper {

    public static final String TEMPERATURE = "temperature";
    public static final String PRESSURE = "pressure";
    public static final String SUGAR_LEVEL = "sugar_level";
    public static final String FEELING = "feeling";
    public static final String MEDICINES = "medicines";
    public static final String WEIGHT = "weight";
    public static final String COVID = "covid";
    public static final String SOS = "sos";
    public static final String NOTIFICATIONS = "notifications";
    private static List<String> choicesStr = Arrays.asList(TEMPERATURE, PRESSURE, SUGAR_LEVEL, FEELING, MEDICINES, WEIGHT, COVID, SOS, NOTIFICATIONS);
    private static List<Choice> choices = new ArrayList<>();

    private static TemperatureDataBaseHelper temperatureDataBaseHelper;
    private static PressureDataBaseHelper pressureDataBaseHelper;
    private static SugarDataBaseHelper sugarDataBaseHelper;
    private static WeightDataBaseHelper weightDataBaseHelper;

    public static List<Choice> loadChoicesList(@Nullable Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        choices.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            choicesStr.forEach(x -> choices.add(loadChoice(x, sharedPreferences)));
        }
        setHasDataValues(context);
        return choices;
    }

    private static Choice loadChoice(String name, SharedPreferences mPrefs){
        boolean b = mPrefs.getBoolean(name, false);
        return new Choice(name, b);
    }
    public static int howManyOptionIsSelected(){
        int n = 0;
        for (int i = 0; i < choices.size(); i++) {
            n = n + (choices.get(i).isSelected() ? 1 : 0);
        }
        return n;
    }
    public static int getImageResourceByChoice(Choice choice){
        switch (choice.getName()){
            case TEMPERATURE:
                return choice.isHasData() ? R.drawable.temp_btn_with : R.drawable.temp_btn;
            case PRESSURE:
                return choice.isHasData() ? R.drawable.blood_btn_with : R.drawable.blood_btn;
            case SUGAR_LEVEL:
                return choice.isHasData() ? R.drawable.sugar_btn_with : R.drawable.sugar_btn;
            case FEELING:
                return R.drawable.feel_btn;
            case MEDICINES:
                return R.drawable.medi_btn;
            case COVID:
                return R.drawable.covid_btn;
            case WEIGHT:
                return choice.isHasData() ? R.drawable.weight_btn_with : R.drawable.weight_btn;
            case SOS:
                return R.drawable.sos_btn;
        }
        return 0;
    }

    private static Choice getChoiceByName(String name){
        return choices.stream().filter(x -> x.getName().equals(name)).findFirst().get();
    }


    public static void setHasDataValues(@Nullable Context context){
        temperatureDataBaseHelper = new TemperatureDataBaseHelper(context);
        boolean hasElements = temperatureDataBaseHelper.hasElements();
        getChoiceByName(TEMPERATURE).setHasData(hasElements);
        if(hasElements) {
            TemperatureModel model = temperatureDataBaseHelper.getLastData();
            getChoiceByName(TEMPERATURE).setLastDataString(model.getTemperature() + "*C");
            getChoiceByName(TEMPERATURE).setLastDateString(model.getDate().toString());
        }

        pressureDataBaseHelper = new PressureDataBaseHelper(context);
        hasElements = pressureDataBaseHelper.hasElements();
        getChoiceByName(PRESSURE).setHasData(hasElements);
        if(hasElements){
            PressureModel model = pressureDataBaseHelper.getLastData();
            getChoiceByName(PRESSURE).setLastDataString(model.getSystolic()+"/" + model.getDiastolic());
            getChoiceByName(PRESSURE).setLastDateString(model.getDate().toString());
        }
        sugarDataBaseHelper = new SugarDataBaseHelper(context);
        hasElements = sugarDataBaseHelper.hasElements();
        getChoiceByName(SUGAR_LEVEL).setHasData(hasElements);
        if(hasElements){
            SugarModel model = sugarDataBaseHelper.getLastData();
            getChoiceByName(SUGAR_LEVEL).setLastDataString(model.getValue() + "mg/dl");
            getChoiceByName(SUGAR_LEVEL).setLastDateString(model.getDate().toString());
        }
        weightDataBaseHelper = new WeightDataBaseHelper(context);
        hasElements = weightDataBaseHelper.hasElements();
        getChoiceByName(WEIGHT).setHasData(hasElements);
        if(hasElements){
            WeightModel model = weightDataBaseHelper.getLastData();
            getChoiceByName(WEIGHT).setLastDataString(model.getWeight() + "kg");
            getChoiceByName(WEIGHT).setLastDateString(model.getDate().toString());
        }
    }

    public static TemperatureDataBaseHelper getTemperatureDataBaseHelper() {
        return temperatureDataBaseHelper;
    }

    public static PressureDataBaseHelper getPressureDataBaseHelper() {
        return pressureDataBaseHelper;
    }

    public static SugarDataBaseHelper getSugarDataBaseHelper() {
        return sugarDataBaseHelper;
    }

    public static WeightDataBaseHelper getWeightDataBaseHelper() {
        return weightDataBaseHelper;
    }
}
