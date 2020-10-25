package com.relit.health_care_app.pressure;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.relit.health_care_app.CreateActivityHelper;
import com.relit.health_care_app.DataHelper;
import com.relit.health_care_app.R;
import com.relit.health_care_app.sugar.SugarAddActivity;
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
public class PressureChartActivity extends AppCompatActivity {

    private Point[] pointsSystolic;
    private Point[] pointsDiastolic;
    private List<String> dates = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_chart);
        CreateActivityHelper.setToolbar(this, R.string.pressure);
        CreateActivityHelper.setNavigationButtonListener(this, R.id.goToBtn, PressureAddActivity.class);
        CreateActivityHelper.initGraph(this);
        loadGraphElements();
        listView = findViewById(R.id.list_view);
        drawGraph();
        refreshAdapter();
    }

    private void loadGraphElements() {
        PressureDataBaseHelper dataBase = DataHelper.getPressureDataBaseHelper();
        List<PressureModel> list = dataBase.getEveryone();
        pointsSystolic = new Point[list.size()];
        pointsDiastolic = new Point[list.size()];
        for (int i = 0; i < list.size(); i++) {
            PressureModel model = list.get(i);
            pointsSystolic[i] = new Point(i, ((double)model.getSystolic()-40)/20);
            pointsDiastolic[i] = new Point(i, ((double)model.getDiastolic()-40)/20);
            dates.add(model.getDate().toString().substring(0,model.getDate().toString().length()-5));
        }
    }

    private void drawGraph() {
        Graph graph = new Graph.Builder().setXLabels(getXLabels()).setYLabels(getYLabels()).setWorldCoordinates(-1, pointsSystolic.length+2, -0.5, 10)
                .setAxes(0, 0).addLineGraph(pointsSystolic, Color.BLUE).addLineGraph(pointsDiastolic, Color.RED).build();
        GraphView graphView = findViewById(R.id.graph_view);
        graphView.setGraph(graph);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refreshAdapter(){
        PressureDataBaseHelper dataBase = DataHelper.getPressureDataBaseHelper();
        List<PressureModel> list = dataBase.getEveryone();
        List<PressureModel> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }
        ArrayAdapter<PressureModel> adapter = new PressureModelArrayActivity(this, reversedList.subList(0, Math.min(reversedList.size(), 5)));
        ((ListView)listView).setAdapter(adapter);
    }

    private List<Label> getYLabels(){
        List<Label> labels = new ArrayList<>();
        int tick = 0;
        labels.add(new Label(tick, ""));
        tick++;
        for (double i = 60; i <= 220; i=i+20) {
            labels.add(new Label(tick, i + ""));
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