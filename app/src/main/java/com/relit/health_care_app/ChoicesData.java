package com.relit.health_care_app;

import android.content.SharedPreferences;

import com.relit.health_care_app.models.Element;

import java.util.Arrays;
import java.util.List;

public class ChoicesData {
    private static int actualId = -1;
    private static List<Element> elements = Arrays.asList(
            new Element(DataHelper.TEMPERATURE, "Temperatura", "Czy chcesz zapisywać pomiary temperatury?", R.drawable.temperature),
            new Element(DataHelper.PRESSURE, "Ciśnienie krwi", "Czy chcesz zapisywać pomiary ciśnienia krwi", R.drawable.blood_test),
            new Element(DataHelper.SUGAR_LEVEL, "Poziom cukru", "Czy chcesz zapisywać pomiary poziomu cukru we krwi?", R.drawable.glucose_meter),
            new Element(DataHelper.FEELING, "Samopoczucie", "Czy chcesz zapisywać swoje samopoczucie i dolegliwości?", R.drawable.evaluate),
            new Element(DataHelper.MEDICINES, "Leki", "Czy chcesz zapisywać swoje samopoczucie i dolegliwości?", R.drawable.medicine),
            new Element(DataHelper.COVID, "Koronawirus", "jasghdsagdjkasd", R.drawable.coronavirus),
            new Element(DataHelper.WEIGHT, "Waga", "asgadshsagd", R.drawable.weight_scale),
            new Element(DataHelper.SOS, "SOS", "sosososos", R.drawable.sos),
            new Element(DataHelper.NOTIFICATIONS, "Powiadomienia", "asdhasjdhasjdas", R.drawable.notification));


    public static Element getNextElement() {
        actualId++;
        if(actualId<elements.size())
            return elements.get(actualId);
        else
            return null;

    }
    public static boolean hasNextElement(){
        return actualId+1<elements.size();
    }
    public static void setActualElementSelected(boolean value){
        elements.get(actualId).setSelected(value);
    }
    public static void saveChoices(SharedPreferences mPrefs){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        for (int i = 0; i < elements.size(); i++) {
            mEditor.putBoolean(elements.get(i).getName(), elements.get(i).isSelected()).apply();
        }
    }

    public static int getActualId() {
        return actualId;
    }

    public static void setActualId(int actualId) {
        ChoicesData.actualId = actualId;
    }
}
