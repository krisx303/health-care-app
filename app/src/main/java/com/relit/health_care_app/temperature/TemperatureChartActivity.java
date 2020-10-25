package com.relit.health_care_app.temperature;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.relit.health_care_app.CreateActivityHelper;
import com.relit.health_care_app.DataHelper;
import com.relit.health_care_app.R;
import com.relit.health_care_app.sugar.SugarDataBaseHelper;
import com.relit.health_care_app.sugar.SugarModel;
import com.relit.health_care_app.sugar.SugarModelArrayAdapter;
import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
@RequiresApi(api = Build.VERSION_CODES.N)
public class TemperatureChartActivity extends AppCompatActivity {

    private Point[] points;
    private List<String> dates = new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_chart);
        CreateActivityHelper.setToolbar(this, R.string.temperature);
        CreateActivityHelper.setNavigationButtonListener(this, R.id.goToBtn, TemperatureAddActivity.class);
        CreateActivityHelper.initGraph(this);
        loadGraphElements();
        listView = findViewById(R.id.list_view);
        drawGraph();
        refreshAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refreshAdapter(){
        TemperatureDataBaseHelper dataBase = DataHelper.getTemperatureDataBaseHelper();
        List<TemperatureModel> list = dataBase.getEveryone();
        List<TemperatureModel> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }
        ArrayAdapter<TemperatureModel> adapter = new TemperatureModelArrayAdapter(this, reversedList.subList(0, Math.min(reversedList.size(), 5)));
        ((ListView)listView).setAdapter(adapter);
    }

    private void loadGraphElements() {
        TemperatureDataBaseHelper dataBase = DataHelper.getTemperatureDataBaseHelper();
        List<TemperatureModel> list = dataBase.getEveryone();
        points = new Point[list.size()];
        for (int i = 0; i < list.size(); i++) {
            TemperatureModel model = list.get(i);
            points[i] = new Point(i, model.getTemperature()*2-70);
            dates.add(model.getDate().toString().substring(0,model.getDate().toString().length()-5));
        }
    }

    private void drawGraph() {
        Graph graph = new Graph.Builder().setYLabels(getYLabels()).setXLabels(getXLabels()).setWorldCoordinates(-1-(points.length)/4, points.length+2, -0.5, 13)
                .setAxes(0, 0).addLineGraph(points, Color.BLUE).build();
        GraphView graphView = findViewById(R.id.graph_view);
        graphView.setGraph(graph);
    }

    private List<Label> getYLabels(){
        List<Label> labels = new ArrayList<>();
        int tick = 0;
        labels.add(new Label(tick, ""));
        tick++;
        for (double i = 35.5; i <= 41.0; i=i+0.5) {
            labels.add(new Label(tick, i + "*C"));
            tick++;
        }
        return labels;
    }

    private List<Label> getXLabels() {
        List<Label> labels = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            labels.add(new Label(i, dates.get(i)));
        }
        return labels;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==16908332)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}